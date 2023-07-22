<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.google.gson.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<script src="${ctxPath}/scripts/general.js"></script>
	<script src="${ctxPath}/scripts/shop.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/shop.css"/>
<title>Product Management</title>
</head>
<body>

<%@include file = "/view/header.jsp" %>
	
	<section class="main_section">
		<h1>Admin Product Management</h1>
		<br>
		<div>Click below to add a new product or click on the already existing products to edit (or delete!) them</div>
		<button class="product_new" value="New Product">New Product</button>
		<h2>Our Courses</h2>
		<div id = "course_container" class = "product_container"></div>
		<br>
		<h2>Our Merchandise</h2>
		<div id = "merchandise_container" class = "product_container">
		<% @SuppressWarnings("unchecked")
		   Collection<ProductBean> Products = (Collection<ProductBean>) request.getAttribute("products");
		
		   Iterator<ProductBean> it = Products.iterator();
		   ProductBean currentProduct;
		   
		   while(it.hasNext()) {
		   		currentProduct = it.next();
		   		String JSONProduct = new Gson().toJson(currentProduct);
		   		%>
		   		<script>
		   		var type = '<%= currentProduct.getType() %>';
		   		var JSONProduct = '<%= JSONProduct %>';
		   		var admin = true;
		   		createProductBlock(JSONProduct, type, admin);</script>
		   		
		   				   <%} %>
		   </div>
	</section>
	
<%@include file = "/view/footer.jsp" %>
</body>
</html>