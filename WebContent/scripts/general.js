$(document).ready(function() {
	
	
	
	$(document).on("cartUpdated", updateCartSpan);
	
	
	function updateCartSpan(event, cartInfo) {
		
		$('#cart_span').text(cartInfo.cartQuantity);
	}
});

	let contextPath = "/" +  window.location.pathname.split('/')[1];
	
	function updateCart(productID, type) {
		

		return $.get({url: contextPath + "/ajaxCart", data: {prodID: productID, type: type},  success: function(response) {
			
			$(document).trigger('cartUpdated', response);
			console.log("Cart Quantity = " + response.cartQuantity);

		}, dataType: "json"});
		
		
	}
	
	function trimDescriptions() {
		
		const descriptions = document.getElementsByClassName("product_description");
	
	for (let i = 0; i < descriptions.length; i++) {
		
		if (descriptions[i].innerText.length > 100) {
			
			descriptions[i].innerText = descriptions[i].innerText.substring(0,100) + "...";
		}
	}
	
	}