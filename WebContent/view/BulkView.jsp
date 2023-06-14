<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%--     pageEncoding="ISO-8859-1"%> --%>
<%@page import="org.apache.catalina.core.ApplicationContext"%>
<%@page import="java.util.*, com.code_fanatic.model.bean.*"%>
<% 	@SuppressWarnings("unchecked")
	Collection<CourseBean> courses = (Collection<CourseBean>) getServletContext().getAttribute("courses");
	if (courses == null) {
		response.sendRedirect( getServletContext().getContextPath() + "/courses");
		return;
	}	%>
	
<header class = "page_header">

		<div id="title"><a href="${ctxPath}/home.jsp"><img id = "fnc_logo" src="${ctxPath}/images/fnatic_logo.png" alt="Fnatic logo"/></a>CODE FANATICS</div>
</header>

<div class = "subheader" align="center">The best site to learn how to code... at a small price! ;)</div>

<aside class="search" id="homesearch">
	<!-- <button id = "sidebar_toggle">&lt;&lt;&lt;</button>*/ -->	
	<div>Select one of our many courses!</div>
	<br><br>
	<label>Search:</label>
	<input type="text" placeholder="Type some programming language here..." name="searchbar" id="searchbar"></input>
	<br>
	<ul id="lang_list">
	<% 	Iterator<CourseBean> iterator = courses.iterator();
		CourseBean currentCourse;
		while (iterator.hasNext()) {
			currentCourse = (CourseBean) iterator.next();
		%>
		<li><a href="${ctxPath}/courses?name=<%=currentCourse.getName()%>"><%= currentCourse.getName()%></a>
		<%} %>
	</ul>
	
</aside>
<aside class="userbar">
	<a href="${ctxPath}/user/myCart"><img src="${ctxPath}/images/cart.png" id="cart_icon" alt="cart icon">
	<button id ="cart_span">
		<%	Cartesio cart = (Cartesio) request.getSession().getAttribute("cart");%>
			<%= cart == null ? 0 : cart.getTotalQuantity() %></button>
	
	</a>
	<% if (request.getSession().getAttribute("role") == null) { %>
	<ul>
		<li><a href="${ctxPath}/access.jsp?type=login">Log in</a></li>
		<li><a href="${ctxPath}/access.jsp?type=register">Register</a></li>
	</ul>
	<%} else { %>
	<ul>
		<li><a href= "${ctxPath}/user/myaccount.jsp">My Account</a></li>
		<li><a href= "${ctxPath}/logout">Log Out</a></li>
	</ul>
	
	<%} %>
	<div id="shop"><a href="${ctxPath}/shop"><img src="${ctxPath}/images/shop.png" id="shop_icon" alt="shop icon"><br>Browse our shop for a full display of our products!</a></div>
</aside>
<!--  Script to dinamically display the languages list  -->
<!-- <script> -->
<script src = "${ctxPath}/scripts/general.js"></script>

