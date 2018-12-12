package com.kustov.webproject.logic;

import com.kustov.webproject.dao.MarkDAO;
import com.kustov.webproject.entity.Mark;
import com.kustov.webproject.entity.Student;
import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.util.List;

public class MarkReceiver {
    public List<Mark> findMarksByGroupAndCourse(Student student, Subject subject) throws ServiceException{
        MarkDAO dao = new MarkDAO();
        try{
            List<Mark> marks = dao.findMarksByGroupAndCourseAndSubject(student.getGroup(),
                                        student.getCourse(), subject.getId());
            for (Mark mark : marks){
                if (mark.getType() != null){
                    mark.setType(dao.findCategoryById(mark.getType().getId()));
                }
            }
            return marks;
        }catch(DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public List<Mark> findMissesByGroupAndCourse(Student student, Subject subject) throws ServiceException{
        MarkDAO dao = new MarkDAO();
        try{
            List<Mark> marks =  dao.findMissesByGroupAndCourseAndSubject(student.getGroup(),
                                student.getCourse(), subject.getId());
            for (Mark mark : marks){
                if (mark.getType() != null){
                    mark.setType(dao.findCategoryById(mark.getType().getId()));
                }
            }
            return marks;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Integer insertMark(Mark mark) throws ServiceException{
        MarkDAO dao = new MarkDAO();
        try {
            if (dao.findMarkByInfo(mark) != 0) {
                return 0;
            }
            else {
                return dao.insert(mark);
            }
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
