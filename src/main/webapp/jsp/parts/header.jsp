<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<header>
    <nav id="header-nav" class="navbar navbar-expand-sm navbar-light">
        <div class="container">
            <div class="navigation-bar-container">
                <a class="navbar-brand navigation-bar-item"
                   href="${pageContext.request.contextPath}/index.jsp">Ведомость</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav">
                    <ctg:user-list user="${user}" contextPath="${pageContext.request.contextPath}"/>
                </ul>
            </div>
        </div>
    </nav>
</header>