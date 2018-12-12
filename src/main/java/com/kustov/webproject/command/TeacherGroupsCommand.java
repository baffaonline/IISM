package com.kustov.webproject.command;

import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.Teacher;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.SubjectReceiver;
import com.kustov.webproject.service.GroupCoursePair;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class TeacherGroupsCommand implements Command {
    private SubjectReceiver receiver;

    TeacherGroupsCommand(SubjectReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page = "/jsp/content/teacher_group.jsp";
        User user = (User)request.getSession().getAttribute("user");
        if (user.getType() == UserType.GUEST){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
        }
        Teacher teacher = (Teacher)request.getSession().getAttribute("role");
        String subjectString = request.getParameter("subject_id");
        if (subjectString == null){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
        }
        int subjectId = Integer.parseInt(subjectString);
        try {
            Subject subject = receiver.findSubjectById(subjectId);
            subject.setTeacher(teacher);
            ArrayList<GroupCoursePair> groupCoursePairList = receiver.findGroupAndCourseBySubjectAndTeacher(subject, teacher);
            request.setAttribute("groupCourse", groupCoursePairList);
            request.getSession().setAttribute("subject", subject);
        }catch (ServiceException exc){
            throw new CommandException(exc.getMessage(), exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
