package com.fashionshop.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionshop.model.Color;
import com.fashionshop.repository.ColorRepository;
import com.fashionshop.repository.ProductColorRepository;
import com.fashionshop.service.ColorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
	private final ColorRepository colorRepository;
	private final ProductColorRepository productColorRepository;

	@Override
	public List<Color> getAll() {
		return colorRepository.findAll();
	}

	@Override
	public Color create(Color color) {
		if (colorRepository.existsByName(color.getName())) {
			throw new RuntimeException("Color ‘" + color.getName() + "’ already exists.");
		}
		return colorRepository.save(color);
	}

	@Override
	@Transactional
	public Color update(Long id, Color request) {
		Color color = colorRepository.findById(id).orElseThrow(() -> new RuntimeException("Color does not exist."));

		if (!color.getName().equals(request.getName()) && colorRepository.existsByName(request.getName())) {
			throw new RuntimeException("Color ‘" + color.getName() + "’ already exists.");
		}
		
		
		color.setName(request.getName());
		color.setHexCode(request.getHexCode());

		return colorRepository.save(color);
	}

	@Override
	public void delete(Long id) {
		if (colorRepository.existsById(id)) {
			throw new RuntimeException("Color does not exist.");
		}

		if (productColorRepository.existsByColorId(id)) {
			throw new RuntimeException("Cannot delete this color because it is currently being used in products.");
		}

		colorRepository.deleteById(id);
	}

}
