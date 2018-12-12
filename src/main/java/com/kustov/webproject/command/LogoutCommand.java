package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.pool.DBConnectionPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The Class LogoutCommand.
 */
public class LogoutCommand implements Command {

    /**
     * Instantiates a new logout command.
     */
    LogoutCommand() {
    }


    @Override
    public CommandPair execute(HttpServletRequest request) {
        String mainPage = "/index.jsp";
        User user = (User)request.getSession().getAttribute("user");
        if (user.getType() == UserType.GUEST){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
        }
        HttpSession session = request.getSession(false);
        session.invalidate();
        return new CommandPair(CommandPair.DispatchType.REDIRECT, mainPage);
    }
}
