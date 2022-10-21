package ru.kpfu.itis.selyantsev.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.selyantsev.dao.DAO;
import ru.kpfu.itis.selyantsev.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import static ru.kpfu.itis.selyantsev.util.PasswordUtil.encrypt;
import static ru.kpfu.itis.selyantsev.util.PostgresConnectionUtil.getConnection;

public class UserDaoImpl implements DAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    private static Connection connection;

    private static final String SQL_FIND_BY_NAME = "select * from account where first_name = ?";

    private static final String SQL_INSERT_USER = "insert into account(first_name, last_name, login_name, " +
            "password_account, email)" +
            "values(?, ?, ?, ?, ?)";

    public static final Function<ResultSet, User> userMapper = row -> {
        try {
            String login = row.getString("login_name");
            String password = row.getString("password_account");
            return new User(login, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void save(User entity) {

        connection = getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getLogin());
            preparedStatement.setString(4, encrypt(entity.getPassword()));
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Save failed! ", e);
        }

    }

    @Override
    public User findByName(String name) {
        connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return userMapper.apply(resultSet);
                }
            } catch (SQLException e) {
                LOGGER.warn("Choice is not correct", e);
            }
        } catch (SQLException e) {
            LOGGER.warn("Illegal Argument, choose correct argument!", e);
        }
        LOGGER.warn("User does not exist");
        return null;
    }

}
