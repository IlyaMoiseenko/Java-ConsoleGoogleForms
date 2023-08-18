package services;

import interfaces.HistoryStorage;
import models.History;
import models.User;
import storages.jdbc.JdbcHistoryStorage;
import storages.json.JsonFilleHistoryStorage;

import java.util.List;

public class HistoryService {
    //private HistoryStorage storage = new JsonFilleHistoryStorage();
    private final HistoryStorage storage = new JdbcHistoryStorage();

    public void save(History history) {
        storage.save(history);
    }

    public List<History> findByUser(User user) {
        return storage.getByUser(user);
    }
}
