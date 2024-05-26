package khoaluan.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import khoaluan.BE.model.Cart;
import khoaluan.BE.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Boolean existsByUser(User user);

	Cart findByUser(User user);
}
