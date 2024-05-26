package khoaluan.BE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import khoaluan.BE.request.CartItemRequest;
import khoaluan.BE.response.DataResponse;
import khoaluan.BE.service.CartItemService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartItemController {
	@Autowired
	CartItemService cartItemService;

	@PostMapping("/add-cart")
	public DataResponse addCart(@RequestBody CartItemRequest cartItemRequest) {
		return cartItemService.addItem(cartItemRequest);
	}

	@GetMapping("/get-list-cart")
	public DataResponse getListCart(@RequestParam Long userId) {
		return cartItemService.getListItem(userId);
	}

	@DeleteMapping("/remove-item")
	public DataResponse removeItem(@RequestParam Long cartItemId) {
		return cartItemService.removeItem(cartItemId);
	}
}
