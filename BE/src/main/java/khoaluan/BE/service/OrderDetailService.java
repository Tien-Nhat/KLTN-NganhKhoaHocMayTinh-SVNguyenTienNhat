package khoaluan.BE.service;

import khoaluan.BE.response.DataResponse;

public interface OrderDetailService {
	DataResponse addOder(Long userId);

	DataResponse getOrder(Long userId);
}
