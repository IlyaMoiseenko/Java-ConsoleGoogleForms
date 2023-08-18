package storages.jdbc;

import config.JdbcPostgresConfig;
import interfaces.AnswerStorage;
import models.Answer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcAnswerStorage implements AnswerStorage {
    private Connection connection;

    private final String ADD_ANSWER = "insert into \"answer\" values (?, ?, ?)";
    private final String GET_ANSWER_BY_ID = "select * from \"answer\" where id = ?";
    private final String GET_ALL_ANSWERS = "select * from \"answer\"";
    private final String GET_ALL_ANSWERS_BY_FORM_ID = "select * from \"answer\" where questionid = ?";

    public JdbcAnswerStorage() {
        try {
            connection = JdbcPostgresConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void add(Answer answer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ANSWER);
            preparedStatement.setString(1, answer.getId().toString());
            preparedStatement.setString(2, answer.getTitle());
            preparedStatement.setString(3, answer.getQuestionId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(List<Answer> answers) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ANSWER);

            for (Answer answer : answers) {
                preparedStatement.setString(1, answer.getId().toString());
                preparedStatement.setString(2, answer.getTitle());
                preparedStatement.setString(3, answer.getQuestionId().toString());

                preparedStatement.execute();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Answer get(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWER_BY_ID);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetTitle = resultSet.getString(2);
                String resultSetQuestionId = resultSet.getString(3);

                preparedStatement.close();

                return new Answer(
                        UUID.fromString(resultSetId),
                        resultSetTitle,
                        UUID.fromString(resultSetQuestionId)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Answer> findAll() {
        List<Answer> allAnswers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_ANSWERS);

            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetTitle = resultSet.getString(2);
                String resultSetQuestionId = resultSet.getString(3);

                Answer answer = new Answer(
                        UUID.fromString(resultSetId),
                        resultSetTitle,
                        UUID.fromString(resultSetQuestionId)
                );

                allAnswers.add(answer);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAnswers;
    }

    @Override
    public List<Answer> findAllByQuestionId(UUID id) {
        List<Answer> allAnswersByQuestionId = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ANSWERS_BY_FORM_ID);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetTitle = resultSet.getString(2);
                String resultSetQuestionId = resultSet.getString(3);

                Answer answer = new Answer(
                        UUID.fromString(resultSetId),
                        resultSetTitle,
                        UUID.fromString(resultSetQuestionId)
                );

                allAnswersByQuestionId.add(answer);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAnswersByQuestionId;
    }
}
