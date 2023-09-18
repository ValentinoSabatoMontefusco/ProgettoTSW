<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%--     pageEncoding="ISO-8859-1"%> --%>
<%@page import="org.apache.catalina.core.ApplicationContext"%>
<%@page import="java.util.*, com.code_fanatic.model.bean.*"%>
<% 	@SuppressWarnings("unchecked")
	Collection<CourseBean> courses = (Collection<CourseBean>) getServletContext().getAttribute("courses");
	if (courses == null) {
		response.sendRedirect( getServletContext().getContextPath() + "/courses");
		return;
	}
	Cartesio cart = (Cartesio) request.getSession().getAttribute("cart");
	%>
	
<header class = "page_header">

		<div id="title" class="hoverable"><a href="${ctxPath}/home.jsp"><img id = "fnc_logo" class="hoverable" src="${ctxPath}/images/fnatic_logo.png" alt="Fnatic logo"/></a>CODE FANATICS</div>
</header>

<div class = "subheader">The best site to learn how to code... at a small price! ;)</div>

<div class="main_container">

<aside class="search" id="homesearch">
	<div class="intro">Select one of our many courses!</div>
	
	<br>
	<ul id="lang_list">
	<% 	Iterator<CourseBean> iterator = courses.iterator();
		Set<Integer> productsOwned = (Set<Integer>) request.getSession().getAttribute("productsOwned");
		CourseBean currentCourse;
		while (iterator.hasNext()) {
			currentCourse = (CourseBean) iterator.next();
			if (productsOwned != null && productsOwned.contains(currentCourse.getId())) { %>
				<li><a href="${ctxPath}/courses?name=<%=currentCourse.getName()%>" class="owned hoverable"><%= currentCourse.getName()%></a>
				<% } else { %>

		<li><a href="${ctxPath}/courses?name=<%=currentCourse.getName()%>" class="hoverable"><%= currentCourse.getName()%></a>
		<%}} %>
	</ul>
	
</aside>