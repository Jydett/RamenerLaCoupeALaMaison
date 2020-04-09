<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Club</title>
    <link rel="stylesheet" href="flags.css">
</head>
<body>
<c:choose>
    <c:when test="${requestScope.club != null}">
        <h2>Détail du club '${club.name}':<br><a style="font-size: small;" href="clubs">Retour</a></h2>
        <jsp:useBean id="club" type="fr.polytech.rlcalm.beans.Club" scope="request"/>
        <div>Id: ${club.id}</div>
        <div>Name: ${club.name}</div>
        <div>Country: ${club.country} ${club.country.icon}</div>
        <c:forEach items="${club.players}" var="player">
            <div>${player.name} ${player.role}</div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <h2>Détail du club</h2>
        Ce club n'existe pas !
    </c:otherwise>
</c:choose>
</body>
</html>