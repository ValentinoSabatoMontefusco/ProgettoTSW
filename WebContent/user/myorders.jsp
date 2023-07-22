<%@page import="javax.swing.plaf.basic.BasicBorders"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.code_fanatic.model.bean.*" %>
<%@ page import="java.util.Map.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/home.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/mycart.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/buttons.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/tables.css"/>
	<title>My Cart</title>
</head>
<body>
	<%@include file="/view/BulkView.jsp" %>
	<%@include file="/view/orders.jsp"%>
	
	
	<script src="${ctxPath}/scripts/general.js"></script>
</body>
</html>