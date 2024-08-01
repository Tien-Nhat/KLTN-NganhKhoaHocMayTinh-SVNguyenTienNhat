import axios from 'axios';

const API_URL = 'http://localhost:8080/api/order/';

class OrderService {
  addOrder(userId) {
    return axios.post(API_URL + 'add-oder?userId=' + userId, null, {});
  }

  getOrder(userId) {
    return axios.get(API_URL + 'get-order?userId=' + userId, {});
  }

  //   removeCartItem(cartItemId) {
  //     return axios.delete(API_URL + 'remove-item?cartItemId=' + cartItemId, {});
  //   }
}

export default new OrderService();
