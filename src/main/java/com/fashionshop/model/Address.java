package com.fashionshop.model;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
	@Nationalized
	private String receiverName;
	
	@Column(nullable = false, length = 10)
	private String phone;
	
	@Column(nullable = false)
	@Nationalized
	private String province;
	
	@Column(nullable = false)
	@Nationalized
	private String district;
	
	@Column(nullable = false)
	@Nationalized
	private String ward;

	@Column(nullable = false)
	@Nationalized
	private String street;
	
	private Boolean isDefault;
}
