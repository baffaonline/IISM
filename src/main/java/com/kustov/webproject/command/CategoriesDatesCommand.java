package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class CategoriesDatesCommand implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page = "/jsp/content/categories_dates.jsp";
        User user = (User)request.getSession().getAttribute("user");
        if (user.getType() != UserType.TEACHER){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
