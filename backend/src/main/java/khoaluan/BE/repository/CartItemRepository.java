package khoaluan.BE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import khoaluan.BE.model.CartItem;
import khoaluan.BE.model.Product;
import khoaluan.BE.response.CartItemResponse;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	@Query("SELECT new khoaluan.BE.response.CartItemResponse(ci.id, ci.product.id, ci.product.name, ci.product.image, ci.quantity, ci.product.price) FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.removed=false")
	List<CartItemResponse> getCartItemResponsesByCartId(Long cartId);

	List<CartItem> findByCartIdAndRemovedFalse(Long cartId);

	Boolean existsByCartIdAndProduct(Long cartId, Product product);

	CartItem findByCartIdAndProductId(Long cartId, Long productId);

	@Modifying
	@Transactional
	@Query("UPDATE CartItem ci SET ci.removed = true WHERE ci.cart.id = ?1")
	void setRemovedTrueByCartId(Long cartId);
}
