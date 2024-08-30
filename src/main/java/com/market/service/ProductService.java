package com.market.service;

import java.util.List;

import com.market.domain.ProductRequestDTO;
import com.market.domain.ProductResponseDTO;

public interface ProductService {

	List<ProductResponseDTO> getAllProducts();

	ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

	List<ProductResponseDTO> searchProducts(String keyword);

	boolean deleteProduct(Long id);

	ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO);

}
