<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tournois</title>
    <link rel="stylesheet" href="error.css">
</head>
    <body>
        <jsp:include page="login.jsp"/>

        <h2>Liste des resultats des tournois : <br><span style="font-size: small; text-decoration: none"><a href="index.jsp">Retour</a></span></h2>

        <c:if test="${not empty requestScope.error}">
            <div class="error">${requestScope.error}</div>
        </c:if>

        <form method="get" action="results">
            <label for="year">Année : </label><input type="number" name="year" id="year" value="${param.year}">
            <input type="submit" value="Rechercher">
            <input type="submit" formmethod="post" formaction="results?action=update" value="Modifier le resultat">
            <input type="submit" formmethod="post" formaction="results?action=delete" value="Supprimer le resultat">
        </form>

        <c:forEach items="${requestScope.results}" var="tour">
            <div>${tour.year} -- ${tour.club.name} : ${tour.placement}</div>
        </c:forEach>
<%--        <c:if test="${sessionScope.connected != null}">--%>
<%--            <br><br><button onclick="window.location='playerEdit';">Creer un Joueur</button>--%>
<%--        </c:if>--%>
    </body>
</html>
