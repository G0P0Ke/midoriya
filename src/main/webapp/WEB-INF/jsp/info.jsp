<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <style><%@include file="/WEB-INF/css/style.css"%></style>
    <hr>
    <p style="text-align: center; font-family:Gill Sans, sans-serif;color: #fff; font-size: 2.0em">Charts</p>
    <div class="button_div">
        <input class="button-next-page" type="button" value="Global export" onclick="window.location='global-export'">
        <input class="button-next-page" type="button" value="Petroleum export" onclick="window.location='petroleum-export'">
        <input class="button-next-page" type="button" value="Non-primary export" onclick="window.location='non-primary-export'" >
        <input class="button-next-page" type="button" value="Global import" onclick="window.location='global-import'" >
        <input class="button-next-page" type="button" value="Category import" onclick="window.location='category-import'" >
    </div>
    <hr>
</head>
<body>
<div>

</div>
<div align="center">
    <form:form method="post" modelAttribute="selectForm">
        <table>
            <tr>
                <td style="font-family:Gill Sans, sans-serif;color: #fff;">Code:</td>
                <td><form:input path="code" value="${code}" required="required"/></td>
            </tr>
            <tr>
                <td style="font-family:Gill Sans, sans-serif;color: #fff;">Title:</td>
                <td><form:input path="title" value="${title}" required="required"/></td>
            </tr>
            <input type="submit" value="add new cmdCode"/>
        </table>
    </form:form>
    <table border="1" cellpadding="5">
        <caption style="font-family:Gill Sans, sans-serif;color: #fff;"><h2>List of cmdCodes</h2></caption>
        <tr style="font-family:Gill Sans, sans-serif;color: #f8f8f8;">
            <th>Code</th>
            <th>Description</th>
            <th>Existence in the api</th>
        </tr>
        <c:forEach var="productCode" items="${productCodeList}">
            <tr style="font-family:Gill Sans, sans-serif;color: #f8f8f8;">
                <td><c:out value="${productCode.code}"/></td>
                <td><c:out value="${productCode.description}"/></td>
                <td><c:out value="${productCode.exist}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>