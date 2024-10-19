package com.solutis.locadora.vehicle_catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VehicleCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleCatalogApplication.class, args);
	}

}
