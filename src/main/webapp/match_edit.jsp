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

        <jsp:useBean id="clubs" scope="request" type="java.util.List"/>
        <c:set var="edition" value="${not empty paramValues['id']}"/>
        <c:set var="hasError" value="${not empty requestScope.error}"/>
        <c:set var="userConnect" value="${sessionScope.connected != null}"/>

        <c:if test="${not hasError && edition}">
            <jsp:useBean id="match" scope="request" type="fr.polytech.rlcalm.beans.Match"/>
        </c:if>
        <c:choose>
            <c:when test="${edition}">
                <h2>Edition d'un match :<br><a style="font-size: small;" href="matches">Retour</a></h2>
            </c:when>
            <c:otherwise>
                <h2>Creation d'un nouveau match :<br><a style="font-size: small;" href="matches">Retour</a></h2>
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
            <div><label for="stadium">Stade : </label><input type="text" name="stadium" id="stadium" value="<c:choose><c:when test="${hasError}">${param.stadium}</c:when><c:when test="${edition}">${match.stadium}</c:when></c:choose>" required/></div>
            <div><label for="city">Ville : </label><input type="text" name="city" id="city" value="<c:choose><c:when test="${hasError}">${param.city}</c:when><c:when test="${edition}">${match.city}</c:when></c:choose>" required/></div>
            <div>
                <label for="date">Date : </label><input <c:if test="${match.result != null}">disabled</c:if> type="date" id="date" name="date" value="<c:choose><c:when test="${hasError}">${param.date}</c:when><c:when test="${edition}">${match.date}</c:when></c:choose>">
                à
                <input <c:if test="${match.result != null}">disabled</c:if> type="time" name="date-h" value="<c:choose><c:when test="${hasError}">${param['date-h']}</c:when><c:when test="${edition}">${match.dateh}</c:when></c:choose>">
                <c:if test="${match.result != null}">
                    <a title="Impossible de modifier la date d'un match ayant un score">
                        <svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 512 512" style="enable-background:new 0 0 512 512;" xml:space="preserve" width="16" height="16"><path style="fill:#3B4145;" d="M322.939,62.642l178.737,309.583C508.231,383.578,512,396.74,512,410.791 c0,42.67-34.592,77.264-77.264,77.264H256L194.189,256L256,23.946C284.62,23.946,309.587,39.519,322.939,62.642z"/><path style="fill:#525A61;" d="M189.061,62.642L10.323,372.225C3.769,383.578,0,396.74,0,410.791 c0,42.67,34.592,77.264,77.264,77.264H256V23.946C227.38,23.946,202.413,39.519,189.061,62.642z"/><path style="fill:#FFB751;" d="M474.913,387.678L296.177,78.098c-8.056-13.959-22.849-22.767-38.848-23.22l152.869,402.275h24.539 c25.559,0,46.358-20.798,46.358-46.358C481.095,402.677,478.952,394.683,474.913,387.678z"/><path style="fill:#FFD764;" d="M444.853,387.678c3.492,7.005,5.336,14.999,5.336,23.117c0,25.559-17.935,46.358-39.992,46.358 H77.264c-25.559,0-46.358-20.799-46.358-46.358c0-8.118,2.143-16.112,6.181-23.117l178.736-309.58 c8.283-14.34,23.674-23.251,40.177-23.251c0.443,0,0.886,0.01,1.329,0.031c13.732,0.536,26.414,9.323,33.326,23.22L444.853,387.678z "/><path style="fill:#3B4145;" d="M256,354.131v51.509c14.227,0,25.755-11.528,25.755-25.755 C281.755,365.659,270.227,354.131,256,354.131z"/><path style="fill:#525A61;" d="M256,354.131c2.843,0,5.151,11.528,5.151,25.755c0,14.227-2.308,25.755-5.151,25.755 c-14.227,0-25.755-11.528-25.755-25.755C230.245,365.659,241.773,354.131,256,354.131z"/><path style="fill:#3B4145;" d="M256,132.646V323.23c14.227,0,25.755-11.538,25.755-25.755V158.401 C281.755,144.174,270.227,132.646,256,132.646z"/><path style="fill:#525A61;" d="M256,132.646c2.843,0,5.151,11.528,5.151,25.755v139.074c0,14.216-2.308,25.755-5.151,25.755 c-14.227,0-25.755-11.538-25.755-25.755V158.401C230.245,144.174,241.773,132.646,256,132.646z"/><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg>
                    </a>
                </c:if>
            </div>
            <div>Clubs & score:
                <span class="custom-select-wrapper">
                    <div class="custom-select">
                        <input type="hidden" name="team1" class="form-input" value="">
                        <div class="custom-select__trigger"><span>Choisir une équipe</span>
                            <div class="arrow"></div>
                        </div>
                        <div class="custom-options">
                            <c:forEach items="${clubs}" var="club">
                                <span class="custom-option <c:if test="${club.id == (hasError ? param.team1 : match.team1.id)}">selected</c:if>" data-value="${club.id}">${club.country.icon}${club.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                </span>
                <span id="score">
                    <c:choose>
                        <c:when test="${match.result eq null}">
                            -
                        </c:when>
                        <c:otherwise>
                            ${match.result.score1} - ${match.result.score2}
                        </c:otherwise>
                    </c:choose>
                </span>
                <div class="custom-select-wrapper">
                    <div class="custom-select">
                        <input type="hidden" name="team2" class="form-input" value="">
                        <div class="custom-select__trigger"><span>Choisir une équipe</span>
                            <div class="arrow"></div>
                        </div>
                        <div class="custom-options">
                            <c:forEach items="${clubs}" var="club">
                                <span class="custom-option <c:if test="${club.id == (hasError ? param.team2 : match.team2.id)}">selected</c:if>" data-value="${club.id}">${club.country.icon}${club.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <c:if test="${match.result ne null && !(match.result.score1 == 0 && match.result.score2 == 0)}">
                    <a title="Modifier une équipe enlevera les participations aux buts des joueurs et mettre le score de l'équipe pour ce match à zero">
                        <svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 512 512" style="enable-background:new 0 0 512 512;" xml:space="preserve" width="16" height="16"><path style="fill:#3B4145;" d="M322.939,62.642l178.737,309.583C508.231,383.578,512,396.74,512,410.791 c0,42.67-34.592,77.264-77.264,77.264H256L194.189,256L256,23.946C284.62,23.946,309.587,39.519,322.939,62.642z"/><path style="fill:#525A61;" d="M189.061,62.642L10.323,372.225C3.769,383.578,0,396.74,0,410.791 c0,42.67,34.592,77.264,77.264,77.264H256V23.946C227.38,23.946,202.413,39.519,189.061,62.642z"/><path style="fill:#FFB751;" d="M474.913,387.678L296.177,78.098c-8.056-13.959-22.849-22.767-38.848-23.22l152.869,402.275h24.539 c25.559,0,46.358-20.798,46.358-46.358C481.095,402.677,478.952,394.683,474.913,387.678z"/><path style="fill:#FFD764;" d="M444.853,387.678c3.492,7.005,5.336,14.999,5.336,23.117c0,25.559-17.935,46.358-39.992,46.358 H77.264c-25.559,0-46.358-20.799-46.358-46.358c0-8.118,2.143-16.112,6.181-23.117l178.736-309.58 c8.283-14.34,23.674-23.251,40.177-23.251c0.443,0,0.886,0.01,1.329,0.031c13.732,0.536,26.414,9.323,33.326,23.22L444.853,387.678z "/><path style="fill:#3B4145;" d="M256,354.131v51.509c14.227,0,25.755-11.528,25.755-25.755 C281.755,365.659,270.227,354.131,256,354.131z"/><path style="fill:#525A61;" d="M256,354.131c2.843,0,5.151,11.528,5.151,25.755c0,14.227-2.308,25.755-5.151,25.755 c-14.227,0-25.755-11.528-25.755-25.755C230.245,365.659,241.773,354.131,256,354.131z"/><path style="fill:#3B4145;" d="M256,132.646V323.23c14.227,0,25.755-11.538,25.755-25.755V158.401 C281.755,144.174,270.227,132.646,256,132.646z"/><path style="fill:#525A61;" d="M256,132.646c2.843,0,5.151,11.528,5.151,25.755v139.074c0,14.216-2.308,25.755-5.151,25.755 c-14.227,0-25.755-11.538-25.755-25.755V158.401C230.245,144.174,241.773,132.646,256,132.646z"/><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg>
                    </a>
                </c:if>
            </div>
            <br>
            <c:if test="${userConnect}">
                <span>
                <input type="submit" formaction="matchEdit?action=createOrUpdate" value="Enregistrer les changement"/>
                <c:if test="${edition}">
                    <input type="submit" formaction="matchEdit?action=delete" value="Supprimer">
                </c:if>
            </span>
            </c:if>
        </form>
        <script type="text/javascript" src="customCombo.js"></script>

    </body>
    <style>
        .tiny {
            width: 50px;
        }
        form {
            margin-bottom: 0;
        }
    </style>
</html>
