<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Добавление отметки</title>
</head>
<body>
<c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
    <c:param name="user" value="${user}"/>
</c:import>
<div class="container">
    <div class="formDiv">
        <p class="errorDiv">${error}</p>
        <h2>Добавить отметку</h2>
        <form action="${pageContext.request.contextPath}/MainController" id="regForm" method="post" role="form">
            <input type="hidden" name="command" value="add_mark"/>
            <div class="form-group">
                <label for="student" class="control-label">Cтудент</label>
                <div>
                    <select name="student" id="student" class="form-control custom-select">
                        <c:forEach var="elem" items="${students}">
                            <option value="${elem.id}">${elem.surname} ${elem.name} ${elem.fathername}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="category" class="control-label">Категория отметки</label>
                <div>
                    <select name="category" id="category" class="form-control custom-select">
                        <c:forEach var="elem" items="${categories}">
                            <option value="${elem.id}">${elem.category}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="mark" class="control-label">Отметка</label>
                <div>
                    <input type="text" name="mark" class="form-control" id="mark"
                           pattern="[\d]{1,2}|[нН+-]"
                           placeholder="Введите отметку" required>
                </div>
            </div>
            <div class="form-group">
                <label for="date" class="control-label">Дата выставления</label>
                <div>
                    <select name="date" id="date" class="form-control custom-select">
                        <c:forEach var="date" items="${dates}">
                            <option value="${date}">${date.dayOfMonth}.${date.month.value}.${date.year}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button type="submit" class="align-content-center btn btn-primary">
                Добавить
            </button>
        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
