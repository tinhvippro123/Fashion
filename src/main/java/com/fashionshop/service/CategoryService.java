package com.fashionshop.service;

import java.util.List;

import com.fashionshop.model.Category;

public interface CategoryService {
	List<Category> getAll();
	
	Category create(Category category);
	
	Category update(Long id,Category request);
	
	void delete(Long id);
}
