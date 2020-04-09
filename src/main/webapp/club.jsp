<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Club</title>
    <link rel="stylesheet" href="flags.css">
</head>
<body>
<jsp:include page="login.jsp"/>

<c:choose>
    <c:when test="${requestScope.club != null}">
        <h2>Détail du club '${club.name}':<br><a style="font-size: small;" href="clubs">Retour</a></h2>
        <jsp:useBean id="club" type="fr.polytech.rlcalm.beans.Club" scope="request"/>
        <div>Nom: ${club.name}</div>
        <div>Pays: ${club.country.name} ${club.country.icon}</div>
        <div>Joueurs: </div>
        <ul>
            <c:forEach items="${club.players}" var="player">
                <li><a title="Détail de ce joueur" href="players?id=${player.id}">${player.name}</a> ${player.role}</li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <h2>Détail du club</h2>
        Ce club n'existe pas !
    </c:otherwise>
</c:choose>
</body>
</html>