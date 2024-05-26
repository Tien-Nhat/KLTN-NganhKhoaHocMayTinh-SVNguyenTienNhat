package khoaluan.BE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import khoaluan.BE.request.ReviewRequest;
import khoaluan.BE.response.DataResponse;
import khoaluan.BE.service.ReviewService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/review")
public class ReviewCotroller {
	@Autowired
	ReviewService reviewService;

	@PostMapping("/add-rating")
	public DataResponse getProductUser(@RequestBody ReviewRequest reviewRequest) {
		return reviewService.addRating(reviewRequest);
	}

	@GetMapping("/get-rating")
	public DataResponse getRatingUser(@RequestParam Long productId) {
		return reviewService.getRating(productId);
	}

	@GetMapping("/is-buy")
	public DataResponse isBuy(@RequestParam Long productId, @RequestParam Long userId) {
		return reviewService.isBuy(productId, userId);
	}
}
