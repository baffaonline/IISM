package com.kustov.webproject.command;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.MarkReceiver;
import com.kustov.webproject.logic.StudentReceiver;
import com.kustov.webproject.logic.SubjectReceiver;
import com.kustov.webproject.logic.TeacherReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupSubjectCommand implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page = "/jsp/content/group_subject.jsp";
        User user = (User)request.getSession().getAttribute("user");
        try {
            switch (user.getType()) {
                case STUDENT:
                    request.getSession().removeAttribute("subjects");
                    Student student = (Student) request.getSession().getAttribute("role");
                    SubjectReceiver subjectReceiver = new SubjectReceiver();
                    String subjectString = request.getParameter("subject_id");
                    if (subjectString == null){
                        return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
                    }
                    Subject subject = subjectReceiver.findSubjectById(Integer.parseInt(subjectString));
                    TeacherReceiver teacherReceiver = new TeacherReceiver();
                    Teacher teacher = teacherReceiver.findTeacherByStudentAndSubject(student, subject);
                    subject.setTeacher(teacher);
                    request.getSession().setAttribute("subject", subject);
                    StudentReceiver studentReceiver = new StudentReceiver();
                    List<Student> students = studentReceiver.findStudentsByGroupAndCourse(student.getGroup(), student.getCourse());
                    MarkReceiver markReceiver = new MarkReceiver();
                    List<Mark> marks = markReceiver.findMissesByGroupAndCourse(student, subject);
                    Map<Student, ArrayList<Mark>> studentListMap = new HashMap<>();
                    changeMap(studentListMap, marks);
                    for (Student student1 : students){
                        if (studentListMap.containsKey(student1)){
                            student1.setMarks(studentListMap.get(student1));
                        }
                    }
                    request.getSession().setAttribute("students", students);
                    break;
                case TEACHER:
                    Subject subject1 = (Subject)request.getSession().getAttribute("subject");
                    StudentReceiver studentReceiver1 = new StudentReceiver();
                    String groupString = request.getParameter("group");
                    if (groupString == null){
                        return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
                    }
                    String courseString = request.getParameter("course");
                    if (courseString == null){
                        return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
                    }
                    int group = Integer.parseInt(groupString);
                    int course = Integer.parseInt(courseString);
                    List<Student> students1 = studentReceiver1.findStudentsByGroupAndCourse(group, course);
                    MarkReceiver markReceiver1 = new MarkReceiver();
                    List<Mark> marks1 = markReceiver1.findMissesByGroupAndCourse(students1.get(0), subject1);
                    Map<Student, ArrayList<Mark>> studentListMap1 = new HashMap<>();
                    changeMap(studentListMap1, marks1);
                    for (Student student1 : students1){
                        if (studentListMap1.containsKey(student1)){
                            student1.setMarks(studentListMap1.get(student1));
                        }
                    }
                    request.getSession().setAttribute("students", students1);
                    request.getSession().setAttribute("group", group);
                    request.getSession().setAttribute("course", course);
                    break;
                case GUEST:
                    return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
            }
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }

    static void changeMap(Map<Student, ArrayList<Mark>> map, List<Mark> marks){
        for (Mark mark : marks){
            if (map.containsKey(mark.getStudent())){
                map.get(mark.getStudent()).add(mark);
            }else {
                map.put(mark.getStudent(), new ArrayList<>());
                map.get(mark.getStudent()).add(mark);
            }
        }
    }
}
