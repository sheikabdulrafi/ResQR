package com.resqr.lifesaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LifesaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifesaverApplication.class, args);
	}

}
