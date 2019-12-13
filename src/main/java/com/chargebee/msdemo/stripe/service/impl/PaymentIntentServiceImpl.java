package com.chargebee.msdemo.stripe.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chargebee.msdemo.stripe.service.PaymentIntentService;

@Service
public class PaymentIntentServiceImpl implements PaymentIntentService {

	@Override
	public String capturePaymentIntent(String intentId) {
		RestTemplate template = new RestTemplate();
		String requestURL = "https://api.stripe.com/v1/payment_intents/" + intentId + "/capture";
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + "sk_test_ZcucN6BJN8RKr7m49P0Jsooc00n1Nyb7Gx");
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> request = new HttpEntity<String>(header);
		ResponseEntity<String>resp = template.postForEntity(requestURL, request, String.class);
		System.out.println(resp.getBody());
		return resp.getBody();	
	}
}
