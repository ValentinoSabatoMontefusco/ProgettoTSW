<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<script src="${ctxPath}/scripts/general.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/tables.css"/>
<title>Orders Recap</title>
</head>
<body>
	
	<%@include file="/view/header.jsp" %>
	<section class="main_section">
	<form action="ordersRecap" method="POST">
		<div class="sort_container">
			<div class="limits_container">
			<label for="all_items">Show All</label>
			<input type="checkbox" name="all_items" id="all_items" checked="checked" value="selected">
			
			
			<label for="from_date">From: </label>
			<input type="datetime-local" name="from_date" id ="from_date" disabled="disabled">
			
			<label for="to_date"> To:</label>
			<input type="datetime-local" name="to_date" id = "to_date" disabled="disabled">
			
		</div>
		<div class="order_by_container">
			Show by: 
			
			<label for="order_by_users">Date</label>
			<input type="radio" name="order_by" id="order_by_users" checked="checked" value="date">
			
			
			<label for="order_by_date">Users</label>
			<input type="radio" name="order_by" id="order_by_date" value="users">
						
		</div>
		
		<div class="order_container">
			Sorting Order: 
			<label for="sort_desc">Descending</label>
			<input type="radio" name="sorting_order" id="sort_desc" checked="checked" value="desc">
			
			<label for="sort_asc">Ascending</label>
			<input type="radio" name="sorting_order" id="sort_asc" value="asc">
			
		</div>
		
		<input type="submit" name="sort_submit" value="Show">
			
		</div>
	</form>
	<%@include file ="/view/orders.jsp" %>
	
	
	</section>
	<script src="${ctxPath}/scripts/orders.js"></script>
	<%@include file="/view/footer.jsp" %>
</body>
</html>