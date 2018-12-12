package com.kustov.webproject.dao;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkDAO extends AbstractEntityDAO<Integer, Mark>{
    private static final String SQL_FIND_MARKS_BY_STUDENT_AND_SUBJECT = "SELECT id, mark, subject, teacher_id, category, " +
            "mark_date \n" +
            "FROM mark WHERE student_id = ? AND subject = ?";

    private static final String SQL_FIND_MARKS_BY_COURSE_AND_GROUP = "SELECT mark.id as mark_id, " +
            "student_id, teacher_id, mark, subject, name, surname, fathername, info, " +
            "category, mark_date FROM mark JOIN student ON student_id = student.id WHERE student.group = ? and course = ?" +
            " and subject = ?";

    private static final String SQL_FIND_ID = "SELECT id \n" +
            "FROM mark \n" +
            "WHERE id=(\n" +
            "    SELECT max(id) FROM mark\n" +
            "    )";

    private static final String SQL_FIND_MARKS_WITHOUT_CATEGORY = "SELECT mark.id as mark_id, " +
            "student_id, teacher_id, mark, subject, name, surname, fathername, info, " +
            "mark_date FROM mark JOIN student ON student_id = student.id WHERE student.group = ? and course = ?" +
            " and subject = ? and category IS NULL";

    private static final String SQL_FIND_MARK_BY_INFO = "SELECT id FROM mark WHERE student_id = ? and teacher_id = ?" +
            " and subject = ? and mark_date = ? and category = ?";

    private static final String SQL_FIND_CATEGORY_BY_ID = "SELECT category, date FROM subject_category WHERE id = ?";

    private static final String SQL_INSERT_MARK = "INSERT INTO mark VALUES (NULL, ?, ?, ?, ?, ?, ?)";

    @Override
    public List<Mark> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer insert(Mark entity) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MARK)) {
            statement.setInt(1, entity.getStudent().getId());
            statement.setInt(2, entity.getTeacher().getId());
            statement.setString(3, entity.getMark());
            statement.setInt(4, entity.getSubject().getId());
            if (entity.getType().getId() != 0) {
                statement.setInt(5, entity.getType().getId());
            }else {
                statement.setNull(5, Types.INTEGER);
            }
            statement.setDate(6, Date.valueOf(entity.getDate()));
            if (statement.executeUpdate() != 0){
                Statement idStatement = connection.createStatement();
                ResultSet resultSet = idStatement.executeQuery(SQL_FIND_ID);
                resultSet.next();
                return resultSet.getInt("id");
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return 0;
    }

    @Override
    public Mark findById(Integer id) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public int findMarkByInfo(Mark mark) throws DAOException{
        int id = 0;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_MARK_BY_INFO)) {
            statement.setInt(1, mark.getStudent().getId());
            statement.setInt(2, mark.getTeacher().getId());
            statement.setInt(3, mark.getSubject().getId());
            statement.setDate(4, Date.valueOf(mark.getDate()));
            if (mark.getType().getId() != 0) {
                statement.setInt(5, mark.getType().getId());
            }else {
                statement.setNull(5, Types.INTEGER);
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                id = resultSet.getInt("id");
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return id;
    }

//    public List<Mark> findMarksByStudentAndSubject(int studentId, int subjectId) throws DAOException{
//        List<Mark> marks = new ArrayList<>();
//        try (Connection connection = DBConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_FIND_MARKS_BY_STUDENT_AND_SUBJECT)) {
//            statement.setInt(1, studentId);
//            statement.setInt(2, subjectId);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Mark mark = new Mark();
//                mark.setId(resultSet.getInt("mark_id"));
//                mark.setMark(resultSet.getString("mark"));
//                mark.setType(chooseCategory(resultSet.getInt("category")));
//                mark.setDate(resultSet.getDate("mark_date").toLocalDate());
//                mark.setTeacher(new Teacher(resultSet.getInt("teacher_id"), null, null, null, null, null));
//                marks.add(mark);
//            }
//        } catch (ConnectionException | SQLException exc) {
//            throw new DAOException(exc);
//        }
//        return marks;
//    }

    public List<Mark> findMarksByGroupAndCourseAndSubject(int group, int course,int subjectId) throws DAOException{
        List<Mark> marks = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_MARKS_BY_COURSE_AND_GROUP)) {
            statement.setInt(1, group);
            statement.setInt(2, course);
            statement.setInt(3, subjectId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Mark mark = new Mark();
                mark.setId(resultSet.getInt("mark_id"));
                mark.setMark(resultSet.getString("mark"));
                mark.setType(new SubjectMarkCategory(resultSet.getInt("category"), null, null));
                mark.setDate(resultSet.getDate("mark_date").toLocalDate());
                mark.setStudent(new Student(resultSet.getInt("student_id"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("fathername"), group,
                        course, new User(), resultSet.getString("info"), new ArrayList<>()));
                mark.setTeacher(new Teacher(resultSet.getInt("teacher_id"), null, null, null, null, null));
                marks.add(mark);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return marks;
    }

    public List<Mark> findMissesByGroupAndCourseAndSubject(int group, int course,int subjectId) throws DAOException{
        List<Mark> marks = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_MARKS_WITHOUT_CATEGORY)) {
            statement.setInt(1, group);
            statement.setInt(2, course);
            statement.setInt(3, subjectId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Mark mark = new Mark();
                mark.setId(resultSet.getInt("mark_id"));
                mark.setMark(resultSet.getString("mark"));
                Date date = resultSet.getDate("mark_date");
                if (date != null) {
                    mark.setDate(date.toLocalDate());
                }
                mark.setStudent(new Student(resultSet.getInt("student_id"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("fathername"), group,
                        course, new User(), resultSet.getString("info"), new ArrayList<>()));
                mark.setTeacher(new Teacher(resultSet.getInt("teacher_id"), null, null, null, null, null));
                marks.add(mark);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return marks;
    }

    public SubjectMarkCategory findCategoryById(int id) throws DAOException{
        SubjectMarkCategory subjectMarkCategory = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CATEGORY_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                subjectMarkCategory = new SubjectMarkCategory();
                subjectMarkCategory.setId(id);
                subjectMarkCategory.setCategory(resultSet.getString("category"));
                Date date = resultSet.getDate("date");
                if (date != null) {
                    subjectMarkCategory.setDate(date.toLocalDate());
                }
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subjectMarkCategory;
    }
}
