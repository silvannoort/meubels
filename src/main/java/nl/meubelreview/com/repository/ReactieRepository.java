package nl.meubelreview.com.repository;

import nl.meubelreview.com.model.Reactie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ReactieRepository {
    private List<Reactie> reacties;
    private AtomicLong idCounter;
    private final File file = new File("reacties.json");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ReactieRepository() {
        try {
            if (file.exists()) {
                reacties = objectMapper.readValue(file, new TypeReference<List<Reactie>>() {});
                idCounter = new AtomicLong(reacties.stream().mapToLong(Reactie::getId).max().orElse(0));
            } else {
                reacties = new ArrayList<>();
                idCounter = new AtomicLong();
                saveToFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            reacties = new ArrayList<>();
            idCounter = new AtomicLong();
        }
    }

    public Reactie save(Reactie reactie) {
        if (reactie.getId() == null) {
            reactie.setId(idCounter.incrementAndGet());
        }
        reacties.add(reactie);
        saveToFile();
        return reactie;
    }

    public List<Reactie> findAll() {
        return new ArrayList<>(reacties);
    }

    public Optional<Reactie> findById(Long id) {
        return reacties.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public List<Reactie> findByReviewId(Long reviewId) {
        return reacties.stream().filter(r -> r.getReviewId().equals(reviewId)).collect(Collectors.toList());
    }

    public List<Reactie> findByUserId(Long userId) {
        return reacties.stream().filter(r -> r.getUserId().equals(userId)).collect(Collectors.toList());
    }

    private void saveToFile() {
        try {
            objectMapper.writeValue(file, reacties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
