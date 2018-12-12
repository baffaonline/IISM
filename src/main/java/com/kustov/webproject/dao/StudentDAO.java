package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Student;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends AbstractEntityDAO<Integer, Student>{
    private final static String SQL_FIND_STUDENT_BY_USER_ID = "SELECT student.id, name, surname, fathername, info, " +
            " course, student.group FROM student WHERE user_id = ?";

    private final static String SQL_FIND_STUDENT_BY_ID = "SELECT id, name, surname, fathername, info, " +
            " course, student.group FROM student WHERE student.id = ?";

    private final static String SQL_FIND_STUDENTS_BY_GROUP_AND_COURSE = "SELECT student.id, name, surname, fathername, info, " +
            " course, student.group FROM student WHERE student.group = ? and course = ?";

    @Override
    public List<Student> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer insert(Student entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Student findById(Integer id) throws DAOException {
        Student student = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_STUDENT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = createStudentFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return student;
    }

    public Student findByUserId(Integer id) throws DAOException{
        Student student = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_STUDENT_BY_USER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = createStudentFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return student;
    }

    public List<Student> findStudentsByGroupAndCourse(int group, int course) throws DAOException{
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_STUDENTS_BY_GROUP_AND_COURSE)) {
            statement.setInt(1, group);
            statement.setInt(2, course);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = createStudentFromResultSet(resultSet);
                students.add(student);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return students;
    }

    private Student createStudentFromResultSet(ResultSet resultSet) throws SQLException{
        Student student = new Student();
        student.setId(resultSet.getInt(1));
        student.setName(resultSet.getString("name"));
        student.setSurname(resultSet.getString("surname"));
        student.setFathername(resultSet.getString("fathername"));
        student.setInfo(resultSet.getString("info"));
        student.setCourse(resultSet.getInt("course"));
        student.setGroup(resultSet.getInt("group"));
        return student;
    }
}
