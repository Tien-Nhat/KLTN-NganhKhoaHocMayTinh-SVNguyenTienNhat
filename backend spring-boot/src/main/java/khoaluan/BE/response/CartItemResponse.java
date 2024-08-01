package khoaluan.BE.response;

public class CartItemResponse {

	private Long id;
	private Long productId;
	private String name;
	private String image;
	private int quantity;
	private Long price;

	public CartItemResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItemResponse(Long id, Long productId, String name, String image, int quantity, Long price) {
		super();
		this.id = id;
		this.productId = productId;
		this.name = name;
		this.image = image;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

}
