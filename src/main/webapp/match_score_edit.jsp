<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <title>Matches</title>
        <link rel="stylesheet" href="flags.css">
        <link rel="stylesheet" href="customCombo.css">
        <link rel="stylesheet" href="error.css">
    </head>
    <body>
        <jsp:include page="login.jsp"/>

        <jsp:useBean id="match" scope="request" type="fr.polytech.rlcalm.beans.Match"/>
        <jsp:useBean id="participations1" scope="request" type="java.util.List"/>
        <jsp:useBean id="participations2" scope="request" type="java.util.List"/>
        <c:set var="hasError" value="${not empty requestScope.error}"/>
        <c:set var="userConnect" value="${sessionScope.connected != null}"/><!-- TODO si pas connecté redirection -->

<%--        <c:if test="${not hasError}">--%>
<%--            <jsp:useBean id="match" scope="request" type="fr.polytech.rlcalm.beans.Match"/>--%>
<%--        </c:if>--%>
        <h2>Edition des score d'un match :<br><a style="font-size: small;" href="matches">Retour</a></h2>
        <c:if test="${hasError}">
            <div class="error">${requestScope.error}</div>
        </c:if>
        <div class="score-display">
            <span id="score-1">0</span> - <span id="score-2">0</span>
        </div>
        <form action="matchScoreEdit" method="post">
            <input type="hidden" name="matchId" value="${match.id}">
            <!-- Tous les participations des joueurs 1 || Toutes les participations des joueurs 2 -->
            <div class="scores">
                <div class="score-column">
                    <h4>Participation de l'équipe '${match.player1.name}' : </h4>
                    <c:if test="${empty participations1}">
                        <div>Il n'y a pas de joueur dans cette équipe !</div>
                    </c:if>
                    <c:forEach items="${participations1}" var="participation">
                        <div>${participation.player.name}: <input class="but1" onchange="onScoreChange1()" name="par${participation.player.id}" type="number" min="0" value="${participation.goals}"/> but(s)</div>
                    </c:forEach>
                </div>
                <div class="score-column">
                    <h4>Participation de l'équipe '${match.player2.name}' : </h4>
                    <c:if test="${empty participations1}">
                        <div>Il n'y a pas de joueur dans cette équipe !</div>
                    </c:if>
                    <c:forEach items="${participations2}" var="participation">
                        <div>${participation.player.name}: <input class="but2" onchange="onScoreChange2()" name="par${participation.player.id}" type="number" min="0" value="${participation.goals}"/> but(s)</div>
                    </c:forEach>
                </div>
            </div>
            <c:if test="${userConnect}">
                <span>
                    <input type="submit" formaction="matchScoreEdit?action=update" value="Enregistrer les changements"/>
                    <input type="submit" formaction="matchScoreEdit?action=delete" value="Supprimer le score du match">
                </span>
            </c:if>
        </form>
    </body>
    <script>
        onScoreChange1(); onScoreChange2();
        function onScoreChange1() {
            var total = 0;
            for (let but of document.getElementsByClassName("but1")) {
                total = total + parseInt(but.value);
            }
            document.getElementById("score-1").innerText = total;
        }
        function onScoreChange2() {
            var total = 0;
            for (let but of document.getElementsByClassName("but2")) {
                total = total + parseInt(but.value);
            }
            document.getElementById("score-2").innerText = total;
        }
    </script>
    <style>
        form {
            margin-bottom: 0;
        }
        .scores {
            display: flex;
        }
        .score-column{
            flex: auto;
            padding: 10px;
        }
        .score-column > h4 {
            margin-top: 0;
        }
        .score-display {
            font-size: 30px;
            text-align: center;
        }
    </style>
</html>
