<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div>
    <style><%@include file="css/error.css"%></style>
    <c:if test="${requestScope.loginError != null}">
        <div class="error">${requestScope.loginError}</div>
    </c:if>
    <c:choose>
        <c:when test="${empty sessionScope.connected}">
            <a href="index.jsp" style="display: inline">Menu</a>&nbsp&nbsp
            <form method="post" action="${pageContext.request.contextPath}/appLogin" style="display: inline">
                <label for="username">Nom d'utilisateur: </label><input type="text" name="username" id="username" value="admin"/>
                <label for="password">Mot de passe: </label><input type="password" name="password" id="password" value="password"/>
                <input type="submit" value="Se connecter !"/>
            </form>
        </c:when>
        <c:otherwise>
            <jsp:useBean id="connected" scope="session" class="fr.polytech.rlcalm.beans.User"/>
            <a href="index.jsp" style="display: inline">Menu</a>&nbsp&nbsp
            <span> Connecté en tant qu${fn:indexOf("aeiouhy", fn:substring(connected.username, 0, 1)) < 0 ? 'e ' : '\''} <b>${sessionScope.connected.username}</b>
                <form style="display: inline" method="post" action="${pageContext.request.contextPath}/appLogin">
                    <input type="hidden" name="action" value="logout"/>
                    <input type="submit" value="Se déconnecter"/>
                </form>
            </span>
        </c:otherwise>
    </c:choose>
</div>
