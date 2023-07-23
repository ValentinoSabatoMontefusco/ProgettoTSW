<%@page import="java.util.*, com.code_fanatic.model.bean.*"%>
<aside class="userbar">
	<div class="cart_container">
	<a href="${ctxPath}/user/myCart"><img src="${ctxPath}/images/cart.png" id="cart_icon" alt="cart icon">
	<button id ="cart_span">
		<% Cartesio footerCart = (Cartesio)  request.getSession().getAttribute("cart"); %>
			<%= footerCart == null ? 0 : footerCart.getTotalQuantity() %></button>
	</a>
	</div>
	<% String footerRole = (String) request.getSession().getAttribute("role");
	if (footerRole == null) { %>
	<ul>
		<li><a href="${ctxPath}/access.jsp?type=login" class="hoverable">Log in</a></li>
		<li><a href="${ctxPath}/access.jsp?type=register" class="hoverable">Register</a></li>
	</ul>
	<%} else { %>
	<ul>
		<li><a href= "${ctxPath}/user/myaccount.jsp" class="hoverable">My Account</a></li>
		<li><a href= "${ctxPath}/logout" class="hoverable">Log Out</a></li>
		<% if (footerRole.equals("admin")) { %>
		<li><a href= "${ctxPath}/admin/overview.jsp" id ="admin_anchor" class="hoverable">Admin Section</a><li>
		<%} %>
	</ul>
	
	<%} %>
	<div id="shop"><a href="${ctxPath}/shop" class="hoverable"><img src="${ctxPath}/images/shop.png" class="hoverable" id="shop_icon" alt="shop icon"><br>Browse our shop for a full display of our products!</a></div>
</aside>

</div>
<footer>
	<img src="${ctxPath}/images/fnc_black.png" id = "fnc_footer" alt = "Fnatic Black Logo">
	<div>This site was made in 2023 for the TSW exam by Valentino Sabato Montefusco</div>
	
</footer>



<script src = "${ctxPath}/scripts/general.js"></script>