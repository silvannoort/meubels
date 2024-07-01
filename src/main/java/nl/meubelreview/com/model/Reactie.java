package nl.meubelreview.com.model;

import java.time.LocalDateTime;

public class Reactie {
    private Long id;
    private String reactieText;
    private LocalDateTime createdDate;
    private Long userId;
    private Long reviewId;

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReactieText() {
        return reactieText;
    }

    public void setReactieText(String reactieText) {
        this.reactieText = reactieText;
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

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
}
