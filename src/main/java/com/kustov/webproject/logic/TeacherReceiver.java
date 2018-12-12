package com.kustov.webproject.logic;

import com.kustov.webproject.dao.TeacherDAO;
import com.kustov.webproject.entity.Student;
import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

public class TeacherReceiver {
    public Teacher findTeacherByUser(User user) throws ServiceException {
        TeacherDAO dao = new TeacherDAO();
        try{
            Teacher teacher = dao.findByUserId(user.getId());
            teacher.setUser(user);
            return teacher;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Teacher findTeacherById(int id) throws ServiceException {
        TeacherDAO dao = new TeacherDAO();
        try{
            return dao.findById(id);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Teacher findTeacherByStudentAndSubject(Student student, Subject subject) throws ServiceException{
        TeacherDAO dao = new TeacherDAO();
        try{
            return dao.findTeacherByStudentAndSubject(student, subject.getId());
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
