package services;

import interfaces.FormStorage;
import models.Form;
import storages.JsonFileFormStorage;

import java.util.List;
import java.util.UUID;

public class FormService {
    private FormStorage storage = new JsonFileFormStorage();

    public Form create(Form form) {
        return storage.add(form);
    }

    public List<Form> getAll() {
        return storage.findAll();
    }

    public UUID shareForm(Form form) {
        return form.getId();
    }

    public Form getGyId(UUID id) {
        return storage.get(id);
    }
}
