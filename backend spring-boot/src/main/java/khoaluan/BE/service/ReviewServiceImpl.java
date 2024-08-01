package khoaluan.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import khoaluan.BE.model.Product;
import khoaluan.BE.model.Review;
import khoaluan.BE.model.User;
import khoaluan.BE.repository.OrderDetailReponsitory;
import khoaluan.BE.repository.ProductReponsitory;
import khoaluan.BE.repository.ReviewRepository;
import khoaluan.BE.repository.UserRepository;
import khoaluan.BE.request.ReviewRequest;
import khoaluan.BE.response.DataResponse;
import khoaluan.BE.response.ReviewResponse;
import util.Constant;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductReponsitory productReponsitory;

	@Autowired
	OrderDetailReponsitory orderDetailReponsitory;

	@Override
	public DataResponse addRating(ReviewRequest reviewRequest) {
		DataResponse res = new DataResponse();
		try {

			User user = userRepository.findById(reviewRequest.getUserId()).get();
			Product product = productReponsitory.findById(reviewRequest.getProductId()).get();
			if (reviewRepository.existsByUserAndProduct(user, product)) {
				Review review = reviewRepository.findByUserAndProduct(user, product);
				review.setRating(reviewRequest.getRating());
				reviewRepository.save(review);
			} else {
				Review review = new Review();
				review.setProduct(product);
				review.setUser(user);
				review.setRating(reviewRequest.getRating());
				reviewRepository.save(review);
			}

			res.setStatus(Constant.SUCCESS);
			res.setMessage("Đánh giá của bạn đã được ghi nhận");

		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Đánh giá bị lỗi");
		}
		return res;
	}

	@Override
	public DataResponse getRating(Long productId) {
		DataResponse res = new DataResponse();
		try {

			Product product = productReponsitory.findById(productId).get();

			if (reviewRepository.existsByProduct(product))
				res.setResult(reviewRepository.findAverageRatingByProductId(productId));
			else
				res.setResult(0);
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy đánh giá thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy đánh giá bị lỗi");
		}
		return res;
	}

	@Override
	public DataResponse isBuy(Long productId, Long userId) {
		DataResponse res = new DataResponse();
		try {
			User user = userRepository.findById(userId).get();
			Product product = productReponsitory.findById(productId).get();
			ReviewResponse reviewResponse = new ReviewResponse();
			reviewResponse.setIsBuy(orderDetailReponsitory.existsByProductIdAndUserId(productId, userId));
			if (reviewRepository.existsByUserAndProduct(user, product))
				reviewResponse.setYourRating(reviewRepository.findByUserAndProduct(user, product).getRating());
			res.setResult(reviewResponse);
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy đánh giá thành công");

		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy đánh giá bị lỗi");
		}
		return res;
	}

}
