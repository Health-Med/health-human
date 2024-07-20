package br.com.healthemed.healthhuman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/** 
 * 
*/
@SpringBootApplication
@ComponentScan(basePackages = { "br.com.healthemed.healthhuman" })
@EnableFeignClients
//@EnableCaching
public class HealthEMedApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HealthEMedApplication.class, args);
	}
}
