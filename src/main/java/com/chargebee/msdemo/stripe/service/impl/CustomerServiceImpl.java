package com.chargebee.msdemo.stripe.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.chargebee.msdemo.stripe.requestdto.CustomerDTO;
import com.chargebee.msdemo.stripe.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Override
	public String createCustomer(CustomerDTO customerDTO) {
		RestTemplate template = new RestTemplate();
		String requestURL = "https://api.stripe.com/v1/customers";
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + "sk_test_ZcucN6BJN8RKr7m49P0Jsooc00n1Nyb7Gx");
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("description", "test User description");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, header);
		ResponseEntity<String>resp = template.postForEntity(requestURL, request, String.class);
		System.out.println(resp.getBody());
		return resp.getBody();
	}

}
