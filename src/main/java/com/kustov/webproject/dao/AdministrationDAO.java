package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Administration;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdministrationDAO extends AbstractEntityDAO<Integer, Administration> {
    private final static String SQL_FIND_ADMINISTRATION_BY_USER_ID = "SELECT administration.id, name, surname, " +
            "fathername, info FROM administration WHERE user_id = ?";

    @Override
    public List<Administration> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer insert(Administration entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Administration findById(Integer id) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public Administration findByUserId(Integer id) throws DAOException{
        Administration administration = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ADMINISTRATION_BY_USER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                administration = createAdministrationFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return administration;
    }

    private Administration createAdministrationFromResultSet(ResultSet resultSet) throws SQLException{
        Administration administration = new Administration();
        administration.setId(resultSet.getInt(1));
        administration.setName(resultSet.getString("name"));
        administration.setSurname(resultSet.getString("surname"));
        administration.setFathername(resultSet.getString("fathername"));
        administration.setInfo(resultSet.getString("info"));
        return administration;
    }
}
