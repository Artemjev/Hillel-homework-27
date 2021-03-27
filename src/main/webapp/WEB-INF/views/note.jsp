<%@ page import="com.hillel.artemjev.notebook.entities.Note" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>note</title>
</head>

<body>
<h3>Notes: ${note.name}</h3>
<h5>Сontent:</h5>
<p>
    <i>${note.description}</i>
</p>
<br/>
created: ${note.dateTime}
<br/>
<form action="<c:url value="/delete"/>" method="post">
    <input type="hidden" value="${note.id}" name="id">
    <input type="submit" value="delete">
</form>
</body>
</html>
