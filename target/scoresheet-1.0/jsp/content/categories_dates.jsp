<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="xsl" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>Сроки</title>
</head>
<body>
<div id="page-body">
    <c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
        <c:param name="user" value="${user}"/>
    </c:import>
    <div class="mainContent">
        <div class="container mainContent_container">
            <div class="content">
                <h1>Сроки сдачи</h1>
                <hr>
                <c:forEach var="elem" items="${subject.categories}">
                    <c:if test="${elem.date != null}">
                        <div class="ratingPanel">
                            <div class="rating-panel-image">
                                <p>${elem.category}:</p>
                            </div>
                            <hr/>
                            <div class="rating-panel-content">
                                <p>${elem.date.dayOfMonth}.${elem.date.month.value}.${elem.date.year}</p>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
    <div>
        <form class="form-inline" id="optionForm" method="get">
            <a class="btn btn-danger form-btn" href="${pageContext.request.contextPath}/">Выйти</a>
            <a class="btn btn-primary form-btn" href="${pageContext.request.contextPath}/MainController?command=before_add_mark">
                Добавить отметку</a>
            <a class="btn btn-primary form-btn"
               href="${pageContext.request.contextPath}/MainController?command=group_subject&group=${group}&course=${course}">
                Посмотреть посещаемость</a>
            <a class="btn btn-primary form-btn" href="${pageContext.request.contextPath}/MainController?command=marks_categories">
                Посмотреть отметки</a>
            <a class="btn btn-primary form-btn" href="${pageContext.request.contextPath}/MainController?command=before_add_category_or_date">
                Добавить дату или категорию</a>
        </form>
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
