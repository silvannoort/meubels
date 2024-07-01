package nl.meubelreview.com.service;

import nl.meubelreview.com.model.Reactie;
import nl.meubelreview.com.model.Review;
import nl.meubelreview.com.model.User;
import nl.meubelreview.com.repository.ReactieRepository;
import nl.meubelreview.com.repository.ReviewRepository;
import nl.meubelreview.com.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReviewService {
    private ReviewRepository reviewRepository = new ReviewRepository();
    private ReactieRepository reactieRepository = new ReactieRepository();

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> searchReviews(String productName) {
        return reviewRepository.findAll().stream()
                .filter(review -> review.getProductName().toLowerCase().contains(productName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Reactie> getReactiesByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(Review::getReacties)
                .orElseThrow(() -> new IllegalArgumentException("Review niet gevonden"));
    }

    public Reactie addReactieToReview(Long reviewId, Reactie reactie) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review niet gevonden"));
        reactie.setReviewId(reviewId);
        reactie = reactieRepository.save(reactie);
        review.getReacties().add(reactie);
        reviewRepository.save(review);
        return reactie;
    }
    public boolean deleteReview(Long id) {
        return reviewRepository.deleteById(id);
    }
}

