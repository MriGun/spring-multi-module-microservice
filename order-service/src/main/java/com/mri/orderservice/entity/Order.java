package com.mri.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_orders")
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_id_seq")
    @SequenceGenerator(
            sequenceName = "SEQ_ORDER_ID",
            name = "order_id_seq")
    private Long id;

    private String orderNumber;
    private String orderName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList;
}
