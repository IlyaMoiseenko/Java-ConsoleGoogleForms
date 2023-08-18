package services;

import interfaces.AnswerStorage;
import interfaces.FormStorage;
import interfaces.QuestionStorage;
import models.Answer;
import models.Form;
import models.Question;
import storages.jdbc.JdbcAnswerStorage;
import storages.jdbc.JdbcFormStorage;
import storages.jdbc.JdbcQuestionStorage;
import storages.json.JsonFileFormStorage;

import java.util.List;
import java.util.UUID;

public class FormService {
    //private FormStorage storage = new JsonFileFormStorage();
    private final FormStorage formStorage = new JdbcFormStorage();
    private final QuestionStorage questionStorage = new JdbcQuestionStorage();
    private final AnswerStorage answerStorage = new JdbcAnswerStorage();

    public Form create(Form form) {
        for (Question question : form.getQuestions()) {
            List<Answer> answers = question.getAnswers();

            question.setFormId(form.getId());

            for (Answer answer : answers) {
                answer.setQuestionId(question.getId());
            }

            answerStorage.add(answers);
        }

        questionStorage.add(form.getQuestions());
        return formStorage.add(form);
    }

    public List<Form> getAll() {
        return formStorage.findAll();
    }

    public UUID shareForm(Form form) {
        return form.getId();
    }

    public Form getGyId(UUID id) {
        Form form = formStorage.get(id);
        List<Question> questionsByForm = questionStorage.findAllByFormId(id);

        for (Question question : questionsByForm) {
            question.setAnswers(
                    answerStorage.findAllByQuestionId(question.getId())
            );
        }

        form.setQuestions(questionsByForm);

        return form;
    }
}
