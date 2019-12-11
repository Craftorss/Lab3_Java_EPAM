<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 10.12.2019
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Station</title>
</head>
<body>

<jsp:useBean id="station" scope="request" type="telephoneStation.entity.Station"/>

<table border="1">
    <caption>Subscribers table</caption>
    <tr>
        <th>firstName</th>
        <th>patronymic</th>
        <th>lastName</th>
        <th>mobileOperator</th>
        <th>countryCode</th>
        <th>number</th>
        <th>fullPhoneNumber</th>
        <th>country</th>
        <th>city</th>
        <th>homeNumber</th>
        <th>street</th>
        <th>flatNumber</th>
        <th>isFlat</th>


    </tr>
    <c:forEach var="sub" items="${station.subs}" >
    <tr><td>${sub.firstName}</td><td>${sub.patronymic}</td>
        <td>${sub.lastName} </td><td>${sub.phoneNumber.mobileOperator}</td>
        <td>${sub.phoneNumber.countryCode}</td><td>${sub.phoneNumber.number}</td>
        <td>${sub.phoneNumber.fullPhoneNumber}</td><td>${sub.adr.country}</td>
        <td>${sub.adr.city}</td><td>${sub.adr.homeNumber}</td>
        <td>${sub.adr.street}</td><td>${sub.adr.flatNumber}</td>
        <td>${sub.adr.flat} </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
