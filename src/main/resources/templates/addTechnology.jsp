<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Add technology jsp, template </title>
</head>
<body>
 
<div align="center">
    <h2>Dynamic Drop Down List Demo</h2>
    <form method="POST" action="/listAllTechnology">
        Select a Category:&nbsp;
        <select name="category">
            <c:forEach items="${techs}" var="tech">
                <option value="${tech.category}">
                    <!-- <c:if test="${tech.id eq selectedCatId}">selected="selected"</c:if>
                    > --> 
                    ${tech.category}
                </option>
            </c:forEach>
        </select>
        <br/><br/>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>