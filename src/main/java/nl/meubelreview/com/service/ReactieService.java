package nl.meubelreview.com.service;

import nl.meubelreview.com.model.Reactie;
import nl.meubelreview.com.repository.ReactieRepository;

import java.util.List;
import java.util.Optional;

public class ReactieService {
    private ReactieRepository reactieRepository = new ReactieRepository();

    public Reactie createReactie(Reactie reactie) {
        return reactieRepository.save(reactie);
    }

    public List<Reactie> getAllReacties() {
        return reactieRepository.findAll();
    }

    public Reactie getReactieById(Long id) {
        Optional<Reactie> reactie = reactieRepository.findById(id);
        return reactie.orElse(null);
    }

    public List<Reactie> getReactiesByUserId(Long userId) {
        return reactieRepository.findByUserId(userId);
    }

    public List<Reactie> getReactiesByReviewId(Long reviewId) {
        return reactieRepository.findByReviewId(reviewId);
    }
}
