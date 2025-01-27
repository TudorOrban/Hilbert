package com.hilbert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class HilbertApplication {

	public static void main(String[] args) {
		SpringApplication.run(HilbertApplication.class, args);
	}

}
