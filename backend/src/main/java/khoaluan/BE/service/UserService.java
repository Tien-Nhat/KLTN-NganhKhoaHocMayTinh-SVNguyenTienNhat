package khoaluan.BE.service;

import khoaluan.BE.request.LoginRequest;
import khoaluan.BE.request.SignupRequest;
import khoaluan.BE.response.DataResponse;

public interface UserService {

	DataResponse login(LoginRequest login);

	DataResponse signup(SignupRequest signup);
}
