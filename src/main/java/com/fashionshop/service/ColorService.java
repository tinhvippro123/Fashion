package com.fashionshop.service;

import java.util.List;

import com.fashionshop.model.Color;

public interface ColorService {
	List<Color> getAll();
	
	Color create(Color color);
	
	Color update(Long id, Color color);
	
	void delete(Long id);
}
