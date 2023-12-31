<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<script src="${ctxPath}/scripts/general.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/product.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/comments.css"/>
	
	<jsp:useBean id="product" class="com.code_fanatic.model.bean.ProductBean" scope = "request"></jsp:useBean>
		
	<title>${product.name} page</title>
</head>
<body>
	<%@include file="/view/header.jsp" %>
	
	<section class ="main_section">
		<div class="product_container">
			
			<div class="product_block">
		   			<img src="images/logos/<%=product.getName().toLowerCase().replaceAll("\\s", "") %>.png" class= "product_logo" alt="product_logo">
		   			<div class="product_name">${product.name}</div>
		   			<div class="product_description">${product.description}</div>
		   			<div class="product_price">${product.price} $</div>
		   			
		   			<% String username = (String) request.getSession(false).getAttribute("username");
		   			
		   				switch(product.getType()) {
		   			
		   				case "Course": %> <div class="course_lessons">Number of Lessons: <%= ((CourseBean) product).getLessonCount() %></div>
		   				<%   		break;
		   				case "Merchandise": %> <div class="merch_amount">Amount available: <%= ((MerchBean) product).getAmount() %></div>
		   				<%			break;
		   				default: break;
		   				
		   				}%>
		   			<div class = "button_container">
		   			
		   			<!--  Different view based on user role  -->
		   			<% 	String role = (String) request.getSession().getAttribute("role");
		   				if (role != null) {
		   					if (role.equals("admin")) { %>
		   				
		   				<button class = "product_edit" name="product_edit" value="Edit Product">Edit Product</button>
		   				<button class = "product_delete" name="product_delete" value="Delete Product">Delete Product</button>
		   			<%} else if (role.equals("user")) { 
		   				
		   			
		   				if (((Collection<Integer>) request.getSession().getAttribute("productsOwned")).contains(product.getId())) {%>
		   				
		   				<button class = "product_owned" name = "product_owned" value ="You already own this product!" disabled="disabled">
		   				You already own this product!</button>
		   				<%}}} else { %>
		   			<button class="product_add" name="product_add" value="Add to Cart" 
		   			onClick= "updateCart(${product.id}, 'Add')">Add to Cart</button>
		   			<% } %>
		   			</div>
		   		</div>
		</div>
		

			<br>
			
		
			<%@include file="view/comments_form.jsp" %>
			<%@include file="view/comments.jsp"%>
		
		
		</section>
	</section>
	
	<!-- Snippet to mediate to pass the Product to JavaScript -->
	<script>
		var JSONProduct = '<%= new Gson().toJson(product) %>';
	</script>
	
	
	<%@include file="/view/footer.jsp" %>
	<script src="${ctxPath}/scripts/product.js"></script>
	
</body>
</html>