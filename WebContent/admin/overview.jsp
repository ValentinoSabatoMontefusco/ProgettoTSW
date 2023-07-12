<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
<title>Admin page</title>
</head>
<body>
	<%@include file="../view/header.jsp" %>
	<section class="main_section">
		<h1>Admin Special Features</h1>
		<div>In this section you can handle your administrative powers to insert new products, review orders and more!</div>
		<br><br>
		<div class="options_container">
			<a href="${ctxPath}/admin/productManagement">Products Management</a>
			<br><br>
			<a href="${ctxPath}/admin/ordersRecap">Orders Recap</a>
			<br><br>
			<a href="${ctxPath}/user/myOrders">Moderation</a>
			<br><br>
		</div>
	</section>
	<%@include file="../view/footer.jsp" %>
</body>
</html>