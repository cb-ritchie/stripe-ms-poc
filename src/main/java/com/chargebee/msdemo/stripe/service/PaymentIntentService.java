package com.chargebee.msdemo.stripe.service;

public interface PaymentIntentService {

	String capturePaymentIntent(String intentId);

}
