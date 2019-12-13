package com.chargebee.msdemo.stripe.stream;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes.StringSerde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chargebee.msdemo.stripe.constants.RequestStatus;
import com.chargebee.msdemo.stripe.requestdto.CustomerDTO;
import com.chargebee.msdemo.stripe.requestdto.StripeKafkaJobDTO;
import com.chargebee.msdemo.stripe.responsedto.KafkaJobResponse;
import com.chargebee.msdemo.stripe.service.CustomerService;
import com.chargebee.msdemo.stripe.service.PaymentIntentService;
import com.chargebee.msdemo.stripe.service.RefundService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Component
public class StripeJobStreamer {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PaymentIntentService paymentIntentService;
	
	@Autowired
	private RefundService refundService;
	
	@Value("${kafka.server.url}") private String kafkaServerUrl;
	
	@Value("${kafka.stream.group.id}") private String kafkaStreamApplicationId;
	
	@Value("${kafka.stream.thread.max}") private int kafkaStreamThreads;
	
	@Value("${kafka.stripe.jobs.topic}") private String stripeKafkaJobTopic;
	
	@Value("${kafka.stripe.response.topic}") private String stripeKafkaResponseTopic;

	public void start() {

		Properties props = new Properties();
		
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaStreamApplicationId);
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, StringSerde.class);
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StringSerde.class);
		props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, kafkaStreamThreads);
		
		StreamsConfig config = new StreamsConfig(props);

		StreamsBuilder builder = new StreamsBuilder();

		KStream<Object, Object> stream = builder.stream(stripeKafkaJobTopic);

		KStream<Object, Object> outputStream = stream.mapValues(new ValueMapper<Object, Object>() {

			@Override
			public String apply(Object value) {
				KafkaJobResponse kafkaResponse = new KafkaJobResponse();
				try {
					Gson gson = new Gson();
					StripeKafkaJobDTO job = gson.fromJson((String) value, StripeKafkaJobDTO.class);
					kafkaResponse.setRequest(job.getRequest());
					ObjectMapper mapper = new ObjectMapper();
					Map<String, String> inputParams = mapper.convertValue(job.getRequest().getInputParams(), Map.class);
					String response = "";
					switch(job.getAction()) {
					case CREATE_CUSTOMER:
						if(inputParams.containsKey("description")) {
							String description = inputParams.get("description");
							CustomerDTO dto = new CustomerDTO();
							dto.setDescription(description);
							response = customerService.createCustomer(dto);
							kafkaResponse.setStatus(RequestStatus.SUCCESS);
							kafkaResponse.setResponse(response);
						}
						break;
						
					case CAPTURE_PAYMENT:
						if(inputParams.containsKey("paymentIntentId")) {
							String paymentIntentId = inputParams.get("paymentIntentId");
							response = paymentIntentService.capturePaymentIntent(paymentIntentId);
							kafkaResponse.setStatus(RequestStatus.SUCCESS);
							kafkaResponse.setResponse(response);
						}
						break;
						
					case CREATE_REFUND:
						if(inputParams.containsKey("chargeId")) {
							String chargeId = inputParams.get("chargeId");
							response = refundService.createChargeRefund(chargeId);
							kafkaResponse.setStatus(RequestStatus.SUCCESS);
							kafkaResponse.setResponse(response);
						}
						break;
						
					default:
						kafkaResponse.setStatus(RequestStatus.FAILURE);
						kafkaResponse.setResponse("NOT VALID OPERATION");
						System.out.println("NOT valid Operation");
					}
					
					return new Gson().toJson(kafkaResponse);
				} catch (Exception e) {
					System.out.println(e);
					kafkaResponse.setStatus(RequestStatus.FAILURE);
					kafkaResponse.setResponse(e.getCause());
					return new Gson().toJson(kafkaResponse);
				}
				
			}
		});
		outputStream.to(stripeKafkaResponseTopic);

		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();

	}

}
