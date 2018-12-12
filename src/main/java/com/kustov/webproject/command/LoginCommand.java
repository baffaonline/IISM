package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.AdministrationReceiver;
import com.kustov.webproject.logic.StudentReceiver;
import com.kustov.webproject.logic.TeacherReceiver;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.validator.LoginCommandValidator;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class LoginCommand.
 */

public class LoginCommand implements Command {

    /**
     * The receiver.
     */
    private UserReceiver receiver;

    /**
     * The Constant PARAM_LOGIN.
     */
    private final static String PARAM_LOGIN = "login";

    /**
     * The Constant PARAM_PASSWORD.
     */
    private final static String PARAM_PASSWORD = "password";

    /**
     * Instantiates a new login command.
     *
     * @param receiver the receiver
     */
    LoginCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        String pageAuthorization = "/jsp/user/authorization.jsp";
        String login = request.getParameter(PARAM_LOGIN);
        User thisUser = (User)request.getSession().getAttribute("user");
        if (login == null || !PageConstant.GUEST_STRING.equals(thisUser.getType().getTypeName())){
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    "/index.jsp");
        }
        String password = request.getParameter(PARAM_PASSWORD);
        if (LoginCommandValidator.checkLoginAndPassword(login, password)) {
            try {
                User user = receiver.checkUser(login, password);
                if (user != null) {
                    request.getSession(true).setAttribute("user", user);
                    switch (user.getType()){
                        case STUDENT:
                            StudentReceiver studentReceiver = new StudentReceiver();
                            request.getSession().setAttribute("role", studentReceiver.findStudentByUser(user));
                            break;
                        case TEACHER:
                            TeacherReceiver teacherReceiver = new TeacherReceiver();
                            request.getSession().setAttribute("role", teacherReceiver.findTeacherByUser(user));
                            break;
                        case ADMINISTRATION:
                            AdministrationReceiver administrationReceiver = new AdministrationReceiver();
                            request.getSession().setAttribute("role", administrationReceiver.findAdministrationByUser(user));
                            break;
                    }
                    page = (String) request.getSession().getAttribute("page");
                    request.getSession().removeAttribute("page");
                    return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
                } else {
                    request.setAttribute("errorInLoginOrPasswordMessage", "Неправильный логин или пароль");
                    page = pageAuthorization;
                }
            } catch (ServiceException exc) {
                throw new CommandException(exc);
            }
        } else {
            request.setAttribute("errorInLoginOrPasswordMessage", "Логин содержит только буквы, цифры и _");
            page = pageAuthorization;
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
