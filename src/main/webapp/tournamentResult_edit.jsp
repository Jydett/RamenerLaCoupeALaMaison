<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tournois</title>
    <link rel="stylesheet" href="error.css">
</head>
    <body>
        <jsp:include page="login.jsp"/>

        <c:set var="creation" value="${'creation' == requestScope.action}"/>

        <c:choose>
            <c:when test="${! creation}">
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
            <c:if test="${creation}">
                <input type="hidden" name="year" value="${requestScope.year}">
            </c:if>
            <table>
                <c:forEach items="${tournamentResult}" var="res" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td class="arrow"><span onclick="up(this)">&uarr;</span><span onclick="down(this)">&darr;</span></td>
                        <c:choose>
                            <c:when test="${creation}">
                                <td><input type="hidden" value="${res.club.id}" name="order">${res.club.name}</td>
                            </c:when>
                            <c:otherwise>
                                <td><input type="hidden" value="${res.id}" name="order">${res.club.name}</td>
                            </c:otherwise>
                        </c:choose>
                        <td><button onclick="del(this)">Supprimer cette équipe du classement</button></td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <c:choose>
                <c:when test="${creation}">
                    <input type="submit" formaction="resultsEdit?action=create" value="Creer le résultat">
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Enregistrer">
                </c:otherwise>
            </c:choose>
        </form>
        <script>
            function up(el) {
                let tr = el.parentElement.parentElement;
                if (tr.previousElementSibling != null) {
                    let td = tr.lastElementChild.previousElementSibling;
                    let newTd = tr.previousElementSibling.lastElementChild.previousElementSibling;
                    let save = td.innerHTML;
                    td.innerHTML = newTd.innerHTML;
                    newTd.innerHTML = save;
                }
            }
            function down(el) {
                let tr = el.parentElement.parentElement;
                if (tr.nextElementSibling != null) {
                    let td = tr.lastElementChild.previousElementSibling;
                    let newTd = tr.nextElementSibling.lastElementChild.previousElementSibling;
                    let save = td.innerHTML;
                    td.innerHTML = newTd.innerHTML;
                    newTd.innerHTML = save;
                }
            }
            function del(el) {
                let tr = el.parentElement.parentElement;
                let n = parseInt(tr.firstElementChild.innerHTML);
                let sib = tr;
                while ((sib = sib.nextElementSibling) != null) {
                    sib.firstElementChild.innerHTML = n + "";
                    n = n + 1;
                }
                tr.parentElement.removeChild(tr);
            }
        </script>
    </body>
<style>
    .arrow {
        cursor: pointer;
    }
</style>
</html>
