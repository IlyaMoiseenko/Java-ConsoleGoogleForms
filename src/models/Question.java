package models;

import java.util.List;
import java.util.UUID;

public class Question {
    private UUID id;
    private String title;
    private UUID formId;
    private List<Answer> answers;

    public Question(String title, List<Answer> answers) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.answers = answers;
    }

    public Question(String title, UUID formId, List<Answer> answers) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.formId = formId;
        this.answers = answers;
    }

    public Question(UUID id, String title, UUID formId) {
        this.id = id;
        this.title = title;
        this.formId = formId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public UUID getFormId() {
        return formId;
    }

    public void setFormId(UUID formId) {
        this.formId = formId;
    }
}
