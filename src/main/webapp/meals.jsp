
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<button type="button" onclick="window.location.href = 'edit?action=create';">Add Meals</button>
<style>
    th, td {
        padding: 6px;
    }

    .no_double {
        border-collapse: collapse;
    }</style>
<table  border="1" width="530" style="border: 2px solid grey" class="no_double">
    <tr><th align="center">Date</th><th align="center">Description</th><th align="center">Calories</th><th></th><th></th></tr> <!--ряд с ячейками заголовков-->
    <c:forEach var="meal" items="${mealsList}">
        <c:if test="${meal.excess == false}">
                <tr style="border: 2px solid grey" class="no_double">
                    <td><font color="green">${meal.dateTime}</font></td>
                    <td><font color="green"> ${meal.description}</font></td>
                    <td><font color="green">${meal.calories}</font></td>
                    <td><a href="edit?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
        </c:if>
        <c:if test="${meal.excess == true}">
                <tr style="border: 2px solid grey" class="no_double">
                    <td><font color="red">${meal.dateTime}</font></td>
                    <td><font color="red"> ${meal.description}</font></td>
                    <td><font color="red">${meal.calories}</font></td>
                    <td><a href="edit?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
        </c:if>
        </p>
    </c:forEach>
</table>

</body>
</html>
