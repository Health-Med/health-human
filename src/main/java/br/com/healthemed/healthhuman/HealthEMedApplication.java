package br.com.healthemed.healthhuman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/** 
 * 
*/
@SpringBootApplication
@ComponentScan(basePackages = { "br.com.healthemed.healthhuman" })
public class HealthEMedApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HealthEMedApplication.class, args);
	}
}
