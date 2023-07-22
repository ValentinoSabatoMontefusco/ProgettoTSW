$(document).ready(function() {
	
	const product = JSON.parse(JSONProduct);
	$(".product_edit").on("click", function(event) {
		
		fakePostRequest(event, /*contextPath +*/ "admin/productEdit?name=" + product.name, "product_id", product.id, "type", "retrieve");
		
	});
	
	$(".product_delete").on("click", function(event) {
		
		let confirmed = confirm("Are you sure you want to delete this product? The decision cannot be reversed");
		
		if (confirmed) {
			
			alert("The product shall be deleted");
			fakePostRequest(event, "admin/productEdit?name=" + product.name, "product_id", product.id, "type", "delete");
		} else {
			
			alert("The product hasn't been deleted");
		}
	});
	
	document.getElementById("submit_comment").addEventListener("click", function(event) {
		
		event.preventDefault();
		let type = document.getElementById("comment_type").value;
		let product_id = document.getElementById("comment_pid").value;
		let username = document.getElementById("comment_username").value;
		let content = document.getElementById("content_input").value;
		
		let xhr =$.post({url: "user/comment", data: {type: type, product_id: product_id, user_username: username, content_input: content}});
		
		xhr.always(function () {
			
			location.reload();
		});
		
	});


	
	
	
});