package khoaluan.BE.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import khoaluan.BE.model.Product;
import khoaluan.BE.repository.CategoryReponsitory;
import khoaluan.BE.repository.ProductReponsitory;
import khoaluan.BE.repository.UserRepository;
import khoaluan.BE.request.ProductRequest;
import khoaluan.BE.response.DataResponse;
import khoaluan.BE.response.ProductCategoryResponse;
import util.Constant;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductReponsitory productReponsitory;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryReponsitory categoryReponsitory;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${product.image.path}")
	String imageUrl;

	@Override
	public DataResponse addProduct(MultipartHttpServletRequest productRequest) {
		DataResponse res = new DataResponse();
		try {
			MultipartFile image = productRequest.getFile("image");
			String productString = productRequest.getParameter("product");
			ProductRequest productRe = objectMapper.readValue(productString, ProductRequest.class);

			Product product = new Product();
			product.setCategory(categoryReponsitory.findById(productRe.getCategoryId()).get());
			product.setDescription(productRe.getDescription());
			product.setName(productRe.getName());
			product.setPrice(productRe.getPrice());
			product.setUser(userRepository.findById(productRe.getSellerId()).get());
			Product productNew = productReponsitory.save(product);

			Path uploadPath = Paths.get(imageUrl);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			String fileName = imageUrl + productNew.getId() + ".png";
			File dest = new File(fileName);
			image.transferTo(dest);

			productNew.setImage("http://localhost:8080/api/product/image?productId=" + productNew.getId());
			productReponsitory.save(productNew);
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Thêm sản phẩm thành công");
			res.setResult(productNew);

		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Thêm sản phẩm bị lỗi");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public DataResponse getProductUser(int pageNumber, int pageSize, Long userId) {
		DataResponse res = new DataResponse();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize);

			res.setResult(productReponsitory.getListProductByUserId(pageable, userId));
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy danh sách thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy danh sách thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public DataResponse getProduct(int pageNumber, int pageSize) {
		DataResponse res = new DataResponse();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize);

			res.setResult(productReponsitory.getListProduct(pageable));
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy danh sách thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy danh sách thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public DataResponse deleteProduct(Long productId) {
		DataResponse res = new DataResponse();
		try {
			Product product = productReponsitory.findById(productId).get();
			product.setDeleted(true);
			productReponsitory.save(product);
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Xóa sản phẩm thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Xóa sản phẩm thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public DataResponse getProductCategory(int pageNumber, int pageSize, Long categoryId) {
		DataResponse res = new DataResponse();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			ProductCategoryResponse productCaRe = new ProductCategoryResponse();
			productCaRe.setCategory(categoryReponsitory.findById(categoryId).get());
			productCaRe.setContent(productReponsitory.getListProductByCategoryId(pageable, categoryId));
			res.setResult(productCaRe);
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy danh sách thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy danh sách thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public DataResponse getProductInfo(Long productId) {
		DataResponse res = new DataResponse();
		try {
			res.setResult(productReponsitory.getProductResponseByProductId(productId));
			res.setStatus(Constant.SUCCESS);
			res.setMessage("Lấy thông tin thành công");
		} catch (Exception e) {
			res.setStatus(Constant.ERROR);
			res.setMessage("Lấy thông tin thất bại");
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public ResponseEntity<?> displayImage(Long productId) throws IOException {
		Path imagePath = Paths.get(imageUrl + productId + ".png");
		byte[] imageBytes = Files.readAllBytes(imagePath);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
	}

}
