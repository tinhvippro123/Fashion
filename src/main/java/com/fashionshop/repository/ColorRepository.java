package com.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fashionshop.model.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {

}
