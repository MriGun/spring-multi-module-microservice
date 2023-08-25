package com.mri.inventoryservice.service;

import com.mri.inventoryservice.dto.InventoryResponse;
import com.mri.inventoryservice.model.Inventory;
import com.mri.inventoryservice.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
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