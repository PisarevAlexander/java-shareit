package ru.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Share it server
 */

@SpringBootApplication
public class ShareItServer {

	/**
	 * The entry point of application
	 * @param args the input arguments
	 */

	public static void main(String[] args) {
		SpringApplication application =
				new SpringApplication(ShareItServer.class);
		application.setAdditionalProfiles("production");
		application.run(args);
	}
}