package org.yearup.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.*;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final ProfileService profileService;

    public OrderService(OrderRepository orderRepository, OrderLineItemRepository orderLineItemRepository, ShoppingCartService shoppingCartService, ProfileService profileService) {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.shoppingCartService = shoppingCartService;
        this.profileService = profileService;
    }

    public Order checkoutService(int userId) {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        if (shoppingCart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No items in cart");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setDate(LocalDateTime.now());

        Profile profile = profileService.getProfileService(userId);
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());

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
