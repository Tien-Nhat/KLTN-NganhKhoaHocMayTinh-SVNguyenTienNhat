package khoaluan.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import khoaluan.BE.model.User;
import khoaluan.BE.repository.UserRepository;
import khoaluan.BE.request.LoginRequest;
import khoaluan.BE.request.SignupRequest;
import khoaluan.BE.response.DataResponse;
import khoaluan.BE.response.UserResponse;
import util.Constant;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public DataResponse login(LoginRequest login) {
		DataResponse res = new DataResponse();
		try {
			User user = userRepository.findByUsername(login.getUsername()).get();
			if (user.getPassword().equals(login.getPassword())) {
				res.setStatus(Constant.SUCCESS);
				UserResponse userResponse = new UserResponse();
				userResponse.setFirstname(user.getFirstname());
				userResponse.setLastname(user.getLastname());
				userResponse.setId(user.getId());
				res.setStatus(Constant.SUCCESS);
				res.setMessage("Đăng nhập thành công");
				res.setResult(userResponse);
			} else {
				res.setStatus(Constant.ERROR);
				res.setMessage("Sai mật khẩu");
			}

		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Đăng nhập bị lỗi");
		}
		return res;
	}

	@Override
	public DataResponse signup(SignupRequest signup) {
		DataResponse res = new DataResponse();
		try {
			User user = new User();
			user.setUsername(signup.getUsername());
			user.setAddress(signup.getAddress());
			user.setEmail(signup.getEmail());
			user.setPassword(signup.getPassword());
			user.setFirstname(signup.getFirstname());
			user.setLastname(signup.getLastname());
			user.setPhone(signup.getPhone());
			userRepository.save(user);
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Đăng ký thành công");
			res.setResult(user);
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Đăng ký thất bại");
		}
		return res;
	}

}
