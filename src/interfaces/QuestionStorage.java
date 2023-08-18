package interfaces;

import models.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionStorage {
    void add(Question question);
    void add(List<Question> questions);
    Question get(UUID id);
    List<Question> findAll();
    List<Question> findAllByFormId(UUID id);
}
