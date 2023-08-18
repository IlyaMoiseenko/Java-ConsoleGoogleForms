package storages.jdbc;

import config.JdbcPostgresConfig;
import interfaces.HistoryStorage;
import models.History;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcHistoryStorage implements HistoryStorage {
    private Connection connection;

    private final String ADD_HISTORY = "insert into \"history\" values (?, ?, ?)";
    private final String GET_ALL_HISTORY = "select * from \"history\"";
    private final String GET_HISTORY_BY_USER_ID = "select * from \"history\" where userid = ?";

    public JdbcHistoryStorage() {
        try {
            connection = JdbcPostgresConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(History history) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_HISTORY);
            preparedStatement.setString(1, history.getId().toString());
            preparedStatement.setString(2, history.getUserId().toString());
            preparedStatement.setString(3, history.getFormId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<History> getAll() {
        List<History> allHistory = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(GET_ALL_HISTORY);
            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resulSetUserId = resultSet.getString(2);
                String resultSetFormId = resultSet.getString(3);

                History history = new History(
                        UUID.fromString(resultSetId),
                        UUID.fromString(resulSetUserId),
                        UUID.fromString(resultSetFormId)
                );

                allHistory.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allHistory;
    }

    @Override
    public List<History> getByUser(User user) {
        List<History> allHistoryByUser = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_HISTORY_BY_USER_ID);
            preparedStatement.setString(1, user.getId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resulSetUserId = resultSet.getString(2);
                String resultSetFormId = resultSet.getString(3);

                History history = new History(
                        UUID.fromString(resultSetId),
                        UUID.fromString(resulSetUserId),
                        UUID.fromString(resultSetFormId)
                );

                allHistoryByUser.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allHistoryByUser;
    }
}
