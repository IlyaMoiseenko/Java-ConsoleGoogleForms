package application.console;

import enums.Role;
import enums.SessionState;
import services.SessionService;

public class ConsoleApplication {
    private final SessionService sessionService = new SessionService();

    public void start() {
        while (sessionService.getSessionState().equals(SessionState.RUN)) {
            if (sessionService.getCurrentSessionUser() == null) {
                GuestActions guestActions = new GuestActions(this.sessionService);
                guestActions.start();

            } else if (sessionService.getCurrentSessionUser().getRole().equals(Role.USER)) {
                UserActions userActions = new UserActions(this.sessionService);
                System.out.println(sessionService.getSessionState());
                userActions.start();

            } else if (sessionService.getCurrentSessionUser().getRole().equals(Role.ADMIN)) {
                AdminActions adminActions = new AdminActions(this.sessionService);
                adminActions.start();

            }
        }
    }
}
