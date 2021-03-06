package com.kustov.webproject.controller;

import com.kustov.webproject.command.Command;
import com.kustov.webproject.command.CommandFactory;
import com.kustov.webproject.command.CommandPair;
import com.kustov.webproject.command.EmptyCommand;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


/**
 * The Class Controller.
 */
@WebServlet("/MainController")
@MultipartConfig
public class Controller extends HttpServlet {

    /**
     * The path page default.
     */
    private static String pathPageDefault;


    /**
     * Inits the servlet.
     *
     * @param config the config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        pathPageDefault = "index.jsp";
    }

    /**
     * Do post.
     *
     * @param req  the req
     * @param resp the resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        commandDefine(req, resp);
    }

    /**
     * Do get.
     *
     * @param req  the req
     * @param resp the resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        commandDefine(req, resp);
    }

    /**
     * Command define.
     *
     * @param req  the req
     * @param resp the resp
     */
    private void commandDefine(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF8");
            if (req.getSession() != null) {
                Object isUpdatedObject = req.getSession().getAttribute("isUpdated");
                boolean isUpdated;
                if (isUpdatedObject != null) {
                    isUpdated = (boolean) isUpdatedObject;
                    if (!isUpdated) {
                        Optional<Command> optionalCommand = CommandFactory.defineCommand(req.getParameter("command"));
                        Command command = optionalCommand.orElse(new EmptyCommand());
                        CommandPair commandPair = command.execute(req);
                        if (commandPair.getDispatchType() == CommandPair.DispatchType.FORWARD) {
                            RequestDispatcher dispatcher;
                            dispatcher = req.getRequestDispatcher(commandPair.getPage());
                            dispatcher.forward(req, resp);
                        } else {
                            String defaultPage = "/index.jsp";
                            if (commandPair.getPage().isEmpty()) {
                                resp.sendRedirect(defaultPage);
                            }
                            String page = commandPair.getPage();
                            if (!defaultPage.equals(page)) {
                                req.getSession().setAttribute("isUpdated", true);
                            }
                            req.getSession().setAttribute("pagePath", page);
                            resp.sendRedirect(page);
                        }
                    } else {
                        req.getSession().setAttribute("isUpdated", false);
                        String page = (String) req.getSession().getAttribute("pagePath");
                        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
                        dispatcher.forward(req, resp);
                    }
                }
                else{
                    resp.sendRedirect(pathPageDefault);
                }
            }else{
                resp.sendRedirect(pathPageDefault);
            }
        } catch (CommandException | IOException | ServletException exc) {
            req.setAttribute("error", exc.getMessage());
            System.out.println(exc.getMessage());
            try {
                resp.sendRedirect(pathPageDefault);
            } catch (IOException exception) {
                this.destroy();
            }
        }
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        for (int i = 0; i < connectionPool.poolSize(); i++) {
            try {
                connectionPool.closeConnection(connectionPool.getConnection());
            } catch (ConnectionException ignored) {
            }
        }
    }
}
