package khoaluan.BE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import khoaluan.BE.request.LoginRequest;
import khoaluan.BE.request.SignupRequest;
import khoaluan.BE.response.DataResponse;
import khoaluan.BE.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthCotroller {

	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public DataResponse signup(@RequestBody SignupRequest signup) {
		return userService.signup(signup);
	}

	@PostMapping("/login")
	public DataResponse login(@RequestBody LoginRequest login) {
		return userService.login(login);
	}
}
