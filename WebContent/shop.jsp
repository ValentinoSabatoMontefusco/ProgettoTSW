<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.google.gson.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<script src="${ctxPath}/scripts/general.js"></script>
	<script src="${ctxPath}/scripts/shop.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/shop.css"/>
	
	<title>Shop</title>
</head>
<body>

	<%@include file = "view/header.jsp" %>
	
	<section class="main_section">
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
		   MerchBean tempMerch = null;
		   Boolean inCart = false;
		   String JSONProduct;
		   
		   
		   while(it.hasNext()) {
		   		currentProduct = it.next();
		   		if (currentProduct.getType().equals("Merchandise")) {
		   			tempMerch = (MerchBean) currentProduct;
		   			JSONProduct = new Gson().toJson(tempMerch);
		   		} else {
		   			JSONProduct = new Gson().toJson(currentProduct);
		   		}
		   			
	
		   		if (cart != null && currentProduct != null) {
					   if (currentProduct.getType().equals("Course") && cart.getProductQuantity(currentProduct.getId()) > 0)
						   inCart = true;
					   else if (currentProduct.getType().equals("Merchandise") && cart.getProductQuantity(tempMerch.getId()) >= tempMerch.getAmount())
						   inCart = true;
					   else
						   inCart = false;
				   }
		   		%>
		   		<script>
		   		var type = '<%= currentProduct.getType() %>';
		   		var inCart = <%= inCart %>;
		   		var JSONProduct = '<%= JSONProduct %>';
		   		var admin = false;
		   		createProductBlock(JSONProduct, type, admin, inCart);</script>
		   
<!--	   		<div class="product_block">
		   			<img src="images/logos/<%=currentProduct.getName().toLowerCase()%>.png" class= "product_logo" alt="product_logo">
		   			<div class="product_name"><%= currentProduct.getName() %></div>
		   			<div class="product_description"><%= currentProduct.getDescription() %></div>
		   			<div class="product_price"><%= currentProduct.getPrice() %> $</div>
		   			<input type="button" class="product_add" name="product_add" value="Add to Cart" 
		   			onClick= "updateCart(<%= currentProduct.getId()%>, 'Add')">
		   		</div>
			   
		    -->	 <%} %>
		   
		   
	</section>
	
	<%@include file = "view/footer.jsp" %>
	
</body>
</html>