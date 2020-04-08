<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Clubs</title>
</head>
<body>
<c:choose>
    <c:when test="${requestScope.club != null}">
        <jsp:useBean id="club" type="fr.polytech.rlcalm.beans.Club" scope="request"/>
        <div>Id: ${club.id}</div>
        <div>Name: ${club.name}</div>
        <div>Country: ${club.country}</div>
    </c:when>
    <c:otherwise>
        Ce joueur n'existe pas !
    </c:otherwise>
</c:choose>
</body>
</html>