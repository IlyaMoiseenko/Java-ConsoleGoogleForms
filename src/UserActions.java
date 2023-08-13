import enums.SessionState;
import interfaces.Reader;
import interfaces.Writer;
import io.ConsoleReader;
import io.ConsoleWriter;
import models.Form;
import models.History;
import models.Question;
import services.FormService;
import services.HistoryService;
import services.SessionService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserActions {
    private Writer writer = new ConsoleWriter();
    private Reader reader = new ConsoleReader();

    private final FormService formService = new FormService();
    private final HistoryService historyService = new HistoryService();
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
            List<String> answers = getAnswerOnFormQuestions(form);

            History currentUserHistory = new History(
                    sessionService.getCurrentSessionUser().getId(),
                    form.getId(),
                    answers
            );

            historyService.save(currentUserHistory);
        } else if (choose == 2) {
            getHistoryByUser();

        } else if (choose == 3) {
            sessionService.removeCurrentSessionUser();

        } else if (choose == 4) {
            sessionService.setSessionState(SessionState.STOP);
        }
    }

    private List<String> getAnswerOnFormQuestions(Form form) {
        List<String> answers = new ArrayList<>();

        writer.write("Title: " + form.getTitle());
        writer.write("Description: "  + form.getDescription());

        for (int i = 0; i < form.getQuestions().size(); i++) {
            Question currentQuestion = form.getQuestions().get(i);

            writer.write(i + ". " + currentQuestion.getTitle());

            for (int j = 0; j < currentQuestion.getAnswers().size(); j++)
                writer.write(j + ". " + currentQuestion.getAnswers().get(j).getTitle());

            int userAnswer = reader.readNum();
            answers.add(currentQuestion.getAnswers().get(userAnswer).getTitle());
        }

        return answers;
    }

    private void getHistoryByUser() {
        List<History> historiesByUser = historyService.findByUser(sessionService.getCurrentSessionUser());

        for (History history : historiesByUser)
            printHistory(history);

    }

    private void printHistory(History history) {
        Form form = formService.getGyId(history.getFormId());
        writer.write("Title: " + form.getTitle());

        for (int j = 0; j < form.getQuestions().size(); j++) {
            String questionTitle = form.getQuestions().get(j).getTitle();

            writer.write(j + ". " + questionTitle + " - " + history.getUserAnswers().get(j));
            writer.write("------");
        }
    }
}
