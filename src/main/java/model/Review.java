package model;

public class Review {
    private int reviewId;
    private int orderId;
    private int star;
    private String description;

    public Review( String orderId, int star, String description) {
        this.orderId = Integer.parseInt(orderId);
        this.star = star;
        this.description = description;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}