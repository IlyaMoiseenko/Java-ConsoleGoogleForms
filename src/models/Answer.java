package models;

import java.util.UUID;

public class Answer {
    private UUID id;
    private String title;
    private UUID questionId;

    public Answer(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }

    public Answer(String title, UUID questionId) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.questionId = questionId;
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

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }
}
