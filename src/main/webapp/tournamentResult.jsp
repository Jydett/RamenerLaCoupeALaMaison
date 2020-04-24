<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tournois</title>
    <link rel="stylesheet" href="error.css">
    <link rel="stylesheet" href="medals.css">
</head>
    <body>
        <jsp:include page="login.jsp"/>

        <h2>Liste des resultats des tournois : <br><span style="font-size: small; text-decoration: none"><a href="index.jsp">Retour</a></span></h2>

        <c:if test="${not empty requestScope.error}">
            <div class="error">${requestScope.error}</div>
        </c:if>

        <form method="get" action="results">
            <label for="year">Année : </label><input type="number" name="year" id="year" value="${param.year}" min="0" max="9999">
            <input type="submit" value="Rechercher">
            <c:if test="${sessionScope.connected != null}">
                <input type="submit" formmethod="post" formaction="results?action=update" value="Modifier le resultat">
                <input type="submit" formmethod="post" formaction="results?action=delete" value="Supprimer le resultat">
                <input type="submit" formmethod="post" formaction="results?action=create" value="Ajouter le resultat">
            </c:if>
        </form>

        <c:if test="${empty requestScope.results}">
            <div>Pas de résultats !</div>
        </c:if>
        <c:forEach items="${requestScope.results}" var="tour">
            <div><span>${tour.year} -- <a href="clubs?id=${tour.club.id}" title="Voir cette équipe">${tour.club.name}</a> : ${tour.placement}</span>
                <c:if test="${tour.placement <= 3}">
                    <img src="medal-solid.svg" class="cup_filter_${tour.placement} cup"/>
                </c:if></div>
        </c:forEach>
<%--        <c:if test="${sessionScope.connected != null}">--%>
<%--            <br><br><button onclick="window.location='playerEdit';">Creer un Joueur</button>--%>
<%--        </c:if>--%>
    </body>
</html>
