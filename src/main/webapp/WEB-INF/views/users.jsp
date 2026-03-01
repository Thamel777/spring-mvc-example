<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>User CRUD</title>
</head>
<body>
    <h2>User Management</h2>
    <p><a href="${pageContext.request.contextPath}/">Back to Home</a></p>

    <%-- CREATE / EDIT form --%>
    <c:choose>
        <c:when test="${not empty user}">
            <%-- Edit mode --%>
            <h3>Edit User</h3>
            <form action="${pageContext.request.contextPath}/users/update/${user.id}" method="post">
                Name:  <input type="text"  name="name"  value="${user.name}" /><br/>
                Email: <input type="email" name="email" value="${user.email}" /><br/>
                <input type="submit" value="Update" />
                <a href="${pageContext.request.contextPath}/users">Cancel</a>
            </form>
        </c:when>
        <c:otherwise>
            <%-- Create mode --%>
            <h3>Add New User</h3>
            <form action="${pageContext.request.contextPath}/users/create" method="post">
                Name:  <input type="text"  name="name"  /><br/>
                Email: <input type="email" name="email" /><br/>
                <input type="submit" value="Add User" />
            </form>
        </c:otherwise>
    </c:choose>

    <hr/>

    <%-- READ - users table --%>
    <h3>All Users</h3>
    <table border="1" cellpadding="6">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/edit/${u.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/users/delete/${u.id}"
                       onclick="return confirm('Delete ${u.name}?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
