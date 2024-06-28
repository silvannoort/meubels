package nl.meubelreview.com.model;

import java.util.List;

public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String profilePicture;
    private List<Review> reviews;
    private List<Reactie> reacties;



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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Reactie> getComments() {
        return reacties;
    }

    public void setComments(List<Reactie> comments) {
        this.reacties = reacties;
    }
}
