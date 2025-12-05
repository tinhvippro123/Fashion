package com.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fashionshop.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	boolean existsByParentId(Long parentId);
	boolean existsByName(String name);
	boolean existsBySlug(String slug);
}
