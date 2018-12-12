package com.kustov.webproject.dao;

import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleDao{

    private final static String FIND_ROLE_BY_USER_ID = "SELECT name FROM user join role ON user.role = role.id " +
            "WHERE user.id = ?";

    public UserType findRoleByUserId(int id) throws DAOException{
        UserType userType = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ROLE_BY_USER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userType = UserType.valueOf(resultSet.getString("name").toUpperCase());
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return userType;
    }

}
