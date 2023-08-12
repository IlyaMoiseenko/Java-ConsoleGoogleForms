package storages;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import interfaces.HistoryStorage;
import models.Form;
import models.History;
import models.User;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonFilleHistoryStorage implements HistoryStorage {
    private String path = "src/resources/history.json";
    private Gson gson = new Gson();
    private final Type listTypeToken = new TypeToken<List<History>>(){}.getType();
    private List<History> allHistory = getAll();

    @Override
    public void save(History history) {
        if (allHistory == null)
            allHistory = new ArrayList<>();

        allHistory.add(history);

        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write(gson.toJson(allHistory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<History> getAll() {
        try {
            return gson.fromJson(Files.readString(Path.of(path)), listTypeToken);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<History> getByUser(User user) {
        List<History> allByUser = new ArrayList<>();

        for (History history : allHistory) {
            if (history.getUserId().equals(user.getId()))
                allByUser.add(history);
        }

        return allByUser;
    }
}
