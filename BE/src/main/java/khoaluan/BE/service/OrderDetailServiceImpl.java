package khoaluan.BE.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import khoaluan.BE.model.Cart;
import khoaluan.BE.model.CartItem;
import khoaluan.BE.model.Order;
import khoaluan.BE.model.OrderDetail;
import khoaluan.BE.model.User;
import khoaluan.BE.repository.CartItemRepository;
import khoaluan.BE.repository.CartRepository;
import khoaluan.BE.repository.CategoryReponsitory;
import khoaluan.BE.repository.OrderDetailReponsitory;
import khoaluan.BE.repository.OrderReponsitory;
import khoaluan.BE.repository.ProductReponsitory;
import khoaluan.BE.repository.UserRepository;
import khoaluan.BE.response.CartItemResponse;
import khoaluan.BE.response.DataResponse;
import khoaluan.BE.response.OrderDetailResponse;
import util.Constant;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	ProductReponsitory productReponsitory;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryReponsitory categoryReponsitory;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	OrderReponsitory orderReponsitory;

	@Autowired
	OrderDetailReponsitory orderDetailReponsitory;

	@Override
	public DataResponse addOder(Long userId) {
		DataResponse res = new DataResponse();
		try {
			User user = userRepository.findById(userId).get();
			Order order = new Order();
			order.setUser(user);
			Order newOrder = orderReponsitory.save(order);
			Cart cart = cartRepository.findByUser(user);
			List<CartItem> listCartItem = cartItemRepository.findByCartIdAndRemovedFalse(cart.getId());
			List<OrderDetail> listOrderDetail = new ArrayList<>();

			for (CartItem cartItem : listCartItem) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(newOrder);
				orderDetail.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
				orderDetail.setProduct(cartItem.getProduct());
				orderDetail.setQuatity(cartItem.getQuantity());
				listOrderDetail.add(orderDetail);
			}

			orderDetailReponsitory.saveAll(listOrderDetail);
			cartItemRepository.setRemovedTrueByCartId(cart.getId());
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Đặt hàng thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Đặt hàng thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public DataResponse getOrder(Long userId) {
		DataResponse res = new DataResponse();
		try {
			List<Long> listOrderId = orderReponsitory.findOrderIdsByUserId(userId);
			List<OrderDetailResponse> listOrder = new ArrayList<>();
			for (Long orderId : listOrderId) {
				OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
				List<OrderDetail> orderDetail = orderDetailReponsitory.findByOrderId(orderId);
				List<CartItemResponse> listOrderDetailResponse = new ArrayList<>();

				for (OrderDetail product : orderDetail) {
					CartItemResponse orderDetailRe = new CartItemResponse();
					orderDetailRe.setId(product.getProduct().getId());
					orderDetailRe.setImage(product.getProduct().getImage());
					orderDetailRe.setName(product.getProduct().getName());
					orderDetailRe.setPrice(product.getProduct().getPrice());
					orderDetailRe.setProductId(product.getProduct().getId());
					orderDetailRe.setQuantity(product.getQuatity());
					listOrderDetailResponse.add(orderDetailRe);
				}
				orderDetailResponse.setOrderId(orderId);
				orderDetailResponse.setOrderItem(listOrderDetailResponse);
				listOrder.add(orderDetailResponse);
			}
			res.setResult(listOrder);
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy danh sách đơn hàng thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy danh sách đơn hàng thất bại");
			e.printStackTrace();
		}

		return res;
	}

}
