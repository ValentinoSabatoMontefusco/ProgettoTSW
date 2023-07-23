	$(document).ready(function() {
		
		
		let comments_container = document.getElementById("comments_container");
	
	comments_container.addEventListener("click", function(event) {
		
		let currentUrl = window.location.pathname;
		let servletTarget;
		if (currentUrl.includes("user"))
			servletTarget = "user/comment";
		else
			servletTarget= "moderation";
		
		
		let xhr = $.post({url: servletTarget, data: {type: "Delete", comment_id: event.target.dataset.id}});
		
		xhr.always(function() {
			
			location.reload();
		})
		
	});
		
		
	});
	
	
	