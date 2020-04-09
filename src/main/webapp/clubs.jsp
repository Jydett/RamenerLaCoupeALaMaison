<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clubs</title>
    <link rel="stylesheet" href="flags.css">
</head>
<body>
    <c:forEach items="${requestScope.clubs}" var="club">
        <div>${club.country.icon}${club.name}</div>
    </c:forEach>
</body>
</html>
