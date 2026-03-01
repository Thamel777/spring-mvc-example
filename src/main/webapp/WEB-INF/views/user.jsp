<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>User</title>
</head>
<body>
    <h2>Hi ${userName}</h2>
    <p><a href="${pageContext.request.contextPath}/">Back to Home</a></p>
    <p><a href="${pageContext.request.contextPath}/users">Manage Users (CRUD)</a></p>
</body>
</html>
