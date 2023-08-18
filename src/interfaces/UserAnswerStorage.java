package interfaces;

import models.UserAnswer;

import java.util.List;
import java.util.UUID;

public interface UserAnswerStorage {
    void add(List<UserAnswer> userAnswers);
    List<UserAnswer> getByUserId(UUID id);
}
