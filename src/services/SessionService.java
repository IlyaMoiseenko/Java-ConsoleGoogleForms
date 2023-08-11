package services;

import interfaces.SessionStorage;
import models.User;
import storages.JsonFileSessionStorage;

public class SessionService {
    private SessionStorage sessionStorage = new JsonFileSessionStorage();

    public User getCurrentSessionUser() {
        return sessionStorage.getSessionUser();
    }

    public boolean setCurrentSessionUser(User user) {
        return sessionStorage.setSessionUser(user);
    }

    public void removeCurrentSessionUser() {
        sessionStorage.removeSessionUser();
    }
}
