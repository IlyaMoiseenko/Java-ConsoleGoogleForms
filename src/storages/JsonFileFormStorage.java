package storages;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import interfaces.FormStorage;
import models.Form;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonFileFormStorage implements FormStorage {
    private String path = "src/resources/forms.json";
    private final Gson gson = new Gson();
    private final Type listTypeToken = new TypeToken<List<Form>>(){}.getType();
    private List<Form> allForms = findAll();

    public JsonFileFormStorage() {}

    public JsonFileFormStorage(String path) {
        this.path = path;
    }

    @Override
    public Form add(Form form) {
        if (allForms == null)
            allForms = new ArrayList<>();

        allForms.add(form);

        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write(gson.toJson(allForms));

            return form;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Form get(UUID id) {
        for (Form form : allForms) {
            if (form.getId().equals(id))
                return form;
        }

        return null;
    }

    @Override
    public List<Form> findAll() {
        try {
            return gson.fromJson(Files.readString(Path.of(path)), listTypeToken);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
