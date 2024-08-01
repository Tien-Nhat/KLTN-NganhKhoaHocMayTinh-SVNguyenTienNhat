package khoaluan.BE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import khoaluan.BE.response.DataResponse;
import khoaluan.BE.service.OrderDetailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderCotroller {
	@Autowired
	OrderDetailService orderDetailService;

	@PostMapping("/add-oder")
	public DataResponse addCart(@RequestParam Long userId) {
		return orderDetailService.addOder(userId);
	}

	@GetMapping("/get-order")
	public DataResponse getOrder(@RequestParam Long userId) {
		return orderDetailService.getOrder(userId);
	}

}
