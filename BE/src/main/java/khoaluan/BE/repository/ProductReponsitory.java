package khoaluan.BE.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import khoaluan.BE.model.Product;
import khoaluan.BE.response.ProductDetailResponse;
import khoaluan.BE.response.ProductResponse;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Long> {
	@Query("SELECT new khoaluan.BE.response.ProductResponse(p.id ,p.name, p.description, p.price, p.category.name, p.image)"
			+ " FROM Product p WHERE p.user.id = :userId AND p.deleted = false ORDER BY p.id DESC")
	Page<ProductResponse> getListProductByUserId(Pageable pageable, Long userId);

	@Query("SELECT new khoaluan.BE.response.ProductResponse(p.id ,p.name, p.description, p.price, p.category.name, p.image)"
			+ " FROM Product p WHERE p.deleted = false ORDER BY p.id DESC")
	Page<ProductResponse> getListProduct(Pageable pageable);

	@Query("SELECT new khoaluan.BE.response.ProductResponse(p.id ,p.name, p.description, p.price, p.category.name, p.image) FROM Product p WHERE p.category.id = ?1 AND p.deleted = false ORDER BY p.id DESC")
	Page<ProductResponse> getListProductByCategoryId(Pageable pageable, Long categoryId);

	@Query("SELECT new khoaluan.BE.response.ProductDetailResponse(p.id ,p.name, p.description, p.price, p.category, p.image) FROM Product p WHERE p.id = ?1")
	ProductDetailResponse getProductResponseByProductId(Long productId);
}
