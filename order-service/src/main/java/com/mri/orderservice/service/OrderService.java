package com.mri.orderservice.service;

import com.mri.orderservice.dto.InventoryResponse;
import com.mri.orderservice.dto.OrderLineItemsDto;
import com.mri.orderservice.dto.OrderRequest;
import com.mri.orderservice.entity.Order;
import com.mri.orderservice.entity.OrderLineItems;
import com.mri.orderservice.event.OrderPlacedEvent;
import com.mri.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    public String createOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderName(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream().
                map(orderLineItem -> orderLineItem.getSkuCode())
                .toList();

        //call to inventory service. if in stock then place order
        InventoryResponse[] response = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory/filter-sku-code",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(response).allMatch(res -> res.isInStock());

        if (!allProductsInStock) {
            throw new IllegalArgumentException("Not in inventory");
        }
        orderRepo.save(order);
        kafkaTemplate.send("notificationTopic", OrderPlacedEvent.builder().orderNumber(order.getOrderName()).build());

        return "Order has been placed successfully!";
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
