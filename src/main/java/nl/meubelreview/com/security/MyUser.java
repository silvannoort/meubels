package nl.meubelreview.com.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyUser implements Principal {
    private String name;
    private String role;
    private String password;
    private static List<MyUser> users = new ArrayList<>();

    public MyUser(String name, String password) {
        this.name = name;
        this.role = "admin";
        this.password = password;
    }

    public static String validateLogin(String username, String password) {
        if (username.equals("user") && password.equals("password")) {
            return "user";
        }
        return null;
        //TODO Actually validate the login!!!
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return Objects.equals(name, myUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public static boolean addUser(MyUser user) {
        if (users.contains(user)) return false;
        users.add(user);
        return true;
    }

    public static MyUser getUserByName(String user) {
        return users.stream().filter(u -> u.getName().equals(user)).findFirst().orElse(null);
    }

    @Override
    public String getName() {
        return name;
    }
}
