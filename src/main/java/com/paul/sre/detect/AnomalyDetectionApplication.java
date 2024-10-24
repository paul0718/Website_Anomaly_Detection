package com.paul.sre.detect;

/**
 *  License info: 
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @see@
 * @author win
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.paul.sre.detect")
public class AnomalyDetectionApplication {

	public static void main(String[] args) {
		
		
		SpringApplication.run(AnomalyDetectionApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}

}
