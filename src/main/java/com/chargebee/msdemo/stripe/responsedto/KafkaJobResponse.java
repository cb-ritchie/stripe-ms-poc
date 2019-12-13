package com.chargebee.msdemo.stripe.responsedto;

import com.chargebee.msdemo.stripe.constants.RequestStatus;
import com.chargebee.msdemo.stripe.requestdto.KafkaJobRequest;

public class KafkaJobResponse {
	private KafkaJobRequest request;
	private RequestStatus status;
	private Object response;
	public KafkaJobRequest getRequest() {
		return request;
	}
	public void setRequest(KafkaJobRequest request) {
		this.request = request;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
}
