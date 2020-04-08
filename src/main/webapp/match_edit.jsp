<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" href="flags.css">
</head>
<body>
    <c:choose>
        <c:when test="${requestScope.match != null}">
            <jsp:useBean id="match" scope="request" type="fr.polytech.rlcalm.beans.Match"/>
            <jsp:useBean id="clubs" scope="request" type="java.util.List"/>
            <form action="/matchEdit" method="post">
                <input type="hidden" name="id" value="${match.id}">
                <div><label for="stadium">Stade: </label><input type="text" name="Stadium" id="stadium" value="${match.stadium}"/></div>
                <div><label for="city">Ville: </label><input type="text" name="City" id="city" value="${match.city}"/></div>
                <div><label for="date">Date: </label><input type="date" id="date" name="date" value='${match.date}'><input type="time" name="date-h" value="${match.dateh}"></div>
                <div>Clubs & score: </div>
                <select name="player1">
                    <c:forEach items="${clubs}" var="club">
                        <option value="${club.id}" <c:if test="${club.id == match.player1.id}">selected</c:if>>${club.name}</option>
                    </c:forEach>
                </select>
                <c:choose>
                    <c:when test="${match.result == null}">
                        <input type="number" class="tiny" name="score1" value="0"> VS <input class="tiny" type="number" name="score2" value="0">
                    </c:when>
                    <c:otherwise>
                        <input type="number" class="tiny" name="score1" value="${match.result.score1}"> VS <input class="tiny" type="number" name="score2" value="${match.result.score2}">
                    </c:otherwise>
                </c:choose>
                <select name="player2">
                    <c:forEach items="${clubs}" var="club">
                        <option value="${club.id}" <c:if test="${club.id == match.player2.id}">selected</c:if>>${club.name}</option>
                    </c:forEach>
                </select>
                <div>
                    <!-- TODO comment on fait pour le tournois ?-->
                    <label for="tournament">Tournois: </label><input id="tournament" type="text" value="${match.tournament}" disabled>
                </div>
                <div><input type="submit" value="Enregistrer les changement"/>
                <!-- TODO bonne facon de faire ? -->
                <input type="submit" formaction="/matchEdit?actionDelete=1" value="Supprimer"></div>
            </form>
            <div>Match :</div>
            <div>&nbsp;&nbsp;${match.formattedDate}: </div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;${match.stringView}<br></div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;<i>${match.stadium}, ${match.city}</i></div>
        </c:when>
        <c:otherwise>
            <!-- TODO redirect ? -->
            <h2>Match inconnu</h2>
        </c:otherwise>
    </c:choose>
</body>
<style>
    .winner {
        background-color: rgba(106, 135, 89, 0.5);
    }
    .looser {
        background-color: rgba(255, 0, 0, 0.5);
    }
    .tiny {
        width: 50px;
    }
</style>
</html>
