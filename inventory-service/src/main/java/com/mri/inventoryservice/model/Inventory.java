package com.mri.inventoryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_inventory")
public class Inventory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inventory_id_seq")
    @SequenceGenerator(
            sequenceName = "SEQ_INVENTORY_ID",
            name = "inventory_id_seq")
    private Long id;
    private String skuCode;
    private Integer quantity;
}
