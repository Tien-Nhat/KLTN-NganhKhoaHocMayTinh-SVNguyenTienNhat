package khoaluan.BE.service;

import khoaluan.BE.request.ReviewRequest;
import khoaluan.BE.response.DataResponse;

public interface ReviewService {
	DataResponse addRating(ReviewRequest reviewRequest);

	DataResponse getRating(Long productId);

	DataResponse isBuy(Long productId, Long userId);
}
