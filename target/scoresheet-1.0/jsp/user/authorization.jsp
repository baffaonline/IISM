<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Вход</title>
</head>
<body>
<c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
    <c:param name="user" value="${user}"/>
</c:import>
<div class="login-container container">
<h3>Введите свой логин</h3>
<form class="loginForm" action="${pageContext.request.contextPath}/MainController" method="post">
    <input type="hidden" name="command" value="login"/>
    <div class="form-group">
        <label for="exampleInputLogin">Логин</label>
        <input type="text" name="login" class="form-control" id="exampleInputLogin" aria-describedby="loginHelp"
               placeholder="Логин" pattern="[а-яА-Яa-ZA-Z.\d]+" required>
    </div>
    <div class="form-group">
        <label for="exampleInputPassword">Пароль</label>
        <input type="password" name="password" class="form-control" id="exampleInputPassword"
               placeholder="Пароль" pattern="[а-яА-Яa-ZA-Z\d]+" required>
    </div>
    <div>
        <p id="errorParagraph">${errorInLoginOrPasswordMessage}</p>
    </div>
    <button type="submit" class="btn btn-primary">Войти</button>
</form>
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
