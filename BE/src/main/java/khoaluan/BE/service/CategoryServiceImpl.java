package khoaluan.BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import khoaluan.BE.repository.CategoryReponsitory;
import khoaluan.BE.response.DataResponse;
import util.Constant;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryReponsitory categoryReponsitory;

	@Override
	public DataResponse getCategory() {
		DataResponse res = new DataResponse();
		res.setStatus(Constant.SUCCESS);
		res.setResult(categoryReponsitory.findAll());
		res.setMessage("Lấy danh mục thành công");

		return res;
	}

}
