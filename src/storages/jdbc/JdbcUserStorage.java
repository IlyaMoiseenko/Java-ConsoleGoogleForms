package storages.jdbc;

import config.JdbcPostgresConfig;
import enums.Role;
import interfaces.UserStorage;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JdbcUserStorage implements UserStorage {
    private Connection connection;

    private final String ADD_USER = "insert into \"user\" values (?, ?, ?, ?)";
    private final String GET_USER_BY_ID = "select * from \"user\" where id = ?";
    private final String GET_USER_BY_USERNAME = "select * from \"user\" where username = ?";

    public JdbcUserStorage() {
        try {
            connection = JdbcPostgresConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User add(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(4, user.getId().toString());
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().toString());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String resultSetId = resultSet.getString(4);
                String resultSetUsername = resultSet.getString(1);
                String resultSetPassword = resultSet.getString(2);
                String resultSetRole = resultSet.getString(3);

                preparedStatement.close();

                return new User(
                        UUID.fromString(resultSetId),
                        resultSetUsername,
                        resultSetPassword,
                        Role.valueOf(resultSetRole)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getByUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String resultSetId = resultSet.getString(4);
                String resultSetUsername = resultSet.getString(1);
                String resultSetPassword = resultSet.getString(2);
                String resultSetRole = resultSet.getString(3);

                preparedStatement.close();

                return new User(
                        UUID.fromString(resultSetId),
                        resultSetUsername,
                        resultSetPassword,
                        Role.valueOf(resultSetRole)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
