package com.fashionshop.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fashionshop.dto.order.OrderResponse;
import com.fashionshop.dto.order.PlaceOrderRequest;
import com.fashionshop.enums.ImageType;
import com.fashionshop.enums.OrderStatus;
import com.fashionshop.mapper.OrderMapper;
import com.fashionshop.model.Cart;
import com.fashionshop.model.CartItem;
import com.fashionshop.model.Color;
import com.fashionshop.model.Order;
import com.fashionshop.model.OrderItem;
import com.fashionshop.model.Payment;
import com.fashionshop.model.Product;
import com.fashionshop.model.ProductImage;
import com.fashionshop.model.ProductVaraint;
import com.fashionshop.model.Size;
import com.fashionshop.model.User;
import com.fashionshop.repository.CartItemRepository;
import com.fashionshop.repository.CartRepository;
import com.fashionshop.repository.OrderRepository;
import com.fashionshop.repository.ProductVariantRepository;
import com.fashionshop.repository.UserRepository;
import com.fashionshop.service.OrderService;
import com.fashionshop.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductVariantRepository productVariantRepository;
	private final UserRepository userRepository;
	private final OrderMapper orderMapper;
	private final UserService userService;
	
	private Long getCurrentUserId() {
		User user = userService.getCurrentUser();
		return (user!=null) ? user.getId() : null;
	}
	
	private Cart getCart(String sessionId) {
		Long userId = getCurrentUserId(); 
		
		if (userId != null)
			return cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found."));
		return cartRepository.findBySessionId(sessionId).orElseThrow(() -> new RuntimeException("Cart not found."));
	}

	@Override
	@Transactional
	public OrderResponse placeOrder(String sessionId, PlaceOrderRequest request) {
		Long userId = getCurrentUserId();
		
		Cart cart = getCart(sessionId);
		if (cart.getCartItems().isEmpty()) {
			throw new RuntimeException("The cart is empty, unable to place an order.");
		}

//		Tạo Order
		Order order = Order.builder().receiverName(request.getReceiverName()).phone(request.getPhone())
				.province(request.getProvince()).district(request.getDistrict()).ward(request.getWard())
				.street(request.getStreet()).status(OrderStatus.PENDING).shippingFee(30000.0)
				.orderItems(new ArrayList<>()).payments(new ArrayList<>()).build();

		if (userId != null) {
			User user = userRepository.findById(userId).orElse(null);
			order.setUser(user);
		}

		double totalAmount = 0;

//		Duyệt giỏ hàng xong rồi chuển thành OrderItem sau đó sẽ trừ stock
		for (CartItem cartItem : cart.getCartItems()) {
			ProductVaraint variant = cartItem.getVariant();
			Product product = variant.getProductColor().getProduct();
			Color color = variant.getProductColor().getColor();
			Size size = variant.getSize();
//			Kiểm tra tổn kho nè
			if (variant.getStock() < cartItem.getQuantity()) {
//				String productName = variant.getProductColor().getProduct().getName();
//				String colorName = variant.getProductColor().getColor().getName();
//				String sizeName = variant.getSize().getName();
//				throw new RuntimeException("The product " + productName + " (" + colorName + " - " + sizeName + ") does not have sufficient stock");
				throw new RuntimeException("The product " + product.getName() + " (" + color.getName() + " - "
						+ size.getName() + ") does not have sufficient stock");
			}

			variant.setStock(variant.getStock() - cartItem.getQuantity());
			productVariantRepository.save(variant);

//			Lấy giá, ưu tiên lấy giá của variant, nếu không có(null) thì sẽ lấy giá gốc là giá bên product
			double price = variant.getPrice() != null ? variant.getPrice()
					: variant.getProductColor().getProduct().getBasePrice();

//			Tìm ảnh main để hiển thị trong order
			String imageUrl = variant.getProductColor().getImages().stream()
					.filter(img -> img.getImageType() == ImageType.MAIN).findFirst().map(ProductImage::getImageUrl)
					.orElse(null);

//			Tạo OrderItem nè
			OrderItem orderItem = OrderItem.builder().order(order).variant(variant).quantity(cartItem.getQuantity())
					.unitPrice(price).productImage(imageUrl).productName(product.getName()).colorName(color.getName())
					.sizeName(size.getName()).build();
			order.getOrderItems().add(orderItem);
			totalAmount += (price * cartItem.getQuantity());
		}
		order.setTotalAmount(totalAmount);

		Payment payment = Payment.builder().order(order).paymentMethod(request.getPaymentMethod())
				.amount(totalAmount + order.getShippingFee()).paymentStatus(false).build();
		order.getPayments().add(payment);

		Order saved = orderRepository.save(order);
		cartItemRepository.deleteAll(cart.getCartItems());
		return orderMapper.toOrderResponse(saved);
	}

	@Override
	public List<OrderResponse> getMyOrders() {
		Long userId = getCurrentUserId();
		if(userId != null) {
			throw new RuntimeException("You must login to view your orders.");
		}
		List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(userId);

		return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
	}

	@Override
	public List<OrderResponse> getAllOrders() {
		List<Order> orders = orderRepository.findAllByOrderByOrderDateDesc();

		return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found."));

//		Kiểm tra trạng thái của đơn hàng nếu đã giao rồi thì không được hủy
		if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
			throw new RuntimeException("The order has been completed and its status cannot be changed.");
		}

//		khi hủy đơn thì sẽ hoàn lại stock
		if (newStatus == OrderStatus.CANCELLED) {
			for (OrderItem item : order.getOrderItems()) {
				ProductVaraint variant = item.getVariant();
				variant.setStock(variant.getStock() + item.getQuantity());
				productVariantRepository.save(variant);
			}
		}

		if (newStatus == OrderStatus.DELIVERED) {
			if(!order.getPayments().isEmpty()) {
			order.getPayments().get(0).setPaymentStatus(true);
			}
		}

		order.setStatus(newStatus);

		Order saved = orderRepository.save(order);
		return orderMapper.toOrderResponse(saved);
	}

}
