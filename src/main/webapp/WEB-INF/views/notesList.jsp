<%@ page import="java.util.List" %>
<%@ page import="com.hillel.artemjev.notebook.entities.Note" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Notebook</title>
</head>
<body>
<h3>Notes to do list</h3>
<br/>
<b>Add note:</b>
<form action="<c:url value="/"/>" method="post">
    Note name  :   <input type="text" name="name"> <br/>
    Description: <input type="text" name="description"> <br/>
    <input type="submit" value="send">
</form>
<br/>
<table border='1'>
    <tr>
        <th> Id</th>
        <th> Note</th>
    </tr>
    <c:forEach var="note" items="${notes}">
    <tr>
        <td>
                <%--<a href="<c:url value="/notes/?id=${note.id}"/>"> -как вариант--%>
            <a href="<c:url value="/notes/${note.id}"/>" target=blank>
                <b>${note.id}</b>
            </a>
        </td>
            <%--<td>${note.id}</td>--%>
        <td>
                <%--<a href="<c:url value="/notes/?id=${note.id}"/>"> -как вариант--%>
            <a href="<c:url value="/notes/${note.id}"/>" target=blank>
                <b>${note.name}</b>
            </a>
        </td>
    </tr>
    </c:forEach>

</body>
</html>
