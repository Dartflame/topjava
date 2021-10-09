<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Editor</title>
</head>
<body>
<h3><a href="index.html" input type="button">Home</a></h3>
<hr>
<h2>Edit meals</h2>

<form method="post">
    <table width="530">
        <tr bgcolor="#fffafa">
            <th align="left">DateTime:</th>
            <th align="left"><input type="datetime-local" size="20" name="dateTime"></th>
        </tr>
        <tr bgcolor="#fffafa">
            <th align="left">Description:</th>
            <th align="left"><input type="text" size="30" name="description"></th>
        </tr>
        <tr bgcolor="#fffafa">
            <th align="left">Calories:</th>
            <th align="left"><input type="text" size="10" name="calories"></th>
        </tr>
    </table>
    <button type="submit">Save</button>
    <button type="button" onclick="window.location.href = 'meals';">Cancel</button>
</form>

</body>
</html>