package com.fashionshop.model;

import java.time.LocalDateTime;

import com.fashionshop.enums.PaymentMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Order order;
	
	private PaymentMethod paymentMethod;
	
	private Double amount;
	
	private Boolean paymentStatus;
	
	private LocalDateTime paymentDate;
	
	private String transactionId;
}
