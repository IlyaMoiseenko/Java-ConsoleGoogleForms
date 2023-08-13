package services;

import enums.SessionState;
import interfaces.SessionStorage;
import models.User;
import storages.JsonFileSessionStorage;

public class SessionService {
    private SessionStorage sessionStorage = new JsonFileSessionStorage();
    private SessionState sessionState = SessionState.RUN;

    public User getCurrentSessionUser() {
        return sessionStorage.getSessionUser();
    }

    public boolean setCurrentSessionUser(User user) {
        return sessionStorage.setSessionUser(user);
    }

    public void removeCurrentSessionUser() {
        sessionStorage.removeSessionUser();
    }

    public SessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(SessionState sessionState) {
        this.sessionState = sessionState;
    }
}
