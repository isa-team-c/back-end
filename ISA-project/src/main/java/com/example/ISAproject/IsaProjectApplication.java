package com.example.ISAproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableSwagger2
public class IsaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaProjectApplication.class, args);
	}
	
	@Bean
	public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .build()
          .pathMapping("");
    }	

}
