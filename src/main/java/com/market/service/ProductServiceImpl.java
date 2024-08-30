package com.market.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.market.domain.ProductRequestDTO;
import com.market.domain.ProductResponseDTO;
import com.market.entity.Product;
import com.market.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		return productRepository.findAll().stream().map(product -> ProductResponseDTO.setDTO(product)).collect(Collectors.toList());
	}

	@Override
	public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
		Product product = productRequestDTO.setEntity();
		Product savedProduct = productRepository.save(product);
		return ProductResponseDTO.setDTO(savedProduct);
	}

	@Override
	public List<ProductResponseDTO> searchProducts(String keyword) {
		return productRepository
			.findAllByNameContainingOrDescriptionContaining(keyword, keyword)
			.stream()
			.map(product -> ProductResponseDTO.setDTO(product))
			.collect(Collectors.toList());
	}

	@Override
	public boolean deleteProduct(Long id) {
		Optional<Product> optProduct = productRepository.findById(id);
		if (optProduct.isEmpty()) return false;
		
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO) {
		Optional<Product> optProduct = productRepository.findById(productRequestDTO.getId());
		if (optProduct.isEmpty()) {
			return null;
		}
		Product product = optProduct.get();
		product.setName(productRequestDTO.getName());
		product.setDescription(productRequestDTO.getDescription());
		product.setPrice(productRequestDTO.getPrice());
		
		Product updatedProduct = productRepository.save(product);
		return ProductResponseDTO.setDTO(updatedProduct);
	}
}
