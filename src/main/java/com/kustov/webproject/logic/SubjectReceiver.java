package com.kustov.webproject.logic;

import com.kustov.webproject.dao.SubjectDAO;
import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.SubjectMarkCategory;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.service.GroupCoursePair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SubjectReceiver {
    public ArrayList<Subject> findSubjectsByCourseAndGroup(int course, int group) throws ServiceException{
        SubjectDAO subjectDAO = new SubjectDAO();
        try{
            return subjectDAO.findSubjectByGroupAndCourse(group, course);
        } catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public ArrayList<Subject> findSubjectsByTeacherId(Teacher teacher) throws ServiceException{
        SubjectDAO subjectDAO = new SubjectDAO();
        try{
            ArrayList<Subject> subjects = subjectDAO.findSubjectByTeacherId(teacher.getId());
            for (Subject subject : subjects){
                subject.setTeacher(teacher);
            }
            return subjects;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Subject findSubjectById(int id) throws ServiceException{
        SubjectDAO subjectDAO = new SubjectDAO();
        try{
            Subject subject = subjectDAO.findById(id);
            ArrayList<LocalDate> dates = subjectDAO.findDatesBySubjectId(id);
            Collections.sort(dates);
            subject.setDates(dates);
            ArrayList<SubjectMarkCategory> categories = subjectDAO.findCategoriesBySubjectId(id);
            categories.sort(Comparator.comparing(SubjectMarkCategory::getCategory));
            subject.setCategories(categories);
            return subject;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public ArrayList<SubjectMarkCategory> findCategoriesBySubjectId(int id) throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            return dao.findCategoriesBySubjectId(id);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public ArrayList<LocalDate> findDatesBySubjectId(int id) throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            return dao.findDatesBySubjectId(id);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public int insertCategory(SubjectMarkCategory category, int subjectId) throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            return dao.insertCategory(category, subjectId);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public int insertDate(LocalDate date, int subjectId) throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            ArrayList<LocalDate> dates = dao.findDatesBySubjectId(subjectId);
            if (!dates.contains(date)){
                return dao.insertDate(date, subjectId);
            }else {
                return 0;
            }
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public List<Subject> findAllSubjects() throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            return dao.findAll();
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public ArrayList<GroupCoursePair> findGroupAndCourseBySubjectAndTeacher(Subject subject, Teacher teacher)
            throws ServiceException{
        SubjectDAO dao = new SubjectDAO();
        try{
            return dao.findCourseAndGroupBySubjectAndTeacher(subject.getId(), teacher.getId());
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
