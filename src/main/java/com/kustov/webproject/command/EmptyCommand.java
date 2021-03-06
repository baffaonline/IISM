package com.kustov.webproject.command;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class EmptyCommand.
 */
public class EmptyCommand implements Command {

    @Override
    public CommandPair execute(HttpServletRequest request) {
        return new CommandPair(CommandPair.DispatchType.REDIRECT, "/");
    }
}
