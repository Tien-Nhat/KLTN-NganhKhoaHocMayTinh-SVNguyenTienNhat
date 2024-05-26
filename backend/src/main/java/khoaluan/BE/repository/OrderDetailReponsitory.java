package khoaluan.BE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import khoaluan.BE.model.OrderDetail;

public interface OrderDetailReponsitory extends JpaRepository<OrderDetail, Long> {

	List<OrderDetail> findByOrderId(Long orderId);

	@Query("SELECT COUNT(od) > 0 FROM OrderDetail od " + "JOIN od.order o "
			+ "WHERE od.product.id = :productId AND o.user.id = :userId")
	boolean existsByProductIdAndUserId(Long productId, Long userId);
}
