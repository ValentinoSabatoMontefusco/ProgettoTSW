<section class="comment_section">
			
			<%	String commentsFormRole = (String) request.getSession().getAttribute("role");
			
			if (commentsFormRole != null && commentsFormRole.equals("user")) { %>
			<form>
			
				<input type ="hidden" name="type" id="comment_type" value="Add">
				<input type="hidden" name="product_id" id = "comment_pid" value="${product.id}">
				<input type="hidden" name="product_name" value ="${product.name}">
				<input type="hidden" name="user_username" id="comment_username" value="<%= (String) request.getSession().getAttribute("username") %>">
				
				
				
				<label for="content_input">Sezione commenti: </label>
				<textarea id = "content_input" name="content_input" placeholder="Inserisci il tuo feedback qui :)"
				 rows = "4" cols ="50" required></textarea>
				
				<button type="submit" id="submit_comment">Invia Commento</button>
			</form>
			<br>
			
			<%} %>