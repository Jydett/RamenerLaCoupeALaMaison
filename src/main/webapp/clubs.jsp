<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Clubs</title>
    <link rel="stylesheet" href="flags.css">
</head>
<body>
    <jsp:include page="login.jsp"/>

    <h2>Liste des clubs: <br><span style="font-size: small; text-decoration: none">(Cliquer pour en savoir plus)</span><br><a style="font-size: small;" href="index.jsp">Retour</a></h2>
    <c:forEach items="${requestScope.clubs}" var="club">
        <div>${club.country.icon}<a href="clubs?id=${club.id}" title="${club.name}">${club.name}</a></div>
    </c:forEach>
</body>
</html>
