package com.kustov.webproject.command;

import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.SubjectMarkCategory;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.SubjectReceiver;
import com.kustov.webproject.service.StringDateFormatter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class AddCategory implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        User user = (User)request.getSession().getAttribute("user");
        if (user.getType() != UserType.TEACHER){
            return new CommandPair(CommandPair.DispatchType.FORWARD, "/index.jsp");
        }
        String name = request.getParameter("name");
        if (name == null){
            return new CommandPair(CommandPair.DispatchType.FORWARD, "/index.jsp");
        }
        String dateString = request.getParameter("date");
        LocalDate date = null;
        if (dateString != null){
            date = StringDateFormatter.getDateFromString(dateString);
        }
        Subject subject = (Subject)request.getSession().getAttribute("subject");
        try {
            SubjectReceiver receiver = new SubjectReceiver();
            if ("Пропуск".equals(name)) {
                if (receiver.insertDate(date, subject.getId()) != 0){
                    request.getSession().removeAttribute("subject");
                    Subject subject1 = receiver.findSubjectById(subject.getId());
                    request.getSession().setAttribute("subject", subject1);
                    return new CommandPair(CommandPair.DispatchType.FORWARD, "/index.jsp");
                }else {
                    request.setAttribute("error",  "Такая дата уже есть");
                    return new CommandPair(CommandPair.DispatchType.FORWARD, "/index.jsp");
                }
            } else {
                if (receiver.insertCategory(new SubjectMarkCategory(0, name, date), subject.getId()) != 0){
                    request.getSession().removeAttribute("subject");
                    Subject subject1 = receiver.findSubjectById(subject.getId());
                    request.getSession().setAttribute("subject", subject1);
                    return new CommandPair(CommandPair.DispatchType.FORWARD, "/jsp/content/marks_categories.jsp");
                }else {
                    request.setAttribute("error", "Такая категория уже есть");
                    return new CommandPair(CommandPair.DispatchType.FORWARD, "/jsp/content/add_category.jsp");
                }
            }
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
    }
}
