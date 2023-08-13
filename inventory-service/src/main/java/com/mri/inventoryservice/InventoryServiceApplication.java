package com.mri.inventoryservice;

import com.mri.inventoryservice.model.Inventory;
import com.mri.inventoryservice.repo.InventoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
		System.out.println("Inventory service is running!");
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepo inventoryRepo) {
		 return args -> {
			 Inventory inventory = new Inventory();
			 inventory.setSkuCode("iphone_13");
			 inventory.setQuantity(5);
			 inventoryRepo.save(inventory);

			 Inventory inventory2 = new Inventory();
			 inventory2.setSkuCode("iphone_13_blue");
			 inventory2.setQuantity(0);
			 inventoryRepo.save(inventory2);
		 };
	}

}
