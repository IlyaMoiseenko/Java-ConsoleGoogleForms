package application.console;

import enums.SessionState;
import interfaces.Reader;
import interfaces.Writer;
import io.ConsoleReader;
import io.ConsoleWriter;
import models.User;
import services.SessionService;
import services.UserService;

import java.util.HashMap;
import java.util.Map;

public class GuestActions {
    private Writer writer = new ConsoleWriter();
    private Reader reader = new ConsoleReader();

    private final UserService userService = new UserService();
    private final SessionService sessionService;

    public GuestActions(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void start() {
        showGuestMenu();
        guestActions();
    }

    private void showGuestMenu() {
        writer.write("Choose:");
        writer.write("1 - Sign Up");
        writer.write("2 - Log In");
        writer.write("3 - Exit");
    }

    private void guestActions() {
        int guestChoose = reader.readNum();

        if (guestChoose == 1) {
            register();

        } else if (guestChoose == 2) {
            logIn();

        } else if (guestChoose == 3) {
            sessionService.setSessionState(SessionState.STOP);
        }
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
}
