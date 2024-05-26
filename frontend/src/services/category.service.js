import axios from "axios";

const API_URL = "http://localhost:8080/api/category/";

class CategoryService {
 
  getCategory() {
    return axios.get(API_URL + "get-category", {});
  }
}

export default new CategoryService();
