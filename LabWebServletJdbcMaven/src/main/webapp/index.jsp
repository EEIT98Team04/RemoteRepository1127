<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>
<h1>庭瑋</h1>
<h1>嫩</h1>

<h1>66666666696969666666</h1>
<h1>馬全才</h1>
<h1>冠廷</h1>
<h1>487?</h1>
<h1>明輝</h1>
<h2>4ni</h2>
<h3>4wo</h3>
<h3>Demo Welcome ${user.email}</h3>
<h3><a href="<c:url value="/secure/login.jsp"/>">Login</a></h3>
<h3><a href="<c:url value="/pages/product.jsp"/>">Poduct</a></h3>

<c:if test="${not empty user}">
<h3><a href="<c:url value="/secure/logout.controller"/>">Logout</a></h3>
</c:if>

</body>
</html>