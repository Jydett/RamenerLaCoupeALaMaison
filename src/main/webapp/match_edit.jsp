<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <title>Matches</title>
        <link rel="stylesheet" href="flags.css">
    </head>
    <body>
        <jsp:useBean id="clubs" scope="request" type="java.util.List"/>
        <c:set scope="request" var="edition" value="${requestScope.match ne null}"/>
        <c:if test="${edition}">
            <jsp:useBean id="match" scope="request" type="fr.polytech.rlcalm.beans.Match"/>
        </c:if>
        <c:choose>
            <c:when test="${edition}">
                <h2>Edition d'un match :</h2>
            </c:when>
            <c:otherwise>
                <h2>Creation d'un nouveau match</h2>
            </c:otherwise>
        </c:choose>
        <form action="matchEdit" method="post">
            <c:if test="${edition}">
                <input type="hidden" name="id" value="${match.id}">
            </c:if>
            <div><label for="stadium">Stade: </label><input type="text" name="stadium" id="stadium" <c:if test="${edition}">value="${match.stadium}"</c:if> required/></div>
            <div><label for="city">Ville: </label><input type="text" name="city" id="city" <c:if test="${edition}">value="${match.city}"</c:if> required/></div>
            <div><label for="date">Date: </label><input type="date" id="date" name="date" <c:if test="${edition}">value='${match.date}'</c:if>> à <input type="time" name="date-h" <c:if test="${edition}">value="${match.dateh}"</c:if>></div>
            <div>Clubs & score: </div>
            <select name="player1">
                <c:forEach items="${clubs}" var="club">
                    <option value="${club.id}" <c:if test="${club.id == match.player1.id}">selected</c:if>>${club.name}</option>
                </c:forEach>
            </select>
            <span id="score">
                <c:choose>
                    <c:when test="${not edition || match.result == null}">
                        <button type="button" onclick="addScore()">Ajouter un résultat</button>
                    </c:when>
                    <c:otherwise>
                        <input type="number" class="tiny" name="score1" value="${match.result.score1}" min="0"> VS <input class="tiny" type="number" name="score2" value="${match.result.score2}" min="0">
                        <button type='button' onclick='removeScore()'>Enlever le score</button>
                    </c:otherwise>
                </c:choose>
            </span>
            <select name="player2">
                <c:forEach items="${clubs}" var="club">
                    <option value="${club.id}" <c:if test="${club.id == match.player2.id}">selected</c:if>>${club.name}</option>
                </c:forEach>
            </select>
            <div>
                <label for="tournament">Tournois: </label>
                <!-- TODO comment on fait pour le tournois ?-->
                <c:choose>
                    <c:when test="${match.tournament == null}">
                        <input id="tournament" name="tournamentId" type="text" value="" disabled>
                    </c:when>
                    <c:otherwise>
                        <input id="tournament" name="tournamentId" type="text" value="${match.tournament.id}" disabled>
                    </c:otherwise>
                </c:choose>
            </div>
            <span>
                <input type="submit" formaction="matchEdit?action=createOrUpdate" value="Enregistrer les changement"/>
                <c:if test="${edition}">
                    <!-- TODO bonne facon de faire ? -->
                    <input type="submit" formaction="matchEdit?action=delete" value="Supprimer">
                </c:if>
            </span>
        </form>
        <a href="matches">Retour</a>
        <br><br>
        <script>
            function addScore() {
                const node = document.getElementById("score");
                while (node.firstChild) node.removeChild(node.firstChild);
                node.innerHTML = "<input type=\"number\" class=\"tiny\" name=\"score1\" value=\"0\" min=\"0\"> VS <input class=\"tiny\" type=\"number\" name=\"score2\" value=\"0\" min=\"0\"><button type='button' onclick='removeScore()'>Enlever le score</button>";
            }
            function removeScore() {
                const node = document.getElementById("score");
                while (node.firstChild) node.removeChild(node.firstChild);
                node.innerHTML = "<button type=\"button\" onclick=\"addScore()\">Ajouter un résultat</button>";
            }
        </script>
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
        form {
            margin-bottom: 0;
        }
    </style>
</html>
