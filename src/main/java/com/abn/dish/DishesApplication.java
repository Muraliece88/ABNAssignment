package com.abn.dish;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DishesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishesApplication.class);

	}

	@Bean
	public InMemoryAuditEventRepository repository(){
		return new InMemoryAuditEventRepository();
	}
}
