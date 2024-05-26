package khoaluan.BE.response;

import org.springframework.data.domain.Page;

import khoaluan.BE.model.Category;

public class ProductCategoryResponse {

	private Category category;
	private Page<ProductResponse> content;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Page<ProductResponse> getContent() {
		return content;
	}

	public void setContent(Page<ProductResponse> content) {
		this.content = content;
	}

}
