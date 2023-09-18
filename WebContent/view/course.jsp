<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.code_fanatic.model.bean.*" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<script src="${ctxPath}/scripts/jquery-3.6.3.js"></script>
		<script src="${ctxPath}/scripts/general.js"></script>
		<link rel="stylesheet" href = "${ctxPath}/styles/general.css" />
		<link rel="stylesheet" href = "${ctxPath}/styles/Course.css" />
		
		<jsp:useBean id = "course" class = "com.code_fanatic.model.bean.CourseBean" scope = "request"/>
		<title>${course.name} Course</title>
	</head>
	<body>
		
		<%@include file ="/view/header.jsp" %>
		<section class = "main_section">
			<h1>Welcome to our ${course.name} Course!</h1>
			<img src="${ctxPath}/images/logos/<%= course.getName().toLowerCase() %>.png" id="course_logo" alt="course logo">
			<div>${course.description}</div>
			<br>
			<%	
				List<LessonBean> lessons = course.getLessons();
				boolean isOwned = (boolean) request.getAttribute("isOwned");
					
					
					for (LessonBean lesson : lessons) { %>
					
					<div class="lesson">
					
						<h1>Lesson <%= lesson.getNumber() %> ~ <%= lesson.getTitle() %></h1>
						<p><%= lesson.getContent() %></p>
					
					</div>
					<br>
					
						
				<% 	if (!isOwned) break;}
					
					
					%>
				<br>
				
				<% ProductBean product = (ProductBean) course;
				request.setAttribute("product", product); %>
				<%@include file="comments_form.jsp" %>
				<%@include file="comments.jsp" %>
				
			</section>
		</section>
		<%@include file ="/view/footer.jsp" %>
	</body>
</html>