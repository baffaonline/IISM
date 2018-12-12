<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>Ведомость</title>
</head>
<body>
<div id="page-body">
    <c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
        <c:param name="user" value="${user}"/>
    </c:import>
    <div class="indexContent">
        <div class="container">
            <div class="filmPanel">
                <c:choose>
                    <c:when test="${user.type.typeName.equals('guest')}">
                        <div>
                        <h1>Войдите для того, чтобы попасть в ведомость</h1>
                        </div>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/MainController?command=prepare_login">Войти</a>
                       </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                        <h1>Приветствуем, ${role.name} ${role.fathername}</h1>
                        </div>
                        <div>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/MainController?command=subjects">Войти</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div>
        <p>${error}</p>
    </div>
</div>
<c:import url="${pageContext.request.contextPath}/jsp/parts/footer.jsp"/>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>