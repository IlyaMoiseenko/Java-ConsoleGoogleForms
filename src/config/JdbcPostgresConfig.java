package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcPostgresConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/ConsoleGoogleForms";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
