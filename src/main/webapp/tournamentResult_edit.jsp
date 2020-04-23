<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tournois</title>
    <link rel="stylesheet" href="error.css">
</head>
    <body>
        <jsp:include page="login.jsp"/>

        <c:choose>
            <c:when test="${edition}">
                <h2>Edition d'un résultat :<br><a style="font-size: small;" href="results">Retour</a></h2>
            </c:when>
            <c:otherwise>
                <h2>Creation d'un résultat :<br><a style="font-size: small;" href="results">Retour</a></h2>
            </c:otherwise>
        </c:choose>

        <c:set var="hasError" value="${not empty requestScope.error}"/>

        <c:if test="${hasError}">
            <div class="error">${requestScope.error}</div>
        </c:if>

        <jsp:useBean id="tournamentResult" type="java.util.List" scope="request"/>

        <form action="resultsEdit" method="post">
            <table>
                <c:forEach items="${tournamentResult}" var="res" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td class="arrow"><span onclick="up(this)">&uarr;</span><span onclick="down(this)">&darr;</span></td>
                        <td><input type="hidden" value="${res.id}" name="order">${res.club.name}</td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <input type="submit" value="Enregistrer">
        </form>
        <script>
            function up(el) {
                let tr = el.parentElement.parentElement;
                if (tr.previousElementSibling != null) {
                    let td = tr.lastElementChild;
                    let newTd = tr.previousElementSibling.lastElementChild;
                    let save = td.innerHTML;
                    td.innerHTML = newTd.innerHTML;
                    newTd.innerHTML = save;
                }
            }
            function down(el) {
                let tr = el.parentElement.parentElement;
                if (tr.nextElementSibling != null) {
                    let td = tr.lastElementChild;
                    let newTd = tr.nextElementSibling.lastElementChild;
                    let save = td.innerHTML;
                    td.innerHTML = newTd.innerHTML;
                    newTd.innerHTML = save;
                }
            }
        </script>
    </body>
<style>
    .arrow {
        cursor: pointer;
    }
</style>
</html>
