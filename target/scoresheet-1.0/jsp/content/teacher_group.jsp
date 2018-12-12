<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="xsl" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>Предметы</title>
</head>
<body>
<div id="page-body">
    <c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
        <c:param name="user" value="${user}"/>
    </c:import>
    <div class="mainContent">
        <div class="container mainContent_container">
            <div class="content">
                <h1>Выберите курс и группу</h1>
                <hr>
                <c:forEach var="elem" items="${groupCourse}">
                    <div class="ratingPanel">
                        <div class="rating-panel-content">
                            <a href="${pageContext.request.contextPath}/MainController?command=group_subject&group=${elem.group}&course=${elem.course}">
                                    ${elem.group} группа ${elem.course} курс</a>
                        </div>
                    </div>
                </c:forEach>
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
