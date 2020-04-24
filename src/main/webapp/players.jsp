<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Clubs</title>
    <link rel="stylesheet" href="css/flags.css">
</head>
<body>
<jsp:include page="login.jsp"/>

    <h2>Liste des joueurs: <br><span style="font-size: small; text-decoration: none">(Cliquer pour en savoir plus)</span><br><a style="font-size: small;" href="index.jsp">Retour</a></h2>
    <c:forEach items="${requestScope.players}" var="player">
        <div>${player.club.country.icon}<a href="players?id=${player.id}" title="${player.name}">${player.name}</a></div>
    </c:forEach>
    <c:if test="${sessionScope.connected != null}">
        <br><br><button onclick="window.location='playerEdit';">Créer un Joueur</button>
    </c:if>
</body>
</html>
