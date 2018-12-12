package com.kustov.webproject.command;

import com.kustov.webproject.entity.Mark;
import com.kustov.webproject.entity.Student;
import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.MarkReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarksCategoriesCommand implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page = "/jsp/content/marks_categories.jsp";
        User user = (User)request.getSession().getAttribute("user");
        try {
            switch (user.getType()) {
                case STUDENT:
                    Student student = (Student) request.getSession().getAttribute("role");
                    ArrayList<Student> students = (ArrayList<Student>)request.getSession().getAttribute("students");
                    Subject subject = (Subject)request.getSession().getAttribute("subject");
                    for (Student student1 : students){
                        student1.getMarks().clear();
                    }
                    MarkReceiver markReceiver = new MarkReceiver();
                    List<Mark> marks = markReceiver.findMarksByGroupAndCourse(student, subject);
                    Map<Student, ArrayList<Mark>> studentListMap = new HashMap<>();
                    GroupSubjectCommand.changeMap(studentListMap, marks);
                    for (Student student1 : students){
                        if (studentListMap.containsKey(student1)){
                            student1.setMarks(studentListMap.get(student1));
                        }
                    }
                    request.getSession().setAttribute("students", students);
                    break;
                case TEACHER:
                    Subject subject1 = (Subject)request.getSession().getAttribute("subject");
                    ArrayList<Student> students1 = (ArrayList<Student>)request.getSession().getAttribute("students");
                    MarkReceiver markReceiver1 = new MarkReceiver();
                    List<Mark> marks1 = markReceiver1.findMarksByGroupAndCourse(students1.get(0), subject1);
                    Map<Student, ArrayList<Mark>> studentListMap1 = new HashMap<>();
                    GroupSubjectCommand.changeMap(studentListMap1, marks1);
                    for (Student student1 : students1){
                        if (studentListMap1.containsKey(student1)){
                            student1.setMarks(studentListMap1.get(student1));
                        }
                    }
                    request.getSession().setAttribute("students", students1);
                    break;
                case GUEST:
                    return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
            }
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
