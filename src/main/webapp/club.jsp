<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Club</title>
        <link rel="stylesheet" href="css/flags.css">
        <link rel="stylesheet" href="css/medals.css">
    </head>
    <body>
        <jsp:include page="login.jsp"/>
        <c:set var="userConnect" value="${sessionScope.connected != null}"/>

        <c:if test="${not empty param.error}">
            <br><div class="error">${param.error}</div>
        </c:if>

        <c:choose>
            <c:when test="${requestScope.club != null}">
                <jsp:useBean id="club" type="fr.polytech.rlcalm.beans.Club" scope="request"/>
                <h2>Détail du club '${club.name}':<br><a style="font-size: small;" href="clubs">Retour</a></h2>
                <div>Nom: ${club.name}</div>
                <div>Pays: ${club.country.name} ${club.country.icon}</div>
                <div>Joueurs: </div>
                <ul>
                    <c:choose>
                        <c:when test="${empty club.players}"><li>Aucun joueur !</li></c:when>
                        <c:otherwise>
                            <c:forEach items="${club.players}" var="player">
                                <li><a title="Détail de ce joueur" href="players?id=${player.id}">${player.name}</a> ${player.role.role}</li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <div>Nombre de match joué : ${requestScope.participations}</div>
                <div>Nombre de victoire : ${requestScope.victories}</div>
                <div>Nombre de match plannifié : ${requestScope.plannified}</div>
                <h3>Classement dans les éditions précédentes :</h3>
                <c:if test="${empty requestScope.palmares}">
                    <div>Pas d'historique dans les tournois précédents</div>
                </c:if>
                <c:forEach items="${requestScope.palmares}" var="tour">
                    <div><span><a href="results?year=${tour.year}" title="Voir le classement">${tour.year} -- Classement : ${tour.placement}</a></span>
                        <c:if test="${tour.placement <= 3}">
                            <img src="img/medal-solid.svg" class="cup_filter_${tour.placement} cup"/>
                        </c:if></div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h2>Détail du club</h2>
                Ce club n'existe pas !
            </c:otherwise>
        </c:choose>
        <c:if test="${userConnect}">
            <br>
            <button onclick="window.location='clubEdit?id=${club.id}';">Modifier ce club</button>
            <form method="post" style="display: inline-block;">
                <input type="submit" formaction="clubEdit?action=delete&id=${club.id}" value="Supprimer ce club">
            </form>
        </c:if>

    </body>
</html>