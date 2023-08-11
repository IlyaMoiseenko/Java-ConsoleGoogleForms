package interfaces;

import models.User;

public interface SessionStorage {
    User getSessionUser();
    boolean setSessionUser(User user);
    void removeSessionUser();
}
