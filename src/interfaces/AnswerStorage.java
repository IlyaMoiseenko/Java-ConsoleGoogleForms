package interfaces;

import models.Answer;

import java.util.List;
import java.util.UUID;

public interface AnswerStorage {
    void add(Answer answer);
    void add(List<Answer> answers);
    Answer get(UUID id);
    List<Answer> findAll();
    List<Answer> findAllByQuestionId(UUID id);
}
