package com.store.electronic.services;

import com.store.electronic.dtos.AddItemToCartRequest;
import com.store.electronic.dtos.CartDto;

public interface CartService {

//    add items to cart
//    case 1: if cart for user is not available then we will create cart and add items
//    case 2: if cart for user is available then we will add items to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest addItemToCartRequest);

    //    remove items from cart
    void removeItemFromCart(String userId, int cartItemId);

    //    remove all items from cart
    void removeAllItemsFromCart(String userId);

    //    fetch cart for user
    CartDto fetchCart(String userId);
}
