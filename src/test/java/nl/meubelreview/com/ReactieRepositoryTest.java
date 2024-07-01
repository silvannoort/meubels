package nl.meubelreview.com;

import nl.meubelreview.com.model.Reactie;
import nl.meubelreview.com.repository.ReactieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ReactieRepositoryTest {
    private ReactieRepository reactieRepository;

    @BeforeEach
    public void setUp() {
        reactieRepository = new ReactieRepository();
    }

    @Test
    public void testAddReactie() {
        Reactie reactie = new Reactie();
        reactie.setReactieText("Great review!");

        reactieRepository.save(reactie);
        List<Reactie> reacties = reactieRepository.findAll();

        assertFalse(reacties.isEmpty());
        assertEquals("Great review!", reacties.get(0).getReactieText());
    }

    @Test
    public void testFindReactieById() {
        Reactie reactie = new Reactie();
        reactie.setReactieText("Great review!");

        Reactie savedReactie = reactieRepository.save(reactie);
        Optional<Reactie> retrievedReactie = reactieRepository.findById(savedReactie.getId());

        assertTrue(retrievedReactie.isPresent());
        assertEquals("Great review!", retrievedReactie.get().getReactieText());
    }
}
