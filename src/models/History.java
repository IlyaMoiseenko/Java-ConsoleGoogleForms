package models;

import java.util.List;
import java.util.UUID;

public class History {
    private UUID id;
    private UUID userId;
    private UUID formId;
    private List<String> userAnswers;

    public History() {
        this.id = UUID.randomUUID();
    }

    public History(UUID id, UUID userId, UUID formId) {
        this.id = id;
        this.userId = userId;
        this.formId = formId;
    }

    public History(UUID userId, UUID formId, List<String> userAnswers) {
        this.userId = userId;
        this.formId = formId;
        this.userAnswers = userAnswers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getFormId() {
        return formId;
    }

    public void setFormId(UUID formId) {
        this.formId = formId;
    }

    public List<String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<String> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
