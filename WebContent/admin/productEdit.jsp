<%@page import="com.code_fanatic.model.bean.MerchBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.google.gson.*" %>
<!DOCTYPE html>
<html>
<head>
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<script src="${ctxPath}/scripts/general.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/forms.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<meta charset="ISO-8859-1">
	<% ProductBean product = (ProductBean) request.getAttribute("product");
		Boolean isEdit = (Boolean) request.getAttribute("isEdit");
		if (product == null || isEdit == null) 
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		
		%>
		<script>var isEdit = <%= isEdit %>;
				<%	if (isEdit) {
					if (product.getType().equals("Course")) { %>
					var product = <%= new Gson().toJson((CourseBean) product)%>
					<%} else { %>
					var product = <%= new Gson().toJson((MerchBean) product) %>
					<%}} else {%>
					var product = <%= new Gson().toJson(product)%>
					<%} %>
					</script>
				
<title>Product <%= isEdit ? "Edit" : "Create" %></title>
</head>
<body>
	<%@include file="../view/header.jsp" %>
	
	<section class="main_section">
		<form action="productEdit" method="POST" enctype="multipart/form-data">
		
			<fieldset id="product_fieldset">
			<legend>Product <%= isEdit ? "Edit" : "Create" %></legend>
			
		
			<% if (isEdit) { %><img src="${ctxPath}/images/logos/<%= product.getName().toLowerCase().replaceAll("\\s", "") %>.png"
			class = "product_logo" alt = "product_logo">
			<%} %>
			
			<label for="logo_input">Upload a new logo:</label>
			<input type="file" id="logo_input" name ="logo_input" accept="image/*">
			
			
			<label for="name_input"><%= isEdit ? "Edit" : "Insert new" %> Product Name</label>
			<input type="text" id="name_input" name = "name_input" value="${product.name}" 
			pattern="^[a-zA-Z._]+$" required>
			
			<label for="description_input"><%= isEdit ? "Edit" : "Insert new" %> Product Description:</label>
			<textarea id="description_input" name ="description_input" required>${product.description}</textarea >
			
			<label for="price_input"><%= isEdit ? "Edit" : "Insert new" %> Price:</label>
			<input type="number" id="price_input" name ="price_input" value="${product.price}" required> $
			
			<% if (!isEdit) {%>
			<select id="type_input" name="type_input">
				<option value="Course">Course</option>
				<option value="Merchandise">Merchandise</option>
			</select>
			<%} else { %>
				<input type="hidden" id = "type_input" name="type_input" value="${product.type}">
			<%} %>
			
			
			<input type="hidden" id="product_id" name = "product_id" value = "${product.id}">
			<input type="hidden" id="type" name = "type" value=<%= isEdit ? "edit" : "create" %>>
			
			<button type="submit" id="submit_button">Confirm Changes</button>
			<button type="reset">Reset Changes</button>
			
			</fieldset>
		</form>
	
	</section>
	
	<%@include file="../view/footer.jsp" %>
	<script src="${ctxPath}/scripts/productEdit.js"></script>

</body>
</html>