package khoaluan.BE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import khoaluan.BE.model.Order;

public interface OrderReponsitory extends JpaRepository<Order, Long> {
	@Query("SELECT o.id FROM Order o WHERE o.user.id = ?1 ORDER BY o.id DESC")
	List<Long> findOrderIdsByUserId(Long userId);

	Order findByUserId(Long userId);
}
