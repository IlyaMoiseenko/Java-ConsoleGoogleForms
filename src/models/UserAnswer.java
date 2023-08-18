package models;

import java.util.UUID;

public class UserAnswer {
    private UUID id;
    private String answer;
    private UUID questionId;
    private UUID userId;

    public UserAnswer(String answer, UUID questionId, UUID userId) {
        this.id = UUID.randomUUID();
        this.answer = answer;
        this.questionId = questionId;
        this.userId = userId;
    }

    public UserAnswer(UUID id, String answer, UUID questionId, UUID userId) {
        this.id = id;
        this.answer = answer;
        this.questionId = questionId;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
