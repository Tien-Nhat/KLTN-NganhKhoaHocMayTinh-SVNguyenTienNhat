package khoaluan.BE.response;

import java.util.List;

public class OrderDetailResponse {
	private Long orderId;
	private List<CartItemResponse> orderItem;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<CartItemResponse> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<CartItemResponse> orderItem) {
		this.orderItem = orderItem;
	}

}
