package interfaces;

import models.User;

import java.util.UUID;

public interface UserStorage {
    User add(User user);
    User getById(UUID id);
    User getByUsername(String username);
}
