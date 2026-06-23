package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart shoppingCart = new ShoppingCart();

        // load the user's cart rows, look up each product, and build the ShoppingCart
        for (CartItem cartItem : shoppingCartRepository.findByUserId(userId)) {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(productService.getById(cartItem.getProductId()));
            shoppingCartItem.setQuantity(cartItem.getQuantity());
            shoppingCart.add(shoppingCartItem);
        }
        return shoppingCart;
    }

    // add additional methods here
    public ShoppingCart addProductService(int userId, int productId) {
        CartItem exist = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (exist == null) {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);
            shoppingCartRepository.save(cartItem);
            return getByUserId(userId);
        } else {
            exist.setQuantity(exist.getQuantity() + 1);
            shoppingCartRepository.save(exist);
            return getByUserId(userId);
        }
    }

    public ShoppingCart updateProduct(int userId, int productId, int quantity) {
        CartItem exist = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        exist.setQuantity(quantity);
        shoppingCartRepository.save(exist);
        return getByUserId(userId);
    }
}
