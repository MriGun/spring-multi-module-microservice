package com.mri.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_order_line_items")
public class OrderLineItems {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_line_item_id_seq")
    @SequenceGenerator(
            sequenceName = "SEQ_ORDER_LINE_ITEM_ID",
            name = "order_line_item_id_seq")
    private Long id;
    private BigDecimal price;
    private String skuCode;
    private Integer quantity;
}
