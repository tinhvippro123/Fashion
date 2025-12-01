package com.fashionshop.service;

import com.fashionshop.dto.cart.CartItemRequest;
import com.fashionshop.dto.cart.CartResponse;

public interface CartService {
	CartResponse addToCart(String sessionId, CartItemRequest request);

//	Xem giỏ hàng nha
	CartResponse getCart(String sessionId);

	CartResponse removeFromCart(String sessionId, Long cartItemId);

	CartResponse updateItemQuantity(String sessionId, Long itemId, Integer newQuantity);

}
