package nl.meubelreview.com.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.meubelreview.com.model.Review;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ReviewRepository {
    private List<Review> reviews;
    private AtomicLong idCounter;
    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public ReviewRepository() {
        file = new File(getClass().getClassLoader().getResource("reviews.json").getFile());
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
    public boolean deleteById(Long id) {
        boolean removed = reviews.removeIf(r -> r.getId().equals(id));
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    public Review save(Review review) {
        if (review.getId() == null) {
            review.setId(idCounter.incrementAndGet());
        } else {
            reviews.removeIf(r -> r.getId().equals(review.getId()));
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

    public List<Review> findByProductName(String productName) {
        return reviews.stream()
                .filter(r -> r.getProductName().toLowerCase().contains(productName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Review> findByUserId(Long userId) {
        return reviews.stream()
                .filter(r -> r.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    private void saveToFile() {
        try {
            objectMapper.writeValue(file, reviews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }


