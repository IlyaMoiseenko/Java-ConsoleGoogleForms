package storages.jdbc;

import config.JdbcPostgresConfig;
import interfaces.FormStorage;
import models.Form;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcFormStorage implements FormStorage {
    private Connection connection;

    public JdbcFormStorage() {
        try {
            connection = JdbcPostgresConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public Form add(Form form) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into \"form\" values (?, ?, ?)");
            preparedStatement.setString(1, form.getId().toString());
            preparedStatement.setString(2, form.getTitle());
            preparedStatement.setString(3, form.getDescription());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return form;
    }

    @Override
    public Form get(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from \"form\" where id  = ?");
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UUID resultSetId = UUID.fromString(resultSet.getString(1));
                String resultSetTitle = resultSet.getString(2);
                String resultSetDescription = resultSet.getString(3);

                preparedStatement.close();
                return new Form(
                        resultSetId,
                        resultSetTitle,
                        resultSetDescription
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Form> findAll() {
        List<Form> allForms = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from \"form\"");
            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetTitle = resultSet.getString(2);
                String resultSetDescription = resultSet.getString(3);

                Form form = new Form(
                        UUID.fromString(resultSetId),
                        resultSetTitle,
                        resultSetDescription
                );

                allForms.add(form);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allForms;
    }
}
