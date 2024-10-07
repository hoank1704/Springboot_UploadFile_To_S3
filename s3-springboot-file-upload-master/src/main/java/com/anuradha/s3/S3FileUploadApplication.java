package com.anuradha.s3;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot REST API", version = "1.0", description = "API documentation for Spring Boot application"))
public class S3FileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3FileUploadApplication.class, args);
	}

}
