import enums.Role;
import interfaces.Reader;
import interfaces.Writer;
import io.ConsoleReader;
import io.ConsoleWriter;
import models.*;
import services.FormService;
import services.HistoryService;
import services.SessionService;
import services.UserService;

import java.util.*;

public class ConsoleApplication {
    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();
    private final FormService formService = new FormService();
    private final HistoryService historyService = new HistoryService();
    private Writer writer = new ConsoleWriter();
    private Reader reader = new ConsoleReader();
    private boolean exit = false;

    public void start() {
        while (!exit) {
            if (sessionService.getCurrentSessionUser() == null) {
                showGuestMenu();
                guestActions();

            } else if (sessionService.getCurrentSessionUser().getRole().equals(Role.USER)) {
                showUserMenu();
                userActions();

            } else if (sessionService.getCurrentSessionUser().getRole().equals(Role.ADMIN)) {
                showAdminMenu();
                adminActions();

            }
        }
    }

    private void showGuestMenu() {
        writer.write("Choose:");
        writer.write("1 - Sign Up");
        writer.write("2 - Log In");
        writer.write("3 - Exit");
    }

    private void showUserMenu() {
        writer.write("Choose:");
        writer.write("1 - Link for the form");
        writer.write("2 - Show history");
        writer.write("3 - Logout");
        writer.write("4 - Exit");
    }

    private void showAdminMenu() {
        writer.write("Choose:");
        writer.write("1 - Create a new form");
        writer.write("2 - Show all");
        writer.write("3 - Share form");
        writer.write("4 - Logout");
        writer.write("5 - Exit");
    }

    private Map<String, String> getUserData() {
        Map<String, String> userData = new HashMap<>();

        writer.write("Enter username: ");
        String username = reader.readLine();

        writer.write("Enter password: ");
        String password = reader.readLine();

        userData.put("Username", username);
        userData.put("Password", password);

        return userData;
    }

    private void register() {
        Map<String, String> userData = getUserData();

        User newUser = userService.create(
                userData.get("Username"),
                userData.get("Password")
        );

        if (newUser != null)
            sessionService.setCurrentSessionUser(newUser);
    }

    private void logIn() {
        Map<String, String> userData = getUserData();

        User logInUser = userService.logIn(
                userData.get("Username"),
                userData.get("Password")
        );

        if (logInUser != null)
            sessionService.setCurrentSessionUser(logInUser);
    }

    private void guestActions() {
        int guestChoose = reader.readNum();

        if (guestChoose == 1) {
            register();

        } else if (guestChoose == 2) {
            logIn();

        } else if (guestChoose == 3) {
            exit = true;
        }
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
            sessionService.setCurrentSessionUser(null);

        } else if (choose == 4) {
            exit = true;
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

    private void adminActions() {
        int choose = reader.readNum();

        if (choose == 1) {
            writer.write("Enter title of form:");
            String title = reader.readLine();

            writer.write("Enter description of form:");
            String description = reader.readLine();

            writer.write("Enter num of questions:");
            int questionNum = reader.readNum();

            List<Question> questions = createQuestions(questionNum);

            Form newForm = new Form(title, description, questions);
            formService.create(newForm);

        } else if (choose == 2) {
            List<Form> allForms = formService.getAll();

            for (int i = 0; i < allForms.size(); i++)
                writer.write(i + ". " + allForms.get(i).getTitle());

        } else if (choose == 3) {
            List<Form> allForms = formService.getAll();

            for (int i = 0; i < allForms.size(); i++)
                writer.write(i + ". " + allForms.get(i).getTitle());


            writer.write("Choose form to share: ");
            int choosedForm = reader.readNum();

            writer.write(formService.shareForm(allForms.get(choosedForm)).toString());

        } else if (choose == 4) {
            sessionService.setCurrentSessionUser(null);

        } else if (choose == 5) {
            exit = true;
        }
    }

    private List<Question> createQuestions(int size) {
        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            writer.write("Enter title of question:");
            String questionTitle = reader.readLine();

            writer.write("Enter number of answer: ");
            int numOfAnswer = reader.readNum();

            List<Answer> answers = createAnswersToQuestion(numOfAnswer);

            Question question = new Question(questionTitle, answers);
            questions.add(question);
        }

        return questions;
    }

    private List<Answer> createAnswersToQuestion(int size) {
        List<Answer> answers = new ArrayList<>();

        for (int j = 0; j < size; j++) {
            writer.write("Enter title of answer:");
            String answerTitle = reader.readLine();

            Answer answer = new Answer(answerTitle);
            answers.add(answer);
        }

        return answers;
    }
}
