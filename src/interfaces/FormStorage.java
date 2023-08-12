package interfaces;

import models.Form;

import java.util.List;
import java.util.UUID;

public interface FormStorage {
    Form add(Form form);
    Form get(UUID id);
    List<Form> findAll();
}
