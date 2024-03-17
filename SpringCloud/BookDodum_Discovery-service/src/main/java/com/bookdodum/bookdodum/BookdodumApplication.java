package com.bookdodum.bookdodum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BookdodumApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookdodumApplication.class, args);
	}

}
