import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/product/';

class ProductService {
  addProduct(formData) {
    return axios.post(API_URL + 'add-product', formData, {});
  }

  getProducts(pageNumber, pageSize) {
    return axios.get(
      API_URL +
        'get-product?pageNumber=' +
        pageNumber +
        '&pageSize=' +
        pageSize +
        '&userId=',
      {}
    );
  }

  getProductInfo(productId) {
    return axios.get(API_URL + 'get-product-info?productId=' + productId, {});
  }

  getProductsUser(pageNumber, pageSize, userId) {
    return axios.get(
      API_URL +
        'get-product-user?pageNumber=' +
        pageNumber +
        '&pageSize=' +
        pageSize +
        '&userId=' +
        userId,
      {}
    );
  }

  getProductsCategory(pageNumber, pageSize, categoryId) {
    return axios.get(
      API_URL +
        'get-product-category?pageNumber=' +
        pageNumber +
        '&pageSize=' +
        pageSize +
        '&categoryId=' +
        categoryId,
      {}
    );
  }

  deleteProduct(productId) {
    return axios.delete(API_URL + 'delete-product?productId=' + productId, {
      headers: authHeader(),
    });
  }

  getSimiProduct(product_id) {
    return axios.get(
      'http://localhost:5000/recommend?product_id=' + product_id,
      {}
    );
  }

  getRecommendCF(userId) {
    return axios.get(
      'http://localhost:5000/recommendations?user_id=' + userId,
      {}
    );
  }
}

export default new ProductService();
