package nl.meubelreview.com.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Review {
    private Long id;
    private String productName;
    private int rating;
    private String reviewText;
    private LocalDateTime createdDate;
    private Long userId;
    private List<Reactie> reacties;

    public Review() {
        this.reacties = new ArrayList<>();
    }

    public Review(Long id, String productName, int rating, String reviewText, LocalDateTime createdDate, Long userId) {
        this.id = id;
        this.productName = productName;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdDate = createdDate;
        this.userId = userId;
        this.reacties = new ArrayList<>();
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Reactie> getReacties() {
        return reacties;
    }

    public void setReacties(List<Reactie> reacties) {
        this.reacties = reacties;
    }
}
