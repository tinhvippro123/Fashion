package com.fashionshop.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.fashionshop.model.Product;

public class ProductSpecification {
//	Lọc theo tên(tìm gần đúng nha)
	public static Specification<Product> hasName(String name) {
		return (root, query, criteriaBuilder) -> {
			if (name == null || name.isEmpty())
				return null;
			return criteriaBuilder.like(criteriaBuilder.lower(root.get("Name")), "%" + name.toLowerCase() + "%");
		};
	}

//	Lọc theo danh mục
	public static Specification<Product> hasCategory(Long categoryId) {
		return (root, query, criteriaBuilder) -> {
			if (categoryId == null)
				return null;
			return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
		};
	}

//	Lọc theo khoảng giá
	public static Specification<Product> priceBetween(Double minPrice, Double maxPrice) {
		return (root, query, criteriaBuilder) -> {
			if (minPrice == null && maxPrice == null)
				return null;

			if (minPrice != null && maxPrice != null) {
				return criteriaBuilder.between(root.get("basePrice"), minPrice, maxPrice);
			} else if (minPrice != null) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get("basePrice"), minPrice);
			} else {
				return criteriaBuilder.lessThanOrEqualTo(root.get("basePrice"), maxPrice);
			}
		};
	}

	public static Specification<Product> isActive() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isActive"));
	}
}
