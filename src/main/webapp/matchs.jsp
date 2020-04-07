<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" href="flags.css">
</head>
<body>
    <c:choose>
        <c:when test="${requestScope.matchs != null}">
            <jsp:useBean id="matchs" scope="request" type="java.util.List"/>
            <c:forEach var="match" items="${matchs}">
                <div>Match :</div>
                <div>&nbsp;&nbsp;${match.formattedDate}: </div>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;${match.stringView}<br></div>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;<i>${match.stadium}, ${match.city}</i></div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h2>Pas de matchs de prévu !</h2>
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
</style>
</html>