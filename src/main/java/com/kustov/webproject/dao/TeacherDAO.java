package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Student;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherDAO extends AbstractEntityDAO<Integer, Teacher> {
    private final static String SQL_FIND_TEACHER_BY_USER_ID = "SELECT teacher.id, name, surname, fathername, info " +
            "FROM teacher WHERE user_id = ?";

    private final static String SQL_FIND_TEACHER_BY_ID = "SELECT teacher.id, name, surname, fathername, info " +
            "FROM teacher WHERE teacher.id = ?";

    private final static String SQL_FIND_TEACHER_BY_STUDENT_AND_SUBJECT = "SELECT teacher.id, name, surname, fathername, " +
            "info FROM teacher JOIN group_subject ON teacher.id = teacher_id WHERE group_subject.group = ? " +
            "and course = ? and subject_id = ?";

    @Override
    public List<Teacher> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer insert(Teacher entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Teacher findById(Integer id) throws DAOException {
        return findTeacherBySomeId(id, SQL_FIND_TEACHER_BY_ID);
    }

    public Teacher findByUserId(Integer id) throws DAOException{
        return findTeacherBySomeId(id, SQL_FIND_TEACHER_BY_USER_ID);
    }

    private Teacher findTeacherBySomeId(int id, String sqlQuery) throws DAOException{
        Teacher teacher = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                teacher = createTeacherFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return teacher;
    }

    public Teacher findTeacherByStudentAndSubject(Student student, int subjectId) throws DAOException{
        Teacher teacher = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_TEACHER_BY_STUDENT_AND_SUBJECT)) {
            statement.setInt(1, student.getGroup());
            statement.setInt(2, student.getCourse());
            statement.setInt(3, subjectId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                teacher = createTeacherFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return teacher;
    }

    private Teacher createTeacherFromResultSet(ResultSet resultSet) throws SQLException{
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt(1));
        teacher.setName(resultSet.getString("name"));
        teacher.setSurname(resultSet.getString("surname"));
        teacher.setFathername(resultSet.getString("fathername"));
        teacher.setInfo(resultSet.getString("info"));
        return teacher;
    }
}
