package com.jlm.banq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BanqApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqApplication.class, args);
	}

}
