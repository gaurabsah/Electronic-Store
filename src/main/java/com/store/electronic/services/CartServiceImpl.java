package com.store.electronic.services;

import com.store.electronic.dtos.AddItemToCartRequest;
import com.store.electronic.dtos.CartDto;
import com.store.electronic.entities.Cart;
import com.store.electronic.entities.CartItem;
import com.store.electronic.entities.Product;
import com.store.electronic.entities.User;
import com.store.electronic.exceptions.ResourceNotFoundException;
import com.store.electronic.repositories.CartItemRepository;
import com.store.electronic.repositories.CartRepository;
import com.store.electronic.repositories.ProductRepository;
import com.store.electronic.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest addItemToCartRequest) {
        String productId = addItemToCartRequest.getProductId();
        int quantity = addItemToCartRequest.getQuantity();
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
//        fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//        fetch the user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        suppose the cart does not exist
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCreatedAt(new Date());
            cart.setCartId(UUID.randomUUID().toString());
        }
//        perform cart operations
//        if items in cart already exist
//        boolean updated=false;
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> cartItems = cart.getCartItems();
        List<CartItem> updatedCartItems = cartItems.stream().map(cartItem -> {
            if (cartItem.getProduct().getProductId().equals(productId)) {
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice(quantity * product.getDiscountedPrice());
//                updated=true;
                updated.set(true);
            }
            return cartItem;
        }).collect(Collectors.toList());

        cart.setCartItems(updatedCartItems);

//        create items
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getCartItems().add(cartItem);
        }

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        logger.info("Cart updated {}", updatedCart.getCartId());
        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void removeAllItemsFromCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        logger.info("fetching user {}", userId);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        logger.info("fetching cart {}", cart.getCartId());
        cart.getCartItems().clear();
        logger.info("Removing all items from cart");
        cartRepository.save(cart);
    }

    @Override
    public CartDto fetchCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        logger.info("fetching user {}", userId);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        logger.info("fetching cart {}", cart.getCartId());
        logger.info("fetching cart items ");
        return mapper.map(cart, CartDto.class);
    }
}
