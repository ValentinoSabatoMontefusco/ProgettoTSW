<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<script src="${ctxPath}/scripts/general.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<title>Comment Moderation</title>
</head>
<body>

	<%@include file="/view/header.jsp" %>
	
	<section class="main_section">
	
		<form action="moderation" method="post">
			
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
		
		<h1>Comment Moderation</h1>
		<div id = "comments_container">
		<% 	@SuppressWarnings("unchecked")
			Collection<CommentBean> comments = (Collection<CommentBean>) request.getAttribute("comments");
			
			if (comments != null && comments.size() > 0) {
				for (CommentBean comment : comments) { %>
				
				<div class ="comment_container">
					
					<div class="comment_user"><%= comment.getUser_username() %></div>
					<div class="comment_date"><%= comment.getCreate_time() %></div>
					<br>
					<div class="comment_content"><%= comment.getContent() %></div>
					
			
					<button class="delete_comment" data-id="<%= comment.getId() %>">Delete Comment</button>

				</div>
				<br>
			<%}} 
			else { %>
					
				<div class="comment_warning">No comment for this product</div>
					
			<% } %>
		
		</div>
		
	
	
	
	</section>
	
	
	
	
	
	
	<%@include file="/view/footer.jsp" %>
	<script src="${ctxPath}/scripts/comments.js"></script>

</body>
</html>


<!--  

<label for="sort_by">Sort By: </label>
			<input type="radio" name="sort_by" id = "sort_by_user" value="User">
			<input type="radio" name="sort_by" id = "sort_by_date" checked="checked" value="Creation_Date">
			
			<label for="order">Order: </label>
			<input type="radio" name="order" id="order_ASC" value="Ascending">
			<inpuyt type="radio" name="order" id="order_DESC" checked="checked" value ="Descending"> -->