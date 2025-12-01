package com.fashionshop.service;

import com.fashionshop.dto.cart.CartItemRequest;
import com.fashionshop.dto.cart.CartResponse;

public interface CartService {
	CartResponse addToCart(Long userId, String sessionId, CartItemRequest request);
	
//	Xem giỏ hàng nha
	CartResponse getCart(Long UserId, String sessionId);
	
	CartResponse removeFromCart(Long userId, String sessionId, Long cartItemId);
	
	CartResponse updateItemQuantity(Long userId, String sessionId, Long itemId, Integer newQuantity);
	
}
	