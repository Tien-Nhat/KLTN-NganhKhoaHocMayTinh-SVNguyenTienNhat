package khoaluan.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import khoaluan.BE.model.Product;
import khoaluan.BE.model.Review;
import khoaluan.BE.model.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Boolean existsByUserAndProduct(User User, Product product);

	Boolean existsByProduct(Product product);

	Review findByUserAndProduct(User User, Product product);

	@Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
	Double findAverageRatingByProductId(Long productId);
}
