package interfaces;

import models.Form;
import models.History;
import models.User;

import java.util.List;

public interface HistoryStorage {
    void save(History history);
    List<History> getAll();

    List<History> getByUser(User user);
}
