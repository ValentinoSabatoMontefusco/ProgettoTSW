<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.code_fanatic.model.bean.*" %>
<%@ page import="java.util.Map.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/mycart.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/buttons.css"/>
	<title>My Orders</title>
</head>
<body>
	<%@include file="/view/header.jsp" %>
	<section class="main_section">
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
			Boolean addable;
		   	for (Entry<ProductBean, Integer> entry : products) {
		   		addable = true;
		   		currentProduct = entry.getKey();
		   		quantity = entry.getValue();
		   		
		    %>
		   
		   	<div class="product_block">
		   		<div class="info_block">
			   		<input type="hidden" class="product_id" value="<%=currentProduct.getId()%>"></input>
		  			<img src="${ctxPath}/images/logos/<%=currentProduct.getName().toLowerCase().replaceAll("\\s", "") %>.png" class= "product_logo" alt="product_logo">
		  			<div class="product_name"><%= currentProduct.getName() %></div>
		  			<div class="product_description"><%= currentProduct.getDescription() %></div>
		  			<div class="product_price"><%= currentProduct.getPrice() %> $</div>
	  				<div class="product_subtotal">Subtotal: <span class="subtotal_span"><%= currentProduct.getPrice() * quantity %></span> $</div>
	  			</div>
	  			<div class="quantity_block">
	  				<div class="merch_amount" data-product-type="<%= currentProduct.getType()%>" 
	  				data-amount="<%= currentProduct.getType().equals("Merchandise") ? ((MerchBean) currentProduct).getAmount() : 1 %>">
	  				<%= (currentProduct.getType().equals("Merchandise") && (((MerchBean) currentProduct).getAmount() - quantity <= 5)) ?
	  					"Total left: " + ((MerchBean) currentProduct).getAmount() 
	  						:
	  						"" %>
	  				</div>
	  				<div class="product_quantity">Qt. <span class = "quantity"><%= quantity %></span></div>
	  			
	  				<button class="product_buttonAdd"  
	  				<% if (currentProduct.getType().equals("Course") || 
	  					(currentProduct.getType().equals("Merchandise") &&
	  					((MerchBean) currentProduct).getAmount() <= quantity))
	  						addable = false;
	  					%>
	  				<%= !addable ? "disabled" : "" %>>+</button>
	  				<button class="product_buttonSub">-</button>
	  			</div>
	  			
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
	
	<%@include file="/view/footer.jsp" %>

	<script src="${ctxPath}/scripts/mycart.js"></script>
	
	
</body>
</html>
