package com.market.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.domain.ProductRequestDTO;
import com.market.domain.ProductResponseDTO;
import com.market.entity.Product;
import com.market.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	private final ProductService ps;
	
	@GetMapping("/product")
	@ResponseBody
	public List<ProductResponseDTO> getProducts(@RequestParam(name = "keyword", required = false) String keyword) {
		System.out.println(keyword);
		List<ProductResponseDTO> productList = null;
		if (keyword != null) {
			productList = ps.searchProducts(keyword);
		} else {
			productList = ps.getAllProducts();			
		}
		return productList;
	}
	
	@PostMapping("/product")
	@ResponseBody
	public ProductResponseDTO postProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		System.out.println(productRequestDTO);
		ProductResponseDTO product = ps.addProduct(productRequestDTO);
		return product;
	}
	
	@DeleteMapping("/product/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
		boolean isDeleted = ps.deleteProduct(id);
		if (isDeleted) {
			return ResponseEntity.ok().build();			
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Patch: 부분
	@PatchMapping("/product")
	@ResponseBody
	public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		System.out.println(productRequestDTO);
		ProductResponseDTO product = ps.updateProduct(productRequestDTO);
		if (product == null) return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(product);
	}
	
}
