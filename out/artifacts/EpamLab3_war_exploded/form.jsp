<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 10.12.2019
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Выбор</title>
</head>
<body>
    <h3>Choose parser</h3>
    <form action="main" method="GET">
        <label>
            Use Parser:
            <select name="parseType">
                <option>DOM</option>
                <option>SAX</option>
                <option>StaX</option>
            </select>
        </label>
        <br><br>
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
