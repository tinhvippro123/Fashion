package com.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fashionshop.model.Size;

public interface SizeRepository extends JpaRepository<Size, Long> {

}
