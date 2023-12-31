package com.mri.inventoryservice.service;

import com.mri.inventoryservice.dto.InventoryResponse;
import com.mri.inventoryservice.model.Inventory;
import com.mri.inventoryservice.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    @Transactional(readOnly = true)
    @SneakyThrows              // don't use this in production
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Wait started!");

        //Thread.sleep(10000);

        log.info("Wait ended!");
        return inventoryRepo.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                          InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }

    public List<Inventory> findAll() {
        return inventoryRepo.findAll();
    }
}
