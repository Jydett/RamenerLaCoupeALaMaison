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
        <h2>Détail du joueur '${player.name}':<br><a style="font-size: small;" href="clubs?id=${player.club.id}">Retour</a></h2>
        <div>Id: ${player.id}</div>
        <div>Name: ${player.name}</div>
        <div>Average score: ${player.mediaRating}</div>
        <div>Club: <a href="clubs?id=${player.club.id}">${player.club.name}</a></div>
        <div>Role: ${player.role}</div>
        <button onclick="window.location='playerEdit?id=${player.id}';">Modifier ce joueur</button>
        <button onclick="window.location='playerEdit';">Creer un Joueur</button>
    </c:when>
    <c:otherwise>
        <!-- TODO retour -->
        <h2>Détail du joueur:<br></h2>
        Ce joueur n'existe pas !
    </c:otherwise>
</c:choose>
</body>
</html>
