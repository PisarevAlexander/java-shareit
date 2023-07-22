package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShareItApp {

	public static void main(String[] args) {
		SpringApplication application =
				new SpringApplication(ShareItApp.class);
		application.setAdditionalProfiles("test");
		application.run(args);
	}
}