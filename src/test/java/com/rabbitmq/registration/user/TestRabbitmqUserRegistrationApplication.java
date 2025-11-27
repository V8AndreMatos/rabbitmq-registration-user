package com.rabbitmq.registration.user;

import org.springframework.boot.SpringApplication;

public class TestRabbitmqUserRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.from(RabbitmqUserRegistrationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
