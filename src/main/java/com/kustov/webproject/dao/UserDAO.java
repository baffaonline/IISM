package com.kustov.webproject.dao;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class UserDAO.
 */
public class UserDAO extends AbstractEntityDAO<Integer, User> {

    /**
     * The Constant SQL_SELECT_ALL_USERS.
     */
    private static final String SQL_SELECT_ALL_USERS = "SELECT user.id, login, password FROM user";

    /**
     * The Constant SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD.
     */
    private static final String SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT user.id, login, password" +
            "    FROM user WHERE login = ? AND password = ?";

    /**
     * The Constant SQL_SELECT_USER_BY_ID.
     */
    private static final String SQL_SELECT_USER_BY_ID = "SELECT user.id, login, password, " +
            "user.role" +
            "    FROM user JOIN role\n" +
            "    ON user.role = role.id WHERE user.id = ?";


    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
        return users;
    }


    @Override
    public User findById(Integer id) throws DAOException {
        User user = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return user;
    }

    @Override
    public Integer insert(User user) throws DAOException {
        throw new UnsupportedOperationException("Добавлять можно только через СУБД");
    }

    /**
     * Find user by username and password.
     *
     * @param username the username
     * @param password the password
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUserByUsernameAndPassword(String username, String password) throws DAOException {
        User user = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return user;
    }


    /**
     * Creates the user from result set.
     *
     * @param resultSet the result set
     * @return the user
     * @throws SQLException the SQL exception
     */
    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt(SQLConstant.USER_ID));
        user.setUsername(resultSet.getString(SQLConstant.USERNAME));
        user.setPassword(resultSet.getString(SQLConstant.PASSWORD));
        return user;
    }
}

