package khoaluan.BE.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import khoaluan.BE.response.DataResponse;
import khoaluan.BE.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	ProductService productService;

	@PostMapping("/add-product")
	public DataResponse getProductUser(MultipartHttpServletRequest producRe) {
		return productService.addProduct(producRe);
	}

	@DeleteMapping("/delete-product")
	public DataResponse deleteProduct(@RequestParam Long productId) {
		return productService.deleteProduct(productId);
	}

	@GetMapping("/get-product-user")
	public DataResponse getProductUser(@RequestParam int pageNumber, @RequestParam int pageSize,
			@RequestParam Long userId) {
		return productService.getProductUser(pageNumber, pageSize, userId);
	}

	@GetMapping("/get-product")
	public DataResponse getProduct(@RequestParam int pageNumber, @RequestParam int pageSize) {
		return productService.getProduct(pageNumber, pageSize);
	}

	@GetMapping("/get-product-info")
	public DataResponse getProductInfo(@RequestParam Long productId) {
		return productService.getProductInfo(productId);
	}

	@GetMapping("/image")
	public ResponseEntity<?> getImage(@RequestParam Long productId) throws IOException {
		return productService.displayImage(productId);
	}

	@GetMapping("/get-product-category")
	public DataResponse getProduct(@RequestParam int pageNumber, @RequestParam int pageSize,
			@RequestParam Long categoryId) {
		return productService.getProductCategory(pageNumber, pageSize, categoryId);
	}

}
