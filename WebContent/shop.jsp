<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.google.gson.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="scripts/jquery-3.6.3.js"></script>

	<script src="scripts/shop.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/home.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/shop.css"/>
	
	<title>Shop</title>
</head>
<body>

	<%@include file = "view/BulkView.jsp" %>
	
	<section>
		<h1>Our Shop</h1>
		<h2>Our Courses</h2>
		<div id = "course_container" class = "product_container"></div>
		<br>
		<h2>Our Merchandise</h2>
		<div id = "merchandise_container" class = "product_container"></div>
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
		   		createProductBlock(JSONProduct, type);</script>
		   
<!--	   		<div class="product_block">
		   			<img src="images/logos/<%=currentProduct.getName().toLowerCase()%>.png" class= "product_logo" alt="product_logo">
		   			<div class="product_name"><%= currentProduct.getName() %></div>
		   			<div class="product_description"><%= currentProduct.getDescription() %></div>
		   			<div class="product_price"><%= currentProduct.getPrice() %> $</div>
		   			<input type="button" class="product_add" name="product_add" value="Add to Cart" 
		   			onClick= "updateCart(<%= currentProduct.getId()%>, 'Add')">
		   		</div>
			   
		    -->	 <%} %>
		   
		   </div>
	</section>
	

	
</body>
</html>