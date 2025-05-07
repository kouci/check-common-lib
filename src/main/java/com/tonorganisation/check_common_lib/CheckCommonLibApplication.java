package com.tonorganisation.check_common_lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Entry point for the CheckCommonLibraryApplication.
 * This class is annotated with @SpringBootApplication, indicating it as the primary
 * configuration class used to bootstrap a Spring Boot application. The main method
 * initializes the application by invoking SpringApplication.run.
 */

@SpringBootApplication
@EnableAspectJAutoProxy
public class CheckCommonLibApplication  {

	/**
	 * The main entry point of the application.
	 *
	 * This method is used to launch the Spring Boot application.
	 *
	 * @param args an array of command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(CheckCommonLibApplication.class, args);
	}

}

