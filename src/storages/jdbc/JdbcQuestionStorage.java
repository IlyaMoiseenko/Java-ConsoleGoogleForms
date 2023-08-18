package storages.jdbc;

import config.JdbcPostgresConfig;
import interfaces.QuestionStorage;
import models.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcQuestionStorage implements QuestionStorage {
    private Connection connection;

    private final String ADD_QUESTION = "insert  into \"question\" values (?, ?, ?)";
    private final String GET_QUESTION_BY_ID = "select * from \"question\" where id = ?";
    private final String GET_ALL_QUESTION = "select * from \"question\"";
    private final String FIND_ALL_QUESTIONS_BY_FORM_ID = "select * from \"question\" where idform = ?";

    public JdbcQuestionStorage() {
        try {
            connection = JdbcPostgresConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUESTION);
            preparedStatement.setString(1, question.getId().toString());
            preparedStatement.setString(2, question.getTitle());
            preparedStatement.setString(3, question.getFormId().toString());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(List<Question> questions) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUESTION);

            for (Question question : questions) {
                preparedStatement.setString(1, question.getId().toString());
                preparedStatement.setString(2, question.getTitle());
                preparedStatement.setString(3, question.getFormId().toString());

                preparedStatement.execute();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Question get(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_QUESTION_BY_ID);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetTitle = resultSet.getString(2);
                String resultSetFormId = resultSet.getString(3);

                preparedStatement.close();
                return new Question(
                        UUID.fromString(resultSetId),
                        resultSetTitle,
                        UUID.fromString(resultSetFormId)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Question> findAll() {
        List<Question> allQuestion = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(GET_ALL_QUESTION);
            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetTitle = resultSet.getString(2);
                String resultSetFormId = resultSet.getString(3);

                Question question = new Question(
                        UUID.fromString(resultSetId),
                        resultSetTitle,
                        UUID.fromString(resultSetFormId)
                );

                allQuestion.add(question);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return allQuestion;
    }

    @Override
    public List<Question> findAllByFormId(UUID id) {
        List<Question> allQuestionByFormId = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUESTIONS_BY_FORM_ID);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetTitle = resultSet.getString(2);
                String resultSetFormId = resultSet.getString(3);

                Question question = new Question(
                        UUID.fromString(resultSetId),
                        resultSetTitle,
                        UUID.fromString(resultSetFormId)
                );

                allQuestionByFormId.add(question);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return allQuestionByFormId;
    }
}
