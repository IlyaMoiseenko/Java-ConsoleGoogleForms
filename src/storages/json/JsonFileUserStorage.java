package storages.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import interfaces.UserStorage;
import models.User;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonFileUserStorage implements UserStorage {
    private String path = "src/resources/users.json";
    private final Gson gson = new Gson();
    private final Type listTypeToken = new TypeToken<List<User>>(){}.getType();
    private List<User> allUsers = getAll();

    public JsonFileUserStorage() {}

    public JsonFileUserStorage(String path) {
        this.path = path;
    }

    @Override
    public User add(User user) {
        if (allUsers == null)
            allUsers = new ArrayList<>();

        allUsers.add(user);

        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write(gson.toJson(allUsers));

            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getById(UUID id) {
        for (User user : allUsers) {
            if (user.getId().equals(id))
                return user;
        }

        return null;
    }

    @Override
    public User getByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username))
                return user;
        }

        return null;
    }

    private List<User> getAll() {
        try {
            return gson.fromJson(Files.readString(Path.of(path)), listTypeToken);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
