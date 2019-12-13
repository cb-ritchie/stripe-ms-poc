package com.chargebee.msdemo.stripe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.chargebee.msdemo.stripe.stream.StripeJobStreamer;

@SpringBootApplication
@ComponentScan(basePackages = {"com.chargebee.msdemo.stripe.*"})
public class StripeApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StripeApplication.class, args);
		StripeJobStreamer streamer = context.getBean(StripeJobStreamer.class);
		streamer.start();
	}

}
