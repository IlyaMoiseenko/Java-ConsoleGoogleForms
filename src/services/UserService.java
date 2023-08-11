package services;

import interfaces.UserStorage;
import models.User;
import storages.JsonFileUserStorage;

import java.util.UUID;

public class UserService {
    private final UserStorage storage = new JsonFileUserStorage();

    public boolean create(String username, String password) {
        User user = new User(username, password);

        return storage.add(user);
    }

    public User getById(UUID id) {
        return storage.getById(id);
    }

    public User getByUsername(String username) {
        return storage.getByUsername(username);
    }
}
