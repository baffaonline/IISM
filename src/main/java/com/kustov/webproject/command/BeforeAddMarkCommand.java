package com.kustov.webproject.command;

import com.kustov.webproject.entity.Subject;
import com.kustov.webproject.entity.SubjectMarkCategory;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.SubjectReceiver;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;

public class BeforeAddMarkCommand implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page = "/jsp/content/add_mark.jsp";
        User user = (User)request.getSession().getAttribute("user");
        if (user.getType() == UserType.GUEST){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
        }
        try {
            Subject subject = (Subject) request.getSession().getAttribute("subject");
            SubjectReceiver receiver = new SubjectReceiver();
            ArrayList<SubjectMarkCategory> categories = new ArrayList<>();
            categories.add(new SubjectMarkCategory(0, "Пропуск", null));
            categories.addAll(receiver.findCategoriesBySubjectId(subject.getId()));
            request.setAttribute("categories", categories);
            ArrayList<LocalDate> dates = new ArrayList<>(receiver.findDatesBySubjectId(subject.getId()));
            request.getSession().setAttribute("dates", dates);
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
