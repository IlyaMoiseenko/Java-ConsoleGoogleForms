package application.console;

import enums.SessionState;
import interfaces.QuestionStorage;
import interfaces.Reader;
import interfaces.UserAnswerStorage;
import interfaces.Writer;
import io.ConsoleReader;
import io.ConsoleWriter;
import models.*;
import services.FormService;
import services.HistoryService;
import services.SessionService;
import storages.jdbc.JdbcQuestionStorage;
import storages.jdbc.JdbcUserAnswerStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserActions {
    private Writer writer = new ConsoleWriter();
    private Reader reader = new ConsoleReader();

    private final FormService formService = new FormService();
    //private final HistoryService historyService = new HistoryService();
    private final UserAnswerStorage userAnswerStorage = new JdbcUserAnswerStorage();
    private final QuestionStorage questionStorage = new JdbcQuestionStorage();
    private final SessionService sessionService;

    public UserActions(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void start() {
        showUserMenu();
        userActions();
    }

    private void showUserMenu() {
        writer.write("Choose:");
        writer.write("1 - Link for the form");
        writer.write("2 - Show history");
        writer.write("3 - Logout");
        writer.write("4 - Exit");
    }

    private void userActions() {
        int choose = reader.readNum();

        if (choose == 1) {
            writer.write("Enter id of the form: ");
            String id = reader.readLine();

            Form form = formService.getGyId(UUID.fromString(id));
            List<UserAnswer> answers = getAnswerOnFormQuestions(form);

            userAnswerStorage.add(answers);

//            History currentUserHistory = new History();
//            currentUserHistory.setUserId(sessionService.getCurrentSessionUser().getId());
//            currentUserHistory.setFormId(form.getId());

            //historyService.save(currentUserHistory);
        } else if (choose == 2) {
            getHistoryByUser();

        } else if (choose == 3) {
            sessionService.removeCurrentSessionUser();

        } else if (choose == 4) {
            sessionService.setSessionState(SessionState.STOP);
        }
    }

    private List<UserAnswer> getAnswerOnFormQuestions(Form form) {
        List<UserAnswer> userAnswers = new ArrayList<>();

        writer.write("Title: " + form.getTitle());
        writer.write("Description: "  + form.getDescription());

        for (int i = 0; i < form.getQuestions().size(); i++) {
            Question currentQuestion = form.getQuestions().get(i);

            writer.write(i + ". " + currentQuestion.getTitle());

            List<Answer> currentQuestionAnswers = currentQuestion.getAnswers();
            if (currentQuestionAnswers.size() > 0) {
                for (int j = 0; j < currentQuestion.getAnswers().size(); j++)
                    writer.write(j + ". " + currentQuestion.getAnswers().get(j).getTitle());

                int userAnswer = reader.readNum();

                userAnswers.add(
                        new UserAnswer(
                                String.valueOf(userAnswer),
                                currentQuestion.getId(),
                                sessionService.getCurrentSessionUser().getId()
                        )
                );
            } else {
                String userAnswer = reader.readLine();
                userAnswers.add(
                        new UserAnswer(
                                userAnswer,
                                currentQuestion.getId(),
                                sessionService.getCurrentSessionUser().getId()
                        )
                );
            }
        }

        return userAnswers;
    }

    private void getHistoryByUser() {
//        List<History> historiesByUser = historyService.findByUser(sessionService.getCurrentSessionUser());
//
//        for (History history : historiesByUser)
//            printHistory(history);

        List<UserAnswer> userAnswers = userAnswerStorage.getByUserId(sessionService.getCurrentSessionUser().getId());

        for (UserAnswer userAnswer : userAnswers) {
            Question question = questionStorage.get(userAnswer.getQuestionId());
            writer.write(question.getTitle() + " - " + userAnswer.getAnswer());
        }
    }

//    private void printHistory(History history) {
//        Form form = formService.getGyId(history.getFormId());
//        writer.write("Title: " + form.getTitle());
//
//        for (int j = 0; j < form.getQuestions().size(); j++) {
//            String questionTitle = form.getQuestions().get(j).getTitle();
//
//            writer.write(j + ". " + questionTitle + " - " + history.getUserAnswers().get(j));
//            writer.write("------");
//        }
//    }
}
