package khoaluan.BE.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import khoaluan.BE.response.DataResponse;

public interface ProductService {

	DataResponse addProduct(MultipartHttpServletRequest productRequest);

	DataResponse getProductUser(int pageNumber, int pageSize, Long userId);

	DataResponse getProduct(int pageNumber, int pageSize);

	ResponseEntity<?> displayImage(Long productId) throws IOException;

	DataResponse deleteProduct(Long productId);

	DataResponse getProductCategory(int pageNumber, int pageSize, Long categoryId);

	DataResponse getProductInfo(Long productId);

}
