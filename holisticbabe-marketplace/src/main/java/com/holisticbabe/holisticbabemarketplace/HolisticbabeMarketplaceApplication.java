package com.holisticbabe.holisticbabemarketplace;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HolisticbabeMarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolisticbabeMarketplaceApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
