package storages.jdbc;

import config.JdbcPostgresConfig;
import interfaces.UserAnswerStorage;
import models.User;
import models.UserAnswer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcUserAnswerStorage implements UserAnswerStorage {
    private Connection connection;

    private final String ADD_USERANSWER = "insert into \"useranswer\" values (?, ?, ?, ?)";
    private final String GET_ALL_USERANSWER_BY_USER_ID = "select * from \"useranswer\" where userid = ?";

    public JdbcUserAnswerStorage() {
        try {
            connection = JdbcPostgresConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(List<UserAnswer> userAnswers) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USERANSWER);

            for (UserAnswer userAnswer : userAnswers) {
                preparedStatement.setString(1, userAnswer.getId().toString());
                preparedStatement.setString(2, userAnswer.getAnswer());
                preparedStatement.setString(3, userAnswer.getQuestionId().toString());
                preparedStatement.setString(4, userAnswer.getUserId().toString());

                preparedStatement.execute();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserAnswer> getByUserId(UUID id) {
        List<UserAnswer> allUserAnswerByUserId = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERANSWER_BY_USER_ID);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String resultSetId = resultSet.getString(1);
                String resultSetAnswer = resultSet.getString(2);
                String resultSetQuestionId = resultSet.getString(3);
                String resultSetUserId = resultSet.getString(4);

                UserAnswer userAnswer = new UserAnswer(
                        UUID.fromString(resultSetId),
                        resultSetAnswer,
                        UUID.fromString(resultSetQuestionId),
                        UUID.fromString(resultSetUserId)
                );

                allUserAnswerByUserId.add(userAnswer);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUserAnswerByUserId;
    }
}
