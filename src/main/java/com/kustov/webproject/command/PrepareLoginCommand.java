package com.kustov.webproject.command;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class PrepareLoginCommand.
 */
public class PrepareLoginCommand implements Command {

    @Override
    public CommandPair execute(HttpServletRequest request) {
        String page = "/jsp/user/authorization.jsp";
        if (request.getHeader("referer") == null){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, "/index.jsp");
        }
        request.getSession().setAttribute("page", "/index.jsp");
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
