package com.tonorganisation.check_common_lib;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CheckCommonLibApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CheckCommonLibApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		return;
	}

}

