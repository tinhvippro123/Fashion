package com.fashionshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fashionshop.dto.product.ImageResponse;
import com.fashionshop.dto.product.ProductColorResponse;
import com.fashionshop.dto.product.ProductDetailResponse;
import com.fashionshop.dto.product.VariantResponse;
import com.fashionshop.model.Product;
import com.fashionshop.model.ProductColor;
import com.fashionshop.model.ProductImage;
import com.fashionshop.model.ProductVaraint;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	//Map từ Product sang ProductDetailResponse
	@Mapping(target = "categoryName", source = "category.name")
	@Mapping(target = "colors", source = "productColors")
	ProductDetailResponse toDetailResponse(Product product);
	
	// Map từ ProductColor sang ProductColorResponse
	@Mapping(target = "colorName", source = "color.name")
	@Mapping(target = "hexCode", source = "color.hexCode")
	@Mapping(target = "colorId", source = "color.id")
	@Mapping(target = "images", source = "images")
	@Mapping(target = "variants", source = "variants")
	ProductColorResponse toProductColorResponse(ProductColor productColor);
	
	// Map từ ProductImage sang ImageResponse
	ImageResponse toImageResponse(ProductImage image);
	
	// Map từ ProductVariant sang VariantResponse
	@Mapping(target = "sizeName", source = "size.name")
	@Mapping(target = "sizeId", source = "size.id")
	VariantResponse toVariantResponse(ProductVaraint variant);
	
}
