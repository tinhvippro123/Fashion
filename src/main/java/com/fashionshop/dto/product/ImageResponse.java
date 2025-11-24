package com.fashionshop.dto.product;

import com.fashionshop.enums.ImageType;

import lombok.Data;

@Data
public class ImageResponse {
	private Long id;
	private String imageUrl;
	private ImageType imageType;
	private Integer sortOrder;
}
