package com.fashionshop.service.implement;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionshop.dto.product.ProductCreateRequest;
import com.fashionshop.dto.product.ProductDetailResponse;
import com.fashionshop.mapper.ProductMapper;
import com.fashionshop.model.Category;
import com.fashionshop.model.Color;
import com.fashionshop.model.Product;
import com.fashionshop.model.ProductColor;
import com.fashionshop.model.ProductImage;
import com.fashionshop.model.ProductVaraint;
import com.fashionshop.model.Size;
import com.fashionshop.repository.CategoryRepository;
import com.fashionshop.repository.ColorRepository;
import com.fashionshop.repository.ProductRepository;
import com.fashionshop.repository.SizeRepository;
import com.fashionshop.service.ProductService;
import com.fashionshop.utils.SlugUtils;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ColorRepository colorRepository;
	private final SizeRepository sizeRepository;
	private final ProductMapper productMapper;;
	
	@Override
	@Transactional
	public ProductDetailResponse createProduct(ProductCreateRequest request) {
//		Tạo slug
		String slug = SlugUtils.toSlug(request.getName());
		
		if(productRepository.existsBySlug(slug)) {
			throw new RuntimeException("A product with this name already exists, please choose a different name!");
		}
		
//		Tìm Cateogory
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
		
//		Tạo product
		Product product = Product.builder()
				.name(request.getName())
				.description(request.getDescription())
				.slug(slug)
				.basePrice(request.getBasePrice())
				.description(request.getDescription())
				.category(category)
				.productColors(new ArrayList<>())
				.isActive(true)
				.build();
		
//		Duyệt qua danh sách ColorRequest để tạo ProductColor
		for(ProductCreateRequest.ColorGroupRequest colorRequest : request.getColors()) {
			Color color = colorRepository.findById(colorRequest.getColorId()).orElseThrow(() -> new RuntimeException("Color not found ID "+ colorRequest.getColorId()));
		
			
//			Tạo ProductColor
			ProductColor productColor = ProductColor.builder()
					.product(product)
					.color(color)
					.isDefault(colorRequest.getIsDefault())
					.images(new ArrayList<>())
					.variants(new ArrayList<>())
					.build();
		
//			Xử lý Images
			if(colorRequest.getImages() != null) {
				for(ProductCreateRequest.ImageRequest imageRequest : colorRequest.getImages()) {
					ProductImage image = ProductImage.builder()
							.productColor(productColor)
							.imageUrl(imageRequest.getImageUrl())
							.imageType(imageRequest.getImageType())
							.sortOrder(imageRequest.getSortOrder())
							.build();
					productColor.getImages().add(image);
				}
			}
			
//			Xử lý variant (Size và Stock)
			if(colorRequest.getVariants() != null) {
				for(ProductCreateRequest.VariantRequest variantRequest : colorRequest.getVariants()) {
					Size size = sizeRepository.findById(variantRequest.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found ID "+ variantRequest.getSizeId()));
				
				ProductVaraint variant = ProductVaraint.builder()
						.productColor(productColor)
						.size(size)
						.stock(variantRequest.getStock())
						.price(variantRequest.getPrice() != null ? variantRequest.getPrice() : product.getBasePrice())
						.status(true)
						.build();
				productColor.getVariants().add(variant);
				}
				
//				Add nhom1 màu này vào Product
				product.getProductColors().add(productColor);
			}
			
			Product saved = productRepository.save(product);
			
			return productMapper.toDetailResponse(saved);
			
		}

		
		
		return null;
	}
	
//	@Override
//	@Transactional
//	public List<ProductDetailResponse> getAllProduct() {
//		return productRepository.findAll().stream().map(productMapper::toDetailResponse).collect(Collectors.toList());
//	}

	
	public Page<ProductDetailResponse> getAllProducts(int page, int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Product> productPage = productRepository.findByIsActiveTrue(pageable);
		return productPage.map(productMapper::toDetailResponse);
	}
	
	
	@Override
	public ProductDetailResponse getProductBySlug(String slug) {
		Product product = productRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Product not found with slug "+slug)); 
		return productMapper.toDetailResponse(product);
	}
	
}
