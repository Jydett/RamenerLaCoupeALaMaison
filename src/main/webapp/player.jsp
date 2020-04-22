<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Player</title>
    <link rel="stylesheet" href="flags.css">
</head>
<body>
<jsp:include page="login.jsp"/>
<c:set var="userConnect" value="${sessionScope.connected != null}"/>
<c:choose>
    <c:when test="${requestScope.player != null}">
        <jsp:useBean id="player" type="fr.polytech.rlcalm.beans.Player" scope="request"/>
        <h2>Détail du joueur '${player.name}':<br><a style="font-size: small;" href="clubs?id=${player.club.id}">Retour</a></h2>
        <div>Name : ${player.name}</div>
        <div>Score des médias :
            <span style="color: goldenrod"><c:forEach var="i" begin="1" end="${player.mediaRating}">★</c:forEach></span><c:forEach var="i" begin="${player.mediaRating + 1}" end="20">★</c:forEach>
             (${player.mediaRating}/20)
        </div>
        <div>Club : <a href="clubs?id=${player.club.id}">${player.club.name}</a></div>
        <div>Role : ${player.role.role}</div>

        <c:if test="${userConnect}">
            <button onclick="window.location='playerEdit?id=${player.id}';">Modifier ce joueur</button>
            <button onclick="window.location='playerEdit';">Creer un nouveau joueur</button>
            <form method="post" style="display: inline-block;">
                <input type="submit" formaction="playerEdit?action=delete&id=${player.id}" value="Supprimer ce joueur">
            </form>
        </c:if>

        <jsp:useBean id="participations" type="java.util.List" scope="request"/>
        <h3>Participations dans les matchs</h3>
        <c:if test="${empty participations}">
            <div>Aucun but marqué !</div>
        </c:if>
        <c:forEach items="${participations}" var="par">
            <div>${par.match.date}) ${par.goals} buts dans le match: &nbsp;${par.match.stringView}
            <c:if test="${userConnect}">&nbsp&nbsp<button onclick="window.location='matchScoreEdit?id=${par.match.id}'">Editer ce match</button></c:if>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <!-- TODO retour -->
        <h2>Détail du joueur:<br></h2>
        Ce joueur n'existe pas !
    </c:otherwise>
</c:choose>
</body>
</html>
