package com.fashionshop.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionshop.model.Category;
import com.fashionshop.repository.CategoryRepository;
import com.fashionshop.repository.ProductRepository;
import com.fashionshop.service.CategoryService;
import com.fashionshop.utils.SlugUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;

	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category create(Category category) {
		if(categoryRepository.existsByName(category.getName())) {
			throw new RuntimeException("Category name '" + category.getName() + "' already exists.");
		}
		
		String slug = SlugUtils.toSlug(category.getName());
		if(categoryRepository.existsBySlug(slug)) {
			throw new RuntimeException("Slug '" + category.getName() + "' already exists.");
		}
		
		category.setSlug(slug);
		
		return categoryRepository.save(category);
	}

	@Override
	@Transactional
	public Category update(Long id, Category request) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category does not exists"));

		if(!existingCategory.getName().equals(request.getName())) {
			throw new RuntimeException("Category name already exists.");

		}
		
		String newSlug = SlugUtils.toSlug(request.getName());
		if(categoryRepository.existsBySlug(newSlug)) {
			throw new RuntimeException("Slug already exists.");
		}
		
		existingCategory.setName(request.getName());
		existingCategory.setSlug(newSlug);

		return categoryRepository.save(existingCategory);
	}

	@Override
	public void delete(Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new RuntimeException("Cannot delete this category because it contains existing products.");
		}

		if (categoryRepository.existsByParentId(id)) {
			throw new RuntimeException(
					"Cannot delete: This category contains subcategories. Please delete the subcategories first.");
		}

		if (productRepository.existsByCategoryId(id)) {
			throw new RuntimeException("Cannot delete this category because it contains existing products.");
		}
	}

}
