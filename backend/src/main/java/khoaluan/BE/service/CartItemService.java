package khoaluan.BE.service;

import khoaluan.BE.request.CartItemRequest;
import khoaluan.BE.response.DataResponse;

public interface CartItemService {
	DataResponse addItem(CartItemRequest cartItemRe);

	DataResponse getListItem(Long userId);

	DataResponse removeItem(Long cartItemId);
}
