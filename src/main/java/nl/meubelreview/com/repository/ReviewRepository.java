package nl.meubelreview.com.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.meubelreview.com.model.Review;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ReviewRepository {
    private List<Review> reviews;
    private AtomicLong idCounter;
    private final File file = new File("reviews.json");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ReviewRepository() {
        try {
            if (file.exists()) {
                reviews = objectMapper.readValue(file, new TypeReference<List<Review>>() {});
                idCounter = new AtomicLong(reviews.stream().mapToLong(Review::getId).max().orElse(0));
            } else {
                reviews = new ArrayList<>();
                idCounter = new AtomicLong();
                saveToFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            reviews = new ArrayList<>();
            idCounter = new AtomicLong();
        }
    }

    public Review save(Review review) {
        if (review.getId() == null) {
            review.setId(idCounter.incrementAndGet());
        }
        reviews.add(review);
        saveToFile();
        return review;
    }

    public List<Review> findAll() {
        return new ArrayList<>(reviews);
    }

    public Optional<Review> findById(Long id) {
        return reviews.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    private void saveToFile() {
        try {
            objectMapper.writeValue(file, reviews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
