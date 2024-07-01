package nl.meubelreview.com.repository;

import nl.meubelreview.com.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository {
    private static final String USER_FILE = "users.json";
    private static List<User> users = new ArrayList<>();
    private static AtomicLong idCounter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();

    static {
        loadUsers();
    }

    private static void loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = UserRepository.class.getClassLoader().getResourceAsStream(USER_FILE)) {
            if (is != null) {
                users = mapper.readValue(is, new TypeReference<List<User>>() {});
                idCounter.set(users.stream().mapToLong(User::getId).max().orElse(0));
                System.out.println("User file loaded successfully");
            } else {
                System.err.println("User file not found: " + USER_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<User> validateLogin(String username, String password) {
        System.out.println("Validating login for user: " + username);
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();
    }

    public static User getUserByName(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public void saveUser(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.incrementAndGet());
        }
        users.add(user);
        saveUsersToFile();
    }

    private void saveUsersToFile() {
        try {
            File file = new File(getClass().getClassLoader().getResource(USER_FILE).getFile());
            mapper.writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
