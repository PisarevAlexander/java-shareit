package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShareItServer {

	public static void main(String[] args) {
		SpringApplication application =
				new SpringApplication(ShareItServer.class);
		application.setAdditionalProfiles("production");
		application.run(args);
	}
}