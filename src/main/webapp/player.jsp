<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Player</title>
</head>
<body>
    <c:choose>
        <c:when test="${requestScope.player != null}">
            <jsp:useBean id="player" type="fr.polytech.rlcalm.beans.Player" scope="request"/>
            <div>Id: ${player.id}</div>
            <div>Name: ${player.name}</div>
            <div>Average score: ${player.averageScore}</div>
            <div>Club: ${player.club}</div>
            <div>Role: ${player.role}</div>
        </c:when>
        <c:otherwise>
            Ce joueur n'existe pas !
        </c:otherwise>
    </c:choose>
</body>
</html>
