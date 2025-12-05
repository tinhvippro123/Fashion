package com.fashionshop.service;

import java.util.List;

import com.fashionshop.model.Size;

public interface SizeService {
	List<Size> getAll();
	
	Size create(Size size);
	
	Size update(Long id, Size request);
	
	void delete(Long id);
}
