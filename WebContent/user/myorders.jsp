<%@page import="javax.swing.plaf.basic.BasicBorders"%>
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
	<title>My Cart</title>
</head>
<body>
	<%@include file="/view/BulkView.jsp" %>
	<section>
		<div class="navigation_div"><a href = "${ctxPath}/user/myaccount.jsp">My Account</a> > My Cart</div>
		
		<br>
		
		<h1>My Orders</h1>
		<br><br>
		<div class = "product_container">
		
		<% @SuppressWarnings("unchecked")
			Collection<OrderBean> orders = (Collection<OrderBean>) request.getAttribute("orders");
		   if (orders == null || orders.isEmpty()) {
		   %>
		   <div>You ain't made no orders</div>
		   <%} else {
		   
		   for(OrderBean order : orders) {
		   		
			   
		   		%>
		   <div class="order_presentation">Order made in <%= order.getOrder_date().getDate() %></div>
		   <table>
		   	<tr>
		   		<th>Product</th>
		   		<th>Name</th>
		   		<th>Price</th>
		   		<th>Quantity</th>
		   		<th>Subtotal</th>
		   	</tr>
		   	
		   	<%
		   		for (Entry<ProductBean, Integer> entry : order.getProducts()) {
		   			
		   			ProductBean product = entry.getKey();
		   			%>
		   			
		   		<tr>
		   			<td><img src="${ctxPath}/images/logios/<%= product.getName().toLowerCase() %>.png"
		   			 alt = "product logo"></td>
		   			 <td><%= product.getName() %></td>
		   			 <td><%= product.getPrice() %></td>
		   			 <td><%= entry.getValue() %></td>
		   			 <td><%=  entry.getValue() * product.getPrice() %><td>
		   		</tr>
		   		<% }
		   	%>
		

		   
		   <%} %>
		   
		   </table>
		</div>
		<br>
		<%} %>
	</section>
	
	
	<script src="${ctxPath}/scripts/general.js"></script>
</body>
</html>