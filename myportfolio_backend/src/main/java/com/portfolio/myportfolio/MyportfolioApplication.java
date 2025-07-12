package com.portfolio.myportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MyportfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyportfolioApplication.class, args);
	}

}
