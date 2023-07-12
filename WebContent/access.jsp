<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/forms.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/buttons.css"/>
	<% String accessType = request.getParameter("type"); %>
<title><%= accessType.equals("login") ? "Login" : "Registration" %> page</title>
</head>
<body>
	<%@include file ="view/header.jsp" %>
	
	<section class = "main_section"> 
		<div class="error_container"><% @SuppressWarnings("unchecked")
								Collection<String> errors = (Collection<String>) request.getAttribute("errors");
								if (errors != null)
									for (String error : errors) {%>
			<div class="error"><%= error %></div>			
			<%} %>			
		</div>
		<form action = "${ctxPath}/access" method="POST" id="access_form">
			<input type = "hidden" name = "action" value = "<%=accessType%>">
			<fieldset>
				<legend><%= accessType.equals("login") ? "Login" : "Registration" %></legend>
				
				<label for="username">Nome Utente: </label>
				<input type = "text" id = "username" name = "username" placeholder = "pluto2000" autofocus="autofocus"
				pattern="^[a-zA-Z0-9_]{3,16}$" title="Please enter a username of 3 to 16 charachters with only alphanumeric characthers and underscores" required></input>
				<div class="username_error"></div>
				<br>
				<label for="password">Password: </label>
				<input type = "password" id = "password" name = "password"
				pattern="^[a-zA-Z0-9._]{6,20}$" required></input>
				<div class="password_error"></div>
				<% if (accessType.equals("register")) { %>
				<label for="password_confirm">Confirm Password: </label>
				<input type = "password" id = "password_confirm" name = "password_confirm"
				pattern="^[a-zA-Z0-9._]{6,20}$" required></input>
				<% } %>
				<br>
			</fieldset>
			
			<!--  <input type="submit" id="submitButton" value = "Conferma"/>
			<input type="reset" value = "Reset">-->
			<button type="submit" id="submit_button">Confirm</button>
			<button type="reset">Reset</button>
		
		</form>
	
	</section>

	
	<%@include file="/view/footer.jsp" %>
	<script src="${ctxPath}/scripts/forms.js"></script>
</body>
</html>

<% /* 				<label for="email">E-Mail: </label>
				<input type = "email" id = "email" name = "email" placeholder = "pippo@topolino.it" autofocus="autofocus"></input>
				<br> */ %>