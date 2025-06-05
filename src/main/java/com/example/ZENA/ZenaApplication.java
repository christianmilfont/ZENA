package com.example.ZENA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class ZenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZenaApplication.class, args);
	}

}
