package services;

import interfaces.UserStorage;
import models.User;
import storages.JsonFileUserStorage;

import java.util.UUID;

public class UserService {
    private final UserStorage storage = new JsonFileUserStorage();

    public User create(String username, String password) {
        User user = new User(username, password);

        return storage.add(user);
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
