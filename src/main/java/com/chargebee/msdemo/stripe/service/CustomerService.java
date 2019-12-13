package com.chargebee.msdemo.stripe.service;

import com.chargebee.msdemo.stripe.requestdto.CustomerDTO;

public interface CustomerService {

	String createCustomer(CustomerDTO customerDTO);

}
