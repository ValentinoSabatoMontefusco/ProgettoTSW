<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.code_fanatic.model.bean.*" %>
<%@ page import="java.util.Map.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/home.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/mycart.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/buttons.css"/>
	<title>My Orders</title>
</head>
<body>
	<%@include file="/view/BulkView.jsp" %>
	<section>
		<div class="navigation_div"><a href = "${ctxPath}/user/myaccount.jsp">My Account</a> > My Cart</div>
		
		<br>
		
		<h1>My Cart</h1>
		<br><br>
		<div class = "product_container">
		
		<% @SuppressWarnings("unchecked")
			Collection<Entry<ProductBean, Integer>> products = (Collection<Entry<ProductBean, Integer>>) request.getAttribute("products");
		   if (products == null || products.isEmpty()) {
		   %>
		   <div>Your cart is empty</div>
		   <%} else {
		
			ProductBean currentProduct = null;
			int quantity;
		   	for (Entry<ProductBean, Integer> entry : products) {
		   		
		   		currentProduct = entry.getKey();
		   		quantity = entry.getValue();
		   		
		    %>
		   
		   	<div class="product_block">
		   		<input type="hidden" class="product_id" value="<%=currentProduct.getId()%>"></input>
	  			<img src="${ctxPath}/images/logos/<%=currentProduct.getName().toLowerCase().replaceAll("\\s", "") %>.png" class= "product_logo" alt="product_logo">
	  			<div class="product_name"><%= currentProduct.getName() %></div>
	  			<div class="product_description"><%= currentProduct.getDescription() %></div>
	  			<div class="quantity_block">
	  				<div class="merch_amount" data-product-type="<%= currentProduct.getType()%>" 
	  				data-amount="<%= currentProduct.getType().equals("Merchandise") ? ((MerchBean) currentProduct).getAmount() : 1 %>"></div>
	  				<div class="product_quantity">Qt. <span class = "quantity"><%= quantity %></span></div>
	  				<!--  <input type="button" class="product_buttonAdd" value="+"></input>
	  				<input type="button" class="product_buttonSub" value="-"></input>-->
	  				<button class="product_buttonAdd"  <%= currentProduct.getType().equals( "Course") ? "disabled" : "" %>>+</button>
	  				<button class="product_buttonSub">-</button>
	  			</div>
	  			<div class="product_price"><%= currentProduct.getPrice() %> $</div>
	  			<div class="product_subtotal"><%= currentProduct.getPrice() * quantity %> $</div>
  			</div>
	   
		   
		   
		   <%}} %>
		   
		</div>
		<div id = "recap">
			<div id = "total">Total: <span id ="total_price"></span> $</div>
			<br>
			<button id="empty_cart">Empty the Cart</button>
			<button id="buy_now">Proceed to Checkout</button>
		</div>
	</section>
	
	
	<script src="${ctxPath}/scripts/general.js"></script>
	<script src="${ctxPath}/scripts/mycart.js"></script>
</body>
</html>
