package models;

import java.util.UUID;

public class Answer {
    private UUID id;
    private String title;

    public Answer(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
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
}
