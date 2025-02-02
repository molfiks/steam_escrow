package com.angular.angularnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AngularNetworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngularNetworkApiApplication.class, args);
	}

}
