package com.solutis.locadora.vehicle_catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class VehicleCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleCatalogApplication.class, args);
	}

}
