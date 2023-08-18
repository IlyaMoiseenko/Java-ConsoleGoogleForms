package services;

import enums.Role;
import interfaces.UserStorage;
import models.User;
import storages.jdbc.JdbcUserStorage;
import storages.json.JsonFileUserStorage;

import java.util.UUID;

public class UserService {
    //private final UserStorage storage = new JsonFileUserStorage();
    private final UserStorage storage = new JdbcUserStorage();

    public User create(String username, String password) {
        User user = new User(username, password);
        if (user.getUsername().equals("admin") && user.getPassword().equals("admin")) {
            user.setRole(Role.ADMIN);
            storage.add(user);
        } else {
            user.setRole(Role.USER);
            storage.add(user);
        }

        return user;
    }

    public User getById(UUID id) {
        return storage.getById(id);
    }

    public User getByUsername(String username) {
        return storage.getByUsername(username);
    }

    public User logIn(String username, String password) {
        User user = getByUsername(username);

        if (user != null)
            if (user.getPassword().equals(password))
                return user;

        return null;
    }
}
