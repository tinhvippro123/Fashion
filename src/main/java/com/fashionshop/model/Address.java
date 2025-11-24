package com.fashionshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserId", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private String receiverName;
	
	@Column(nullable = false, length = 10)
	private String phone;
	
	@Column(nullable = false)
	private String province;
	
	@Column(nullable = false)
	private String district;
	
	@Column(nullable = false)
	private String ward;
	
	@Column(nullable = false)
	private String street;
	
	private boolean isDefault = false;
}
