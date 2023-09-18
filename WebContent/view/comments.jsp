<%@page import="com.code_fanatic.model.bean.*, java.util.Collection" %> 
<div id="comments_container">
		<% 	@SuppressWarnings("unchecked")
			Collection<CommentBean> comments = (Collection<CommentBean>) request.getAttribute("comments");
			String commentsRole = (String) request.getSession().getAttribute("role");
			String commentsUsername = (String) request.getSession().getAttribute("username");
		
			if (comments != null && comments.size() > 0) {
				for (CommentBean comment : comments) { %>
				
				<div class ="comment_container">
					
					<div class="comment_user"><%= comment.getUser_username() %></div>
					<div class="comment_date"><%= comment.getCreate_time() %></div>
					<br>
					<div class="comment_content"><%= comment.getContent() %></div>
					
					<%if ((commentsRole != null && commentsRole.equals("admin")) || (commentsUsername != null && commentsUsername.equals(comment.getUser_username()))) { %>
					<button class="delete_comment" data-id="<%= comment.getId() %>">Delete Comment</button>
					<%} %>
				</div>
				<br>
			<%}} 
			else { %>
					
				<div class="comment_warning">No comment for this product</div>
					
			<% } %>
		
		<script src="${ctxPath}/scripts/comments.js"></script>
</div>
		
