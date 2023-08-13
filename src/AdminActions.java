import enums.SessionState;
import interfaces.Reader;
import interfaces.Writer;
import io.ConsoleReader;
import io.ConsoleWriter;
import models.Answer;
import models.Form;
import models.Question;
import services.FormService;
import services.SessionService;

import java.util.ArrayList;
import java.util.List;

public class AdminActions {
    private Writer writer = new ConsoleWriter();
    private Reader reader = new ConsoleReader();

    private final FormService formService = new FormService();
    private final SessionService sessionService;

    public AdminActions(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void start() {
        showAdminMenu();
        adminActions();
    }

    private void showAdminMenu() {
        writer.write("Choose:");
        writer.write("1 - Create a new form");
        writer.write("2 - Show all");
        writer.write("3 - Share form");
        writer.write("4 - Logout");
        writer.write("5 - Exit");
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

            printForms(allForms);

        } else if (choose == 3) {
            List<Form> allForms = formService.getAll();

            printForms(allForms);

            writer.write("Choose form to share: ");
            int choosedForm = reader.readNum();

            writer.write(formService.shareForm(allForms.get(choosedForm)).toString());

        } else if (choose == 4) {
            sessionService.removeCurrentSessionUser();

        } else if (choose == 5) {
            sessionService.setSessionState(SessionState.STOP);
        }
    }

    private void printForms(List<Form> forms) {
        for (int i = 0; i < forms.size(); i++) {
            Form currentForm = forms.get(i);

            writer.write(i + ". " + currentForm.getTitle() + " - " + currentForm.getDescription());
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
