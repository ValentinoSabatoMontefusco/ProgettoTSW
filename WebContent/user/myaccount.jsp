<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.code_fanatic.model.bean.*" %>
<%  String username = (String) request.getAttribute("username"); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<title> ${username} Page</title>
</head>
<body>
	<%@include file="/view/header.jsp" %>
	<section class="main_section">
		<h1>My Account</h1>
		<div>Hi ${username}, here you can control and manage all your personal information and items!</div>
		<br><br>
		<div class ="options_container">
			<a href="${ctxPath}/user/myCourses">My Courses</a>
			<br><br>
			<a href="${ctxPath}/user/myCart">My Cart</a>
			<br><br>
			<a href="${ctxPath}/user/myOrders">My Orders</a>
		<br><br>
		</div>
	</section>
	<%@include file="/view/footer.jsp" %>
</body>
</html>