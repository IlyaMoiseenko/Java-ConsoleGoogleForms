package services;

import interfaces.HistoryStorage;
import models.History;
import models.User;
import storages.JsonFilleHistoryStorage;

import java.util.List;

public class HistoryService {
    private HistoryStorage storage = new JsonFilleHistoryStorage();

    public void save(History history) {
        storage.save(history);
    }

    public List<History> findByUser(User user) {
        return storage.getByUser(user);
    }
}
