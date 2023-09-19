	$(document).ready(function() {
		
		
		let comments_container = document.getElementById("comments_container");
	
	comments_container.addEventListener("click", function(event) {
		
		if (event.target.tagName === 'BUTTON') {
			let currentUrl = window.location.pathname;
			let servletTarget;
			if (currentUrl.includes("admin"))
				
				servletTarget= "moderation";
			else
				servletTarget = "user/comment";
			
			
			let xhr = $.post({url: servletTarget, data: {type: "Delete", comment_id: event.target.dataset.id}});
			
			xhr.always(function() {
				
				location.reload();
			})
		}
		
		
	});
		
		
		document.getElementById("submit_comment").addEventListener("click", function(event) {
		
		event.preventDefault();
		let error = $(".error:first");
		let type = document.getElementById("comment_type").value;
		let product_id = document.getElementById("comment_pid").value;
		let username = document.getElementById("comment_username").value;
		let content = document.getElementById("content_input").value;
		
		if (content == "") {
			error.text("You cannot send empty comments!");
			return;
		}
		error.text("");
		
		let xhr =$.post({url: "user/comment", data: {type: type, product_id: product_id, user_username: username, content_input: content}});
		
		xhr.always(function () {
			
			location.reload();
		});
		
	});
});
	
	
	