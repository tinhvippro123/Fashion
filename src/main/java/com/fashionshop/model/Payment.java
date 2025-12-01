package com.fashionshop.model;

import java.time.LocalDateTime;

import com.fashionshop.enums.PaymentMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
	private Order order;
	
	private PaymentMethod paymentMethod;
	
	private Double amount;
	
	private Boolean paymentStatus;
	
	private LocalDateTime paymentDate;
	
	private String transactionId;
}
