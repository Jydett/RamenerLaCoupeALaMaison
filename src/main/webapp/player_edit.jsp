<%@ page import="fr.polytech.rlcalm.beans.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Player</title>
        <link rel="stylesheet" href="css/flags.css">
        <link rel="stylesheet" href="css/customCombo.css">
    </head>
    <body>
    <jsp:include page="login.jsp"/>
    <c:set var="hasError" value="${not empty requestScope.error}"/>
    <c:set var="edition" value="${not empty paramValues['id']}"/>
    <c:set var="userConnect" value="${sessionScope.connected != null}"/>
    <c:if test="${not hasError && edition}">
        <jsp:useBean id="player" type="fr.polytech.rlcalm.beans.Player" scope="request"/>
    </c:if>
    <c:choose>
        <c:when test="${edition}">
            <h2>Edition d'un joueur :<br><a style="font-size: small;" href="players?id=${player.club.id}">Retour</a></h2>
        </c:when>
        <c:otherwise>
            <h2>Creation d'un joueur :<br><a style="font-size: small;" href="players">Retour</a></h2>
        </c:otherwise>
    </c:choose>
    <c:if test="${hasError}">
        <c:set scope="request" var="edition" value="${requestScope.match ne null}"/>
        <div class="error">${requestScope.error}</div>
    </c:if>
    <form action="matchEdit" method="post">
        <input type="hidden" name="id" value="${player.id}"/>
        <div><label for="name">Nom : </label><input id="name" name="name" value="${player.name}" required/></div>
        <div><label for="media-rating">Score des média :</label><input type="number" min="0" max="20" id="media-rating" name="mediaRating" value="${player.mediaRating}"/></div>
        <jsp:useBean id="clubs" scope="request" type="java.util.List"/>
        <div>Club : <span class="custom-select-wrapper">
            <div class="custom-select">
                <input type="hidden" name="clubId" class="form-input" value="">
                <div class="custom-select__trigger"><span>Choisir une équipe</span>
                    <div class="arrow"></div>
                </div>
                <div class="custom-options">
                    <c:forEach items="${clubs}" var="club">
                        <span class="custom-option <c:if test="${club.id == (hasError ? param.clubId : player.club.id)}">selected</c:if>" data-value="${club.id}">${club.country.icon}${club.name}</span>
                    </c:forEach>
                </div>
            </div>
        </span></div>
        <div><label for="role">Role : </label><select id="role" name="role">
            <c:set var="allRoles" value="<%= Role.values() %>"/>
            <c:forEach items="${allRoles}" var="value">
                <option value="${value}">${value.role}</option>
            </c:forEach>
        </select></div>
        <br>
        <c:if test="${userConnect}">
            <span>
                <input type="submit" formaction="playerEdit?action=createOrUpdate" value="Enregistrer les changements"/>
                <c:if test="${edition}">
                    <input type="submit" formaction="playerEdit?action=delete" value="Supprimer">
                </c:if>
            </span>
        </c:if>
    </form>
    <script type="text/javascript" src="js/customCombo.js"></script>
    </body>
</html>
