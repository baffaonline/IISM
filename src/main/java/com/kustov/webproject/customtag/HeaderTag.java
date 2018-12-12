package com.kustov.webproject.customtag;

import com.kustov.webproject.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * The Class HeaderTag.
 */
public class HeaderTag extends TagSupport {

    /**
     * The user.
     */
    private User user;

    /**
     * The context path.
     */
    private String contextPath;

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the context path.
     *
     * @param contextPath the new context path
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * Do start tag.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            switch (user.getType().getTypeName()) {
                case "teacher":
                case "administration":
                case "student": {
                    JspWriter jspWriter = pageContext.getOut();
                    jspWriter.write("<li class=\"nav-item\">");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            "/MainController?command=user_information&page=user\">" +
                            user.getUsername() + "</a>");
                    jspWriter.write("</li>");
                    jspWriter.write("<li class=\"nav-item\">");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            "/MainController?command=logout\">" +
                             "Выйти</a>");
                    jspWriter.write("</li>");
                    break;
                }
                case "guest": {
                    JspWriter jspWriter = pageContext.getOut();
                    jspWriter.write("<li class=\"nav-item\">");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            "/MainController" + "?command=prepare_login" + "\">" +
                             "Войти</a>");
                    jspWriter.write("</li>");
                    break;
                }
            }
        } catch (IOException exc) {
            throw new JspException(exc);
        }
        return SKIP_BODY;
    }
}
