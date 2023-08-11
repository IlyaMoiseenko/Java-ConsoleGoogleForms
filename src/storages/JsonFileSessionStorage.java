package storages;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import interfaces.SessionStorage;
import models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileSessionStorage implements SessionStorage {
    private String path = "src/resources/session.json";
    private final Gson gson = new Gson();
    private final Type userTypeToken = new TypeToken<User>(){}.getType();

    public JsonFileSessionStorage() {}

    public JsonFileSessionStorage(String path) {
        this.path = path;
    }

    @Override
    public User getSessionUser() {
        try {
            return gson.fromJson(Files.readString(Path.of(path)), userTypeToken);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean setSessionUser(User user) {
        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write(gson.toJson(user));

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void removeSessionUser() {
        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
