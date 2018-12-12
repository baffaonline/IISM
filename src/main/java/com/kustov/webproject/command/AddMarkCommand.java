package com.kustov.webproject.command;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.MarkReceiver;
import com.kustov.webproject.logic.StudentReceiver;
import com.kustov.webproject.service.StringDateFormatter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AddMarkCommand implements Command {
    private final static String SKIP_PATTERN = "[нН]";

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page = "/jsp/content/marks_categories.jsp";
        User user = (User) request.getSession().getAttribute("user");
        if (user.getType() != UserType.TEACHER) {
            return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
        }
        try {
            StudentReceiver studentReceiver = new StudentReceiver();
            String studentId = request.getParameter("student");
            if (studentId == null) {
                return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
            }
            Student student = studentReceiver.findStudentById(Integer.parseInt(studentId));
            String value = request.getParameter("mark");
            String categoryString = request.getParameter("category");
            if (categoryString == null) {
                return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
            }
            int categoryId = Integer.parseInt(categoryString);
            String dateString = request.getParameter("date");
            LocalDate date = null;
            if (dateString != null){
                date = StringDateFormatter.getDateFromString(request.getParameter("date"));
            }
            Teacher teacher = (Teacher) request.getSession().getAttribute("role");
            Subject subject = (Subject) request.getSession().getAttribute("subject");
            Mark mark = new Mark(0, student, teacher, value, subject,
                    new SubjectMarkCategory(categoryId, null, null), date);
            MarkReceiver receiver = new MarkReceiver();
            if (mark.getType().getId() == 0 && !Pattern.matches(SKIP_PATTERN, mark.getMark())) {
                page = "/MainController".concat("?command=before_add_mark");
                request.setAttribute("error", "Пропуск отмечается русской буквой н");
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            }
            if (receiver.insertMark(mark) != 0) {
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            } else {
                page = "/MainConroller".concat("?command=before_add_mark");
                request.setAttribute("error", "Отметка на данную дату уже стоит");
                return new CommandPair(CommandPair.DispatchType.FORWARD, page);
            }
        } catch (ServiceException exc) {
            throw new CommandException(exc.getMessage(), exc);
        }
    }
}
