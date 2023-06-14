<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> <%= request.getParameter("lang") %> Course</title>
</head>
<body>
	<h1>Welcome to our <%= "beloved " + request.getParameter("lang") %> Course!</h1>
</body>
</html>