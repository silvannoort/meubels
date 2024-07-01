package nl.meubelreview.com.model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class User implements Principal {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String profilePicture;
    private String role;
    private List<Review> reviews = new ArrayList<>();
    private List<Reactie> reacties = new ArrayList<>();

    @Override
    public String getName() {
        return this.username;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Reactie> getReacties() {
        return reacties;
    }

    public void setReacties(List<Reactie> reacties) {
        this.reacties = reacties;
    }
}
