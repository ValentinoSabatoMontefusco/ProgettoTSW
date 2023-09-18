<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
	<link rel="stylesheet" href="${ctxPath}/styles/general.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/mycourses.css"/>
	<title>My Courses</title>
</head>
<body>
	<%@include file="/view/header.jsp" %>
	
	<section class="main_section">
		<div class="navigation_div"><a href = "${ctxPath}/user/myaccount.jsp">My Account</a> > My Courses</div>
		
		<br>
		
		<h1>My Courses</h1>
		
		<%	@SuppressWarnings("unchecked")
			Collection<CourseBean> myCourses = (Collection<CourseBean>) request.getAttribute("courses");
		
			if (courses != null) {
				for (CourseBean course : myCourses) {
					
					%>
					
					<div class="course_container">
						<a href="${ctxPath}/courses?name=<%= course.getName() %>">
						<img class ="course_logo" src="${ctxPath}/images/logos/<%= course.getName().toLowerCase().replaceAll("\\s", "") %>.png" alt="course logo">
						</a>
						<a href="${ctxPath}/courses?name=<%= course.getName() %>" class="course_name">
						<%= course.getName() %></a>
						
					</div>
					
					
					
				<% }}%>
				
		</section>
		
		<%@include file="/view/footer.jsp" %>
</body>
</html>