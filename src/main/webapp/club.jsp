<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Club</title>
        <link rel="stylesheet" href="flags.css">
    </head>
    <body>
        <jsp:include page="login.jsp"/>
        <c:set var="userConnect" value="${sessionScope.connected != null}"/>

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
                                <li><a title="Détail de ce joueur" href="players?id=${player.id}">${player.name}</a> ${player.role}</li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul><!-- TODO -->
                <h3 style="color: red">Nombre de victoire</h3>
                <h3 style="color: red">Nombre de nombre de participations</h3>
                <h3 style="color: red">Classement dans les éditions précédente</h3>
            </c:when>
            <c:otherwise>
                <h2>Détail du club</h2>
                Ce club n'existe pas !
            </c:otherwise>
        </c:choose>

        <c:if test="${userConnect}">
            <button onclick="window.location='clubEdit?id=${club.id}';">Modifier ce club</button>
            <form method="post" style="display: inline-block;">
                <input type="submit" formaction="clubEdit?action=delete&id=${club.id}" value="Supprimer ce club">
            </form>
        </c:if>

    </body>
</html>