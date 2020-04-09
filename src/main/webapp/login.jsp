<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <c:choose>
        <c:when test="${empty sessionScope.connected}">
            <c:if test="${requestScope.loginError != null}">
                <div style="color: red;">${requestScope.loginError}</div>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/libraryLogin">
                <label for="username">Nom d'utilisateur: </label><input type="text" name="username" id="username"/>
                <label for="password">Mot de passe: </label><input type="password" name="password" id="password"/>
                <input type="submit" value="Se connecter !"/>
            </form>
        </c:when>
        <c:otherwise>
            <jsp:useBean id="connected" scope="session" class="fr.polytech.rlcalm.beans.User"/>
            <span> Connecté en tant qu${fn:indexOf("aeiouhy", fn:substring(connected.username, 0, 1)) < 0 ? 'e ' : '\''} <b>${sessionScope.connected.username}</b>
                <form style="display: inline" method="post" action="${pageContext.request.contextPath}/libraryLogin">
                    <input type="hidden" name="action" value="logout"/>
                    <input type="submit" value="Se déconnecter"/>
                </form>
            </span>
        </c:otherwise>
    </c:choose>
</div>

<c:set var="hasError" value="${not empty requestScope.error}"/>

<c:if test="${hasError}">
    <c:set scope="request" var="edition" value="${requestScope.match ne null}"/>
    <div class="error">${requestScope.error}</div>
</c:if>
