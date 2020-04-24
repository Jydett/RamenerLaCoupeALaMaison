<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Matches</title>
        <link rel="stylesheet" href="flags.css">
    </head>
    <body>
    <jsp:include page="login.jsp"/>

    <c:set var="userConnect" value="${sessionScope.connected != null}"/>

    <h2>Liste des matchs :<br><a style="font-size: small;" href="index.jsp">Retour</a></h2>
    <c:if test="${not empty requestScope.error}">
        <c:set scope="request" var="edition" value="${requestScope.match ne null}"/>
        <div class="error">${requestScope.error}</div>
    </c:if>
    <c:choose>
        <c:when test="${requestScope.matchs != null && not empty requestScope.matchs}">
            <jsp:useBean id="matchs" scope="request" type="java.util.List"/>
            <c:forEach var="match" items="${matchs}">
                <div>Match :</div>
                <div>&nbsp;&nbsp;<c:choose>
                    <c:when test="${match.formattedDate == null}">Pas encore plannifié</c:when>
                    <c:otherwise>${match.formattedDate}: </c:otherwise>
                </c:choose></div>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;${match.stringView}<br></div>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;<i>${match.stadium}, ${match.city}</i></div>
                <c:if test="${userConnect}">
                    <button onclick="window.location='matchEdit?id=${match.id}'">Editer</button>
                    <c:if test="${match.instant != null}">
                        <button onclick="window.location='matchScoreEdit?id=${match.id}'">Modifier le score</button>
                    </c:if>
                </c:if>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h2>Pas de matchs de prévu !</h2>
        </c:otherwise>
    </c:choose>
    <br><br>
    <c:if test="${userConnect}">
        <button onclick="window.location='matchEdit';">Creer un nouveau match</button>
    </c:if>
</body>
<style>
    .winner {
        background-color: rgba(106, 135, 89, 0.5);
    }
    .looser {
        background-color: rgba(255, 0, 0, 0.5);
    }
    .tie {
        background-color: rgba(170, 170, 170, 0.5);
    }
</style>
</html>
