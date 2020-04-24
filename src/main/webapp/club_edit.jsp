<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Club</title>
        <link rel="stylesheet" href="flags.css">
        <link rel="stylesheet" href="customCombo.css">
    </head>
    <body>
        <jsp:include page="login.jsp"/>
        <c:set var="userConnect" value="${sessionScope.connected != null}"/>
        <c:set var="hasError" value="${not empty requestScope.error}"/>
        <c:set var="edition" value="${not empty paramValues['id']}"/>
        <jsp:useBean id="countries" scope="request" type="java.util.List"/>

        <c:if test="${not hasError && edition}">
            <jsp:useBean id="club" type="fr.polytech.rlcalm.beans.Club" scope="request"/>
        </c:if>
        <c:choose>
            <c:when test="${edition}">
                <h2>Edition d'un club :<br><a style="font-size: small;" href="clubs?id=${club.id}">Retour</a></h2>
            </c:when>
            <c:otherwise>
                <h2>Creation d'un club :<br><a style="font-size: small;" href="clubs">Retour</a></h2>
            </c:otherwise>
        </c:choose>

        <c:if test="${hasError}">
            <c:set scope="request" var="edition" value="${requestScope.club ne null}"/>
            <div class="error">${requestScope.error}</div>
        </c:if>

        <form action="clubEdit" method="post">
            <c:if test="${edition}">
                <input type="hidden" name="id" value="${club.id}"/>
            </c:if>
            <div><label for="name">Nom : </label><input id="name" name="name" value="${club.name}" required/></div>

            <div>Pays : <span class="custom-select-wrapper">
                    <div class="custom-select">
                        <input type="hidden" name="countryId" class="form-input" value="">
                        <div class="custom-select__trigger"><span>Select a country</span>
                            <div class="arrow"></div>
                        </div>
                        <div class="custom-options">
                            <c:forEach items="${countries}" var="country">
                                <span class="custom-option <c:if test="${country.id == (hasError ? param.countryId : club.country.id)}">selected</c:if>" data-value="${country.id}">${country.icon}${country.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                </span>
            </div>
            <br>
            <c:if test="${userConnect}">
                    <span>
                        <input type="submit" formaction="clubEdit?action=createOrUpdate" value="Enregistrer les changements"/>
                        <c:if test="${edition}">
                            <input type="submit" formaction="clubEdit?action=delete" value="Supprimer">
                        </c:if>
                    </span>
            </c:if>
        </form>
        <script type="text/javascript" src="customCombo.js"></script>
    </body>
</html>
