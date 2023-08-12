package com.mri.inventoryservice.service;

import com.mri.inventoryservice.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }
}
