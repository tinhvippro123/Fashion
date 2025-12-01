package com.fashionshop.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionshop.dto.cart.CartItemRequest;
import com.fashionshop.dto.cart.CartResponse;
import com.fashionshop.enums.ImageType;
import com.fashionshop.model.Cart;
import com.fashionshop.model.CartItem;
import com.fashionshop.model.Product;
import com.fashionshop.model.ProductImage;
import com.fashionshop.model.ProductVaraint;
import com.fashionshop.model.User;
import com.fashionshop.repository.CartItemRepository;
import com.fashionshop.repository.CartRepository;
import com.fashionshop.repository.ProductVariantRepository;
import com.fashionshop.repository.UserRepository;
import com.fashionshop.service.CartService;
import com.fashionshop.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductVariantRepository variantRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	private Long getCurrentUserId() {
		User user = userService.getCurrentUser();
		return (user != null) ? user.getId() : null;
	}

	@Override
	@Transactional
	public CartResponse addToCart(String sessionId, CartItemRequest request) {
//		 Tìm hoặc là tạo giỏ hàng
		Cart cart = getOrCreateCart(sessionId);

//		Tìm Variant
		ProductVaraint variant = variantRepository.findById(request.getVariantId())
				.orElseThrow(() -> new RuntimeException("The product does not exist."));

//		Kiểm tra sản phẩm đã có trong giỏ chưa, nếu có rồi thì cộng số lượng
		Optional<CartItem> existingItem = cart.getCartItems().stream()
				.filter(item -> item.getVariant().getId().equals(variant.getId())).findFirst();

		if (existingItem.isPresent()) {
//			Nếu có thì công dồn số lượng nha
			CartItem item = existingItem.get();
			int newQuantity = item.getQuantity() + request.getQuantity();

			if (newQuantity > variant.getStock()) {
				throw new RuntimeException("Only " + variant.getStock() + " items left in stock");
			}
			item.setQuantity(newQuantity);
		} else {
			if (request.getQuantity() > variant.getStock()) {
				throw new RuntimeException("Only " + variant.getStock() + " items left in stock");
			}

			CartItem newItem = CartItem.builder().cart(cart).variant(variant).quantity(request.getQuantity()).build();
			cart.getCartItems().add(newItem);
		}

		Cart saveCart = cartRepository.save(cart);
		return mapToResponse(saveCart);
	}

	@Override
	public CartResponse getCart(String sessionId) {
		Cart cart = getOrCreateCart(sessionId);
		return mapToResponse(cart);
	}

//	Hàm tìm hoặc tạo giỏ hàng
	private Cart getOrCreateCart(String sessionId) {
		Long userId = getCurrentUserId();
		Optional<Cart> cartOptional;

		if (userId != null) {
			cartOptional = cartRepository.findByUserId(userId);
			if (cartOptional.isPresent())
				return cartOptional.get();
		}

		if (sessionId != null) {
			cartOptional = cartRepository.findBySessionId(sessionId);
			if (cartOptional.isPresent())
				return cartOptional.get();
		}

		Cart newCart = new Cart();
		if (userId != null) {
			User user = userRepository.findById(userId).orElse(null);
			newCart.setUser(user);
		}
		newCart.setSessionId(sessionId);

		return cartRepository.save(newCart);
	}

//	Map Entity sang DTO
	private CartResponse mapToResponse(Cart cart) {
		double totalAmount = 0;

		List<CartResponse.CartItemResponse> itemResponses = new ArrayList<>();

		for (CartItem item : cart.getCartItems()) {
			ProductVaraint v = item.getVariant();
			Product p = v.getProductColor().getProduct();

//			Lưu Price theo variant, nếu variant null thì lấy của product
			double price = v.getPrice() != null ? v.getPrice() : p.getBasePrice();
			double subTotal = price * item.getQuantity();
			totalAmount += subTotal;

//			Tìm ảnh MAIN trong nhóm màu đóa
			String imageUrl = v.getProductColor().getImages().stream()
					.filter(img -> img.getImageType() == ImageType.MAIN).findFirst().map(ProductImage::getImageUrl)
					.orElse("");

			itemResponses.add(CartResponse.CartItemResponse.builder().itemId(item.getId()).variantId(v.getId())
					.productName(p.getName()).colorName(v.getProductColor().getColor().getName())
					.sizeName(v.getSize().getName()).price(price).quantity(item.getQuantity()).subTotal(subTotal)
					.imageUrl(imageUrl).build());
		}

		return CartResponse.builder().id(cart.getId()).items(itemResponses).totalAmount(totalAmount).build();
	}

	@Override
	@Transactional
	public CartResponse removeFromCart(String sessionId, Long cartItemId) {
//		Lấy giỏ hàng hiện tại ra
		Cart cart = getOrCreateCart(sessionId);

//		Tìm item cần xóa
//		Check xem item có thuộc giỏ hàng không, để tranh hacker xóa giỏ người khác ấy mà
		Optional<CartItem> itemToDelete = cart.getCartItems().stream().filter(item -> item.getId().equals(cartItemId))
				.findFirst();

		if (itemToDelete.isPresent()) {
			CartItem item = itemToDelete.get();

//			Xóa khỏi cart
			cart.getCartItems().remove(item);

//			Xóa luôn khỏi database
			cartItemRepository.delete(item);
		} else {
			throw new RuntimeException("The product does not exist in this cart");
		}

		return mapToResponse(cart);
	}

	@Override
	@Transactional
	public CartResponse updateItemQuantity(String sessionId, Long itemId, Integer newQuantity) {
		Cart cart = getOrCreateCart(sessionId);

		Optional<CartItem> itemOptional = cart.getCartItems().stream().filter(item -> item.getId().equals(itemId))
				.findFirst();

		if (itemOptional.isPresent()) {
			CartItem item = itemOptional.get();

//			Số lượng <=0 thì xóa luôn
			if (newQuantity <= 0) {
				cart.getCartItems().remove(item);
				cartItemRepository.delete(item);
			}

//			Cập nhật số lượng
			else {
//				Kiểm tra tồn kho
				if (newQuantity > item.getVariant().getStock()) {
					throw new RuntimeException("Only " + item.getVariant().getStock() + " items left in stock");
				}
				item.setQuantity(newQuantity);
			}

		} else {
			throw new RuntimeException("The product does not exist in this cart.");
		}
		Cart saved = cartRepository.save(cart);

		return mapToResponse(saved);
	}

}
