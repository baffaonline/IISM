package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class BeforeAddCategory implements Command {
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        User user = (User)request.getSession().getAttribute("user");
        if (user.getType() != UserType.TEACHER){
            return new CommandPair(CommandPair.DispatchType.FORWARD, "/index.jsp");
        }
        else return new CommandPair(CommandPair.DispatchType.FORWARD, "/jsp/content/add_category.jsp");
    }
}
