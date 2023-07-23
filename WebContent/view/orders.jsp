<%@ page import="com.code_fanatic.model.bean.*" %>
<%@ page import="java.util.Map.*" import="java.util.*" import="java.text.DecimalFormat" %>

<section>
		<div class="navigation_div"><a href = "${ctxPath}/user/myaccount.jsp">My Account</a> > My Cart</div>
		
		<br>
		
		<h1>My Orders</h1>
		<br><br>
		<div class = "product_container">
		
		<% @SuppressWarnings("unchecked")
			Collection<OrderBean> orders = (Collection<OrderBean>) request.getAttribute("orders");
		   if (orders == null || orders.isEmpty()) {
		   %>
		   <div>You ain't made no orders</div>
		   <%} else {
			   
			   String view = (String) request.getAttribute("view");
		   		Float total;
		   		DecimalFormat format = new DecimalFormat("#0.00");
		   for(OrderBean order : orders) {
		   		total = 0f;
			   
		   		%>
		   
		   <table>
		   <caption>Order made in date: <%= order.getOrder_date() %>
		   		<span class="caption_owner"><%if ("recap".equals(view)) { %> by <%= order.getUser_username() %> <% } %></span></caption>
		   	<tr>
		   		<th>Product</th>
		   		<th>Name</th>
		   		<th>Price</th>
		   		<th>Quantity</th>
		   		<th>Subtotal</th>
		   	</tr>
		   	
		   	<%
		   		
		   		for (Entry<ProductBean, Integer> entry : order.getProducts()) {
		   			
		   			ProductBean product = entry.getKey();
					total += entry.getValue() * product.getPrice();
		   			%>
		   			
		   		<tr>
		   			<td><img src="${ctxPath}/images/logos/<%= product.getName().toLowerCase().replaceAll("\\s", "") %>.png"
		   			 alt = "product logo" width="30px" height="30px"></td>
		   			 <td><%= product.getName() %></td>
		   			 <td><%= product.getPrice() %></td>
		   			 <td><%= entry.getValue() %></td>
		   			 <td><%=  entry.getValue() * product.getPrice() %><td>
		   		</tr>
		   		<% }
		   	%>
				<tr class="total_row">
					<td colspan="3" > Total = <%= format.format(total) %> $</td>

		   
		   <%} %>
		   
		   </table>
		</div>
		<br>
		<%} %>
	</section>