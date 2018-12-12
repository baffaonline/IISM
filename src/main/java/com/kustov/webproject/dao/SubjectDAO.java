package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.SubjectMarkCategory;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.GroupCoursePair;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO extends AbstractEntityDAO<Integer, Subject>{
    private final static String SQL_FIND_SUBJECTS_BY_GROUP_AND_COURSE = "SELECT subject.id as subj_id, " +
            "subject.name as subj_name, " +
            "group_subject.group as subj_group, course " +
            "FROM subject JOIN group_subject ON subject_id = subject.id WHERE group_subject.group = ? and course = ?";

    private final static String SQL_FIND_SUBJECTS_BY_TEACHER_ID = "SELECT distinct subject.id as subj_id, \n" +
            "            subject.name as subj_name\n" +
            "            FROM subject JOIN group_subject ON subject_id = subject.id WHERE teacher_id = ?";

    private final static String SQL_FIND_ALL_SUBJECTS = "SELECT distinct subject.id as subject_id, \n" +
            "            subject.name as subject_name\n" +
            "            FROM subject JOIN group_subject ON subject_id = subject.id";

    private final static String SQL_FIND_BY_ID = "SELECT id, name FROM subject WHERE id = ?";

    private final static String SQL_FIND_GROUP_AND_COURSE_BY_SUBJECT_AND_TEACHER = "SELECT group_subject.group as group_s, " +
            "course " +
            " FROM group_subject where subject_id = ? and teacher_id = ?";

    private final static String SQL_FIND_SUBJECT_DATES_BY_ID = "SELECT class_date FROM subject_date WHERE subject_id = ?";

    private final static String SQL_FIND_CATEGORIES_BY_SUBJECT_ID = "SELECT id, category, date FROM subject_category WHERE" +
            "  subject_id = ?";

    private final static String SQL_INSERT_CATEGORY = "INSERT INTO subject_category VALUES (NULL, ?, ?, ?)";

    private static final String SQL_FIND_CATEGORY_ID = "SELECT id \n" +
            "FROM subject_category \n" +
            "WHERE id=(\n" +
            "    SELECT max(id) FROM mark\n" +
            "    )";

    private final static String SQL_INSERT_DATE = "INSERT INTO subject_date VALUES(?, ?)";

    @Override
    public List<Subject> findAll() throws DAOException {
        ArrayList<Subject> subjects = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_SUBJECTS);
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("subject_id"));
                subject.setName(resultSet.getString("subject_name"));
                subjects.add(subject);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subjects;
    }

    @Override
    public Integer insert(Subject entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Subject findById(Integer id) throws DAOException {
        Subject subject = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                subject = new Subject();
                subject.setId(resultSet.getInt("id"));
                subject.setName(resultSet.getString("name"));
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subject;
    }

    public int insertCategory(SubjectMarkCategory category, int subjectId) throws DAOException{
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CATEGORY)) {
            statement.setInt(1, subjectId);
            statement.setString(2, category.getCategory());
            if (category.getDate() != null) {
                statement.setDate(3, Date.valueOf(category.getDate()));
            }else {
                statement.setNull(3, Types.DATE);
            }
            if (statement.executeUpdate() != 0){
                Statement idStatement = connection.createStatement();
                ResultSet resultSet = idStatement.executeQuery(SQL_FIND_CATEGORY_ID);
                resultSet.next();
                return resultSet.getInt("id");
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return 0;
    }

    public int insertDate(LocalDate date, int subjectId) throws DAOException{
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_DATE)) {
            statement.setInt(1, subjectId);
            statement.setDate(2, Date.valueOf(date));
            return statement.executeUpdate();
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    public ArrayList<GroupCoursePair> findCourseAndGroupBySubjectAndTeacher(int subjectId, int teacherId) throws DAOException{
        ArrayList<GroupCoursePair> groupCoursePair = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_GROUP_AND_COURSE_BY_SUBJECT_AND_TEACHER)) {
            statement.setInt(1, subjectId);
            statement.setInt(2, teacherId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groupCoursePair.add(new GroupCoursePair(resultSet.getInt("group_s"),
                        resultSet.getInt("course")));
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return groupCoursePair;
    }

    public ArrayList<Subject> findSubjectByGroupAndCourse(int group, int course) throws DAOException{
        ArrayList<Subject> subjects = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SUBJECTS_BY_GROUP_AND_COURSE)) {
            statement.setInt(1, group);
            statement.setInt(2, course);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("subj_id"));
                subject.setName(resultSet.getString("subj_name"));
                subject.setGroup(resultSet.getInt("subj_group"));
                subject.setCourse(resultSet.getInt("course"));
                subjects.add(subject);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subjects;
    }

    public ArrayList<Subject> findSubjectByTeacherId(int id) throws DAOException{
        ArrayList<Subject> subjects = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SUBJECTS_BY_TEACHER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt("subj_id"));
                subject.setName(resultSet.getString("subj_name"));
                subjects.add(subject);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return subjects;
    }

    public ArrayList<LocalDate> findDatesBySubjectId(int id) throws DAOException{
        ArrayList<LocalDate> dates = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SUBJECT_DATES_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dates.add(resultSet.getDate("class_date").toLocalDate());
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return dates;
    }

    public ArrayList<SubjectMarkCategory> findCategoriesBySubjectId(int id) throws DAOException{
        ArrayList<SubjectMarkCategory> categories = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CATEGORIES_BY_SUBJECT_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                SubjectMarkCategory category = new SubjectMarkCategory();
                category.setId(resultSet.getInt("id"));
                category.setCategory(resultSet.getString("category"));
                Date date = resultSet.getDate("date");
                if (date != null) {
                    category.setDate(resultSet.getDate("date").toLocalDate());
                }
                categories.add(category);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return categories;
    }
}
