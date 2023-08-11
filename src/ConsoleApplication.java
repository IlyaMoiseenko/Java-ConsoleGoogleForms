import interfaces.Reader;
import interfaces.Writer;
import io.ConsoleReader;
import io.ConsoleWriter;
import services.UserService;

public class ConsoleApplication {
    private final UserService userService = new UserService();
    private Writer writer = new ConsoleWriter();
    private Reader reader = new ConsoleReader();

    public void start() {
        while (true) {
            writer.write("Enter username: ");
            String username = reader.readLine();

            writer.write("Enter password: ");
            String password = reader.readLine();

            if (userService.create(username, password))
                writer.write("Done!");
        }
    }
}
