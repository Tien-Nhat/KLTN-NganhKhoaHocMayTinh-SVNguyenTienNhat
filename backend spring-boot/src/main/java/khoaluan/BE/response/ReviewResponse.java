package khoaluan.BE.response;

public class ReviewResponse {

	private int yourRating = 0;
	private Boolean isBuy;

	public int getYourRating() {
		return yourRating;
	}

	public void setYourRating(int yourRating) {
		this.yourRating = yourRating;
	}

	public Boolean getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Boolean isBuy) {
		this.isBuy = isBuy;
	}

}
