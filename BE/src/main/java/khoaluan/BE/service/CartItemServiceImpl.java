package khoaluan.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import khoaluan.BE.model.Cart;
import khoaluan.BE.model.CartItem;
import khoaluan.BE.model.Product;
import khoaluan.BE.model.User;
import khoaluan.BE.repository.CartItemRepository;
import khoaluan.BE.repository.CartRepository;
import khoaluan.BE.repository.ProductReponsitory;
import khoaluan.BE.repository.UserRepository;
import khoaluan.BE.request.CartItemRequest;
import khoaluan.BE.response.DataResponse;
import util.Constant;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	ProductReponsitory productReponsitory;

	@Override
	public DataResponse addItem(CartItemRequest cartItemRe) {
		DataResponse res = new DataResponse();
		try {
			User user = userRepository.findById(cartItemRe.getUserId()).get();
			Cart cart = new Cart();

			if (cartRepository.existsByUser(user))
				cart = cartRepository.findByUser(user);
			else {
				cart.setUser(user);
				cart = cartRepository.save(cart);
			}

			CartItem cartItem = new CartItem();
			Product product = productReponsitory.findById(cartItemRe.getProductId()).get();
			if (cartItemRepository.existsByCartIdAndProduct(cart.getId(), product)) {
				cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());
				if (cartItem.getRemoved()) {
					cartItem.setQuantity(cartItemRe.getQuantity());
					cartItem.setRemoved(false);
				} else
					cartItem.setQuantity(cartItem.getQuantity() + cartItemRe.getQuantity());
				cartItemRepository.save(cartItem);
			} else {
				cartItem.setCart(cart);
				cartItem.setProduct(product);
				cartItem.setQuantity(cartItemRe.getQuantity());
				cartItemRepository.save(cartItem);
			}

			res.setStatus(Constant.SUCCESS);
			res.setMessage("Thêm sản phẩm vào giỏ hàng thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Thêm sản phẩm vào giỏ hàng thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public DataResponse getListItem(Long userId) {
		DataResponse res = new DataResponse();
		try {
			User user = userRepository.findById(userId).get();
			Cart cart = cartRepository.findByUser(user);
			res.setResult(cartItemRepository.getCartItemResponsesByCartId(cart.getId()));
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy danh sách thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy danh sách thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public DataResponse removeItem(Long cartItemId) {
		DataResponse res = new DataResponse();
		try {
			CartItem cartItem = cartItemRepository.findById(cartItemId).get();
			cartItem.setRemoved(true);
			cartItemRepository.save(cartItem);

			res.setStatus(Constant.SUCCESS);
			res.setMessage("Bỏ sản phẩm thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Bỏ sản phẩm thất bại");
			e.printStackTrace();
		}

		return res;
	}

}
