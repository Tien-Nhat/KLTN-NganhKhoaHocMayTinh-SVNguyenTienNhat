package khoaluan.BE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import khoaluan.BE.response.DataResponse;
import khoaluan.BE.service.CategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryCotroller {
	@Autowired
	CategoryService categoryService;

	@GetMapping("/get-category")
	public DataResponse getCategory() {
		return categoryService.getCategory();
	}
}
