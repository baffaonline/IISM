package com.kustov.webproject.logic;

import com.kustov.webproject.dao.StudentDAO;
import com.kustov.webproject.entity.Student;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.util.List;


public class StudentReceiver {
    public Student findStudentByUser(User user) throws ServiceException{
        StudentDAO dao = new StudentDAO();
        try{
            Student student = dao.findByUserId(user.getId());
            student.setUser(user);
            return student;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public List<Student> findStudentsByGroupAndCourse(int group, int course) throws ServiceException{
        StudentDAO dao = new StudentDAO();
        try{
            return dao.findStudentsByGroupAndCourse(group, course);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Student findStudentById(int id) throws  ServiceException{
        StudentDAO dao = new StudentDAO();
        try{
            return dao.findById(id);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
