import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cart/';

class CartService {
  addCartItem(cartItem) {
    return axios.post(API_URL + 'add-cart', cartItem, {});
  }

  getCartItem(userId) {
    return axios.get(API_URL + 'get-list-cart?userId=' + userId, {});
  }

  removeCartItem(cartItemId) {
    return axios.delete(API_URL + 'remove-item?cartItemId=' + cartItemId, {});
  }
}

export default new CartService();
