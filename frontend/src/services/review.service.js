import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/review/';

class ReviewService {
  addRating(reviewRequest) {
    return axios.post(API_URL + 'add-rating', reviewRequest, {});
  }

  getRating(productId) {
    return axios.get(API_URL + 'get-rating?productId=' + productId, {});
  }

  isBuy(productId, useId) {
    return axios.get(
      API_URL + 'is-buy?productId=' + productId + '&userId=' + useId,
      {}
    );
  }
}

export default new ReviewService();
