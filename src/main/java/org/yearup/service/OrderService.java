package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.*;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository, OrderLineItemRepository orderLineItemRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.shoppingCartService = shoppingCartService;
    }

    public Order checkoutService(int userId) {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        Order order = new Order();
        order.setUserId(userId);
        Order orderSaved = orderRepository.save(order);

        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems().values()) {
            OrderLineItem orderLineItem = new OrderLineItem();

            orderLineItem.setOrderId(orderSaved.getOrderId());
            orderLineItem.setProductId(shoppingCartItem.getProductId());
            orderLineItem.setSalesPrice(shoppingCartItem.getProduct().getPrice());
            orderLineItem.setQuantity(shoppingCartItem.getQuantity());

            orderLineItemRepository.save(orderLineItem);
        }

        shoppingCartService.deleteCartService(userId);
        return orderSaved;
    }
}
