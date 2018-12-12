package com.kustov.webproject.command;

import com.kustov.webproject.entity.Student;
import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.SubjectReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SubjectsCommand implements Command{
    private SubjectReceiver receiver;

    SubjectsCommand(SubjectReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        String pageSubjects = "/jsp/content/subjects.jsp";
        User thisUser = (User)request.getSession().getAttribute("user");
        try {
            switch (thisUser.getType()) {
                case STUDENT:
                    Student student = (Student) request.getSession().getAttribute("role");
                    ArrayList<Subject> subjects = receiver.findSubjectsByCourseAndGroup(student.getCourse(), student.getGroup());
                    request.getSession().setAttribute("subjects", subjects);
                    break;
                case TEACHER:
                    Teacher teacher = (Teacher) request.getSession().getAttribute("role");
                    ArrayList<Subject> subjects1 = receiver.findSubjectsByTeacherId(teacher);
                    request.getSession().setAttribute("subjects", subjects1);
                    break;
                case ADMINISTRATION:
                    List<Subject> subjects2 = receiver.findAllSubjects();
                    request.getSession().setAttribute("subjects", subjects2);
                    break;
                case GUEST:
                    return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
            }
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
        page = pageSubjects;
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
