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
	



	
	
	
});