package com.chargebee.msdemo.stripe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chargebee.msdemo.stripe.service.PaymentIntentService;
import com.chargebee.msdemo.stripe.service.impl.PaymentIntentServiceImpl;

@RestController
@RequestMapping("/paymentintent")
public class PaymentIntentController {
	
	@Autowired
	private PaymentIntentService paymentIntentService;

	@PostMapping("capture/{intentId}")
	public String capturePaymentIntent(@PathVariable("intentId")String intentId) {
		System.out.println(intentId);
		return paymentIntentService.capturePaymentIntent(intentId);
	}
	
	
}
