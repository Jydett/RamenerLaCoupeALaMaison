<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <title>Matches</title>
        <link rel="stylesheet" href="flags.css">
        <link rel="stylesheet" href="customCombo.css">
    </head>
    <body>
        <jsp:useBean id="clubs" scope="request" type="java.util.List"/>
        <c:set var="edition" value="${not empty paramValues['id']}"/>
        <c:set var="hasError" value="${not empty requestScope.error}"/>

        <c:if test="${not hasError && edition}">
            <jsp:useBean id="match" scope="request" type="fr.polytech.rlcalm.beans.Match"/>
        </c:if>

        <c:choose>
            <c:when test="${edition}">
                <h2>Edition d'un match :</h2>
            </c:when>
            <c:otherwise>
                <h2>Creation d'un nouveau match :</h2>
            </c:otherwise>
        </c:choose>
        <c:if test="${hasError}">
            <c:set scope="request" var="edition" value="${requestScope.match ne null}"/>
            <div class="error">${requestScope.error}</div>
        </c:if>
        <form action="matchEdit" method="post">
            <c:if test="${edition}">
                <input type="hidden" name="id" value="<c:choose><c:when test="${hasError}">${param.id}</c:when><c:when test="${edition}">${match.id}</c:when></c:choose>">
            </c:if>
            <div><label for="stadium">Stade: </label><input type="text" name="stadium" id="stadium" value="<c:choose><c:when test="${hasError}">${param.stadium}</c:when><c:when test="${edition}">${match.stadium}</c:when></c:choose>" required/></div>
            <div><label for="city">Ville: </label><input type="text" name="city" id="city" value="<c:choose><c:when test="${hasError}">${param.city}</c:when><c:when test="${edition}">${match.city}</c:when></c:choose>" required/></div><!-- TODO ajouter les not empty -->
            <div>
                <label for="date">Date: </label><input type="date" id="date" name="date" value="<c:choose><c:when test="${hasError}">${param.date}</c:when><c:when test="${edition}">${match.date}</c:when></c:choose>">
                à
                <input type="time" name="date-h" value="<c:choose><c:when test="${hasError}">${param['date-h']}</c:when><c:when test="${edition}">${match.dateh}</c:when></c:choose>">
            </div>
            <div>Clubs & score:
                <span class="custom-select-wrapper">
                    <div class="custom-select">
                        <input type="hidden" name="player1" class="form-input" value="">
                        <div class="custom-select__trigger"><span>Select a team</span>
                            <div class="arrow"></div>
                        </div>
                        <div class="custom-options">
                            <c:forEach items="${clubs}" var="club">
                                <span class="custom-option <c:if test="${club.id == (hasError ? param.player1 : match.player1.id)}">selected</c:if>" data-value="${club.id}">${club.country.icon}${club.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                </span>
                <span id="score">
                    <c:choose>
                        <c:when test="${(hasError && (empty param.score1 || empty param.score2)) || (! hasError && (not edition || match.result == null))}">
                            <button type="button" onclick="addScore()">Ajouter un résultat</button>
                        </c:when>
                        <c:otherwise>
                            <input type="number" class="tiny" name="score1" value="<c:choose><c:when test="${hasError}">${param.score1}</c:when><c:otherwise>${match.result.score1}</c:otherwise></c:choose>" min="0"> VS <input class="tiny" type="number" name="score2" value="<c:choose><c:when test="${hasError}">${param.score2}</c:when><c:otherwise>${match.result.score2}</c:otherwise></c:choose>" min="0">
                            <button type='button' onclick='removeScore()'>Enlever le résultat</button>
                        </c:otherwise>
                    </c:choose>
                </span>
                <div class="custom-select-wrapper">
                    <div class="custom-select">
                        <input type="hidden" name="player2" class="form-input" value="">
                        <div class="custom-select__trigger"><span>Select a team</span>
                            <div class="arrow"></div>
                        </div>
                        <div class="custom-options">
                            <c:forEach items="${clubs}" var="club">
                                <span class="custom-option <c:if test="${club.id == (hasError ? param.player2 : match.player2.id)}">selected</c:if>" data-value="${club.id}">${club.country.icon}${club.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
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

<%--                <jsp:include page="debug/debugger.jsp"/>--%>
        <script>
            function addScore() {
                const node = document.getElementById("score");
                while (node.firstChild) node.removeChild(node.firstChild);
                node.innerHTML = "<input type=\"number\" class=\"tiny\" name=\"score1\" value=\"0\" min=\"0\"> VS <input class=\"tiny\" type=\"number\" name=\"score2\" value=\"0\" min=\"0\"> <button type='button' onclick='removeScore()'>Enlever le résultat</button>";
            }
            function removeScore() {
                const node = document.getElementById("score");
                while (node.firstChild) node.removeChild(node.firstChild);
                node.innerHTML = "<button type=\"button\" onclick=\"addScore()\">Ajouter un résultat</button>";
            }
        </script>
        <script type="text/javascript" src="customCombo.js"></script>

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
        .error {
            color: #a94442;
            background-color: #f2dede;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #ebccd1;
            padding-left: 25px;
        }
        .error::before, .error::after {
            content: "";
            position: absolute;
            height: 15px;
            width: 3px;
        }
        .error::before {
            left: 20px;
            transform: rotate(-45deg);
            background-color: #a94442;
        }
        .error::after {
            left: 20px;
            transform: rotate(45deg);
            background-color: #a94442;
        }
    </style>
</html>
