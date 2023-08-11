package models;

import java.util.List;
import java.util.UUID;

public class Form {
    private UUID id;
    private String title;
    private String description;
    private List<Question> questions;

    public Form(String title, String description, List<Question> questions) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.questions = questions;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
