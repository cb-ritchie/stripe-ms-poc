package com.chargebee.msdemo.stripe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chargebee.msdemo.stripe.service.RefundService;

@RestController
@RequestMapping("/refund")
public class RefundController {
	
	@Autowired
	private RefundService refundService;
	
	@PostMapping("/chargeId/{chargeId}")
	public String createChargeRequest(@PathVariable("chargeId") String chargeId) {
		return refundService.createChargeRefund(chargeId);
	}

}
