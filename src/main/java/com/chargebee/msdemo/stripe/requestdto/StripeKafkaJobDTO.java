package com.chargebee.msdemo.stripe.requestdto;

import com.chargebee.msdemo.stripe.constants.StripeActions;

public class StripeKafkaJobDTO {
	
	private StripeActions action;
	private KafkaJobRequest request;
	
	public StripeActions getAction() {
		return action;
	}
	public void setAction(StripeActions action) {
		this.action = action;
	}
	public KafkaJobRequest getRequest() {
		return request;
	}
	public void setRequest(KafkaJobRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		return "StripeKafkaJobDTO [action=" + action + ", request=" + request + "]";
	}
	
	

}
