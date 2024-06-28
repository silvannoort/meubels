package nl.meubelreview.com.service;

import nl.meubelreview.com.model.Review;
import nl.meubelreview.com.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

public class ReviewService {
    private ReviewRepository reviewRepository = new ReviewRepository();

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
}
