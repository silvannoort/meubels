package nl.meubelreview.com.model;


public class Review {
    private Long id;
    private String productName;
    private Integer rating;
    private String reviewText;

    public Review() {}

    public Review(Long id, String productName, Integer rating, String reviewText) {
        this.id = id;
        this.productName = productName;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
