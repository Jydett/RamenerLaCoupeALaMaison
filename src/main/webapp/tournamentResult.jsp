<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tournois</title>
</head>
    <body>
        <jsp:include page="login.jsp"/>

        <h2>Liste des resultats des tournois : <br><span style="font-size: small; text-decoration: none">(Cliquer pour en savoir plus)</span><br><a style="font-size: small;" href="index.jsp">Retour</a></h2>
        <form method="get">
            <label for="year">Année : </label><input type="number" name="year" id="year">
            <input type="submit" value="Rechercher !">
        </form>

        <c:forEach items="${requestScope.results}" var="tour">
            <div>${tour.year} -- ${tour.club.name} : ${tour.placement}</div>
        </c:forEach>
<%--        <c:if test="${sessionScope.connected != null}">--%>
<%--            <br><br><button onclick="window.location='playerEdit';">Creer un Joueur</button>--%>
<%--        </c:if>--%>
    </body>
</html>
