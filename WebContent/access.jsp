<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/home.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/Forms.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/buttons.css"/>
	<% String accessType = request.getParameter("type"); %>
<title><%= accessType.equals("login") ? "Login" : "Registration" %> page</title>
</head>
<body>
	<%@include file ="view/BulkView.jsp" %>
	
	<section id = "form_section"> 
		<div class="error_container"><% @SuppressWarnings("unchecked")
								Collection<String> errors = (Collection<String>) request.getAttribute("errors");
								if (errors != null)
									for (String error : errors) {%>
			<div class="error"><%= error %></div>			
			<%} %>			
		</div>
		<form action = "${ctxPath}/access" method="POST">
			<input type = "hidden" name = "action" value = "<%=accessType%>">
			<fieldset>
				<legend><%= accessType.equals("login") ? "Login" : "Registration" %></legend>
				
				<label for="username">Nome Utente: </label>
				<input type = "text" id = "username" name = "username" placeholder = "pluto2000" autofocus="autofocus"></input>
				<br>
				<label for="password">Password: </label>
				<input type = "password" id = "password" name = "password" autofocus="autofocus"></input>
				<br>
			</fieldset>
			
			<!--  <input type="submit" id="submitButton" value = "Conferma"/>
			<input type="reset" value = "Reset">-->
			<button type="submit" id="submitButton">Confirm</button>
			<button type="reset">Reset</button>
		
		</form>
	
	</section>


	<script src="${ctxPath}/scripts/FormValidation.js"></script>
</body>
</html>

<% /* 				<label for="email">E-Mail: </label>
				<input type = "email" id = "email" name = "email" placeholder = "pippo@topolino.it" autofocus="autofocus"></input>
				<br> */ %>