package com.app.customer_front_theymeleaf_app;

import com.app.customer_front_theymeleaf_app.entities.Customer;
import com.app.customer_front_theymeleaf_app.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerFrontTheymeleafAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerFrontTheymeleafAppApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
		return args -> {
		 	customerRepository.save(Customer.builder().name("Mohammed").email("mohammed123@gmail.com").build());
			customerRepository.save(Customer.builder().name("Ali").email("ali123@gmail.com").build());
			customerRepository.save(Customer.builder().name("Ahmed").email("ahmed123@gmail.com").build());
		};
	}

}
