package com.chargebee.msdemo.stripe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chargebee.msdemo.stripe.requestdto.CustomerDTO;
import com.chargebee.msdemo.stripe.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@PostMapping("")
	public String createCustomer(@RequestBody CustomerDTO dto) {
		System.out.println(dto.toString());
		return customerService.createCustomer(dto);
	}
	
}
