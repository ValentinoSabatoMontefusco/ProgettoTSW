<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.code_fanatic.model.bean.*" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" href = "${ctxPath}/styles/home.css" />
		<link rel="stylesheet" href = "${ctxPath}/styles/Course.css" />
		
		<jsp:useBean id = "course" class = "com.code_fanatic.model.bean.CourseBean" scope = "request"/>
		<title> <!--<jsp:getProperty name="course" property = "name" />--> ${course.name} Course</title>
	</head>
	<body>
		
		<%@include file ="BulkView.jsp" %>
		<section class = "main_section">
			<h1>Welcome to our <!--<jsp:getProperty name="course" property = "name" />--> ${course.name} Course!</h1>
			<img src="${ctxPath}/images/logos/<%= course.getName().toLowerCase() %>.png" id="course_logo" alt="course logo">
			<p><!--<jsp:getProperty name="course" property = "description" />--> ${course.description}</p>
			<br>
			<%	
				Collection<LessonBean> lessons = course.getLessons();
				boolean isOwned = (boolean) request.getAttribute("isOwned");
					
					
					for (LessonBean lesson : lessons) { %>
					
					<div class="lesson">
					
						<h1>Lesson <%= lesson.getNumber() %> ~ <%= lesson.getTitle() %></h1>
						<p><%= lesson.getContent() %></p>
					
					</div>
					<br>
					
						
				<% 	if (!isOwned) break;}
					
					
					%>
				}
				
		</section>
		
	</body>
</html>