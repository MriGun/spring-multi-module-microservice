package com.mri.orderservice.service;

import com.mri.orderservice.dto.OrderLineItemsDto;
import com.mri.orderservice.dto.OrderRequest;
import com.mri.orderservice.entity.Order;
import com.mri.orderservice.entity.OrderLineItems;
import com.mri.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    public void createOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderName(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);

        //call to inventory service. if in stock then place order
        orderRepo.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
