package com.fashionshop.dto.product;

import java.util.List;

import com.fashionshop.enums.ImageType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductCreateRequest {
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Description is required")
	private String description;
	
	@NotNull(message = "Base price is required")
	@Positive(message = "Base price must be greater than 0")
	private Double basePrice;
	
	@NotNull(message = "Category ID is required")
	private Long categoryId;
	
	@NotNull(message = "There must be at least one color")
	private List<ColorGroupRequest> colors;
	
	@Data
	public static class ColorGroupRequest {
		@NotNull(message = "Color ID is required")
		private Long colorId;
		
		private Boolean isDefault;
		
		private List<ImageRequest> images;
		
		private List<VariantRequest> variants;
	}
	
	@Data
	public static class ImageRequest {
		@NotBlank(message = "Image URL is required")
		private String imageUrl;
		
		private ImageType imageType = ImageType.GALLERY;
		
		private Integer sortOrder = 0;
	}
	
	@Data
	public static class VariantRequest {
		@NotBlank(message = "SizeId is required")
		private Long sizeId;
		
		@NotNull(message = "Stock quantity is required")
		@Positive(message = "Stock must be greater than or equal to 0")
		private Integer stockQuantity;
		
		@NotNull(message = "Additional price is required")
		private Double price;
	}
}