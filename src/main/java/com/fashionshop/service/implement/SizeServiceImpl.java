package com.fashionshop.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionshop.model.Size;
import com.fashionshop.repository.ProductVariantRepository;
import com.fashionshop.repository.SizeRepository;
import com.fashionshop.service.SizeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
	private final SizeRepository sizeRepository;
	private final ProductVariantRepository productVariantRepository;

	@Override
	public List<Size> getAll() {
		return sizeRepository.findAll();
	}

	@Override
	public Size create(Size size) {
		if(sizeRepository.existsByName(size.getName())) {
			throw new RuntimeException("Size ‘" + size.getName() + "’ already exists.");
		}
		
		return sizeRepository.save(size);
	}

	@Override
	@Transactional
	public Size update(Long id, Size request) {
		Size size = sizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Size does not exist"));
		
		if (!size.getName().equals(request.getName()) && sizeRepository.existsByName(request.getName())) {
			throw new RuntimeException("Color ‘" + size.getName() + "’ already exists.");
		}
		
		size.setName(request.getName());
		size.setSortOrder(request.getSortOrder());

		return sizeRepository.save(size);
	}

	@Override
	public void delete(Long id) {
		if (sizeRepository.existsById(id)) {
			throw new RuntimeException("Size does not exist");
		}

		if (productVariantRepository.existsBySizeId(id)) {
			throw new RuntimeException("Cannot delete this size because it is currently being used in products.");
		}

		sizeRepository.deleteById(id);
	}

}
