$(document).ready(function() {
	
	
	
	$(document).on("cartUpdated", updateCartSpan);
	
	
	function updateCartSpan(event, cartInfo) {
		
		$('#cart_span').text(cartInfo.cartQuantity);
	}
});

	var contextPath = "/" +  window.location.pathname.split('/')[1];
	
	function updateCart(productID, type, source) {
		

		return $.get({url: contextPath + "/ajaxCart", data: {prodID: productID, type: type},  success: function(response) {
			
			$(source).trigger('cartUpdated', response);
			console.log("Cart Quantity = " + response.cartQuantity);

		}, dataType: "json"});
		
		
	}
	
	function trimDescriptions() {
		
		const descriptions = document.getElementsByClassName("product_description");
		
		for (const element of descriptions) {
			
			if(element.innerText.length > 100) {
				element.innerText = element.innerText.substring(0, 100) + "...";
			}
		}
	
	}
	
	function fakePostRequest(event, href, ...hiddenParams) {
	
	event.preventDefault();
	
	const form = document.createElement("form");
	form.method = "POST";
	form.action = href;
	for (let i = 0; i < hiddenParams.length; i += 2) {
		
		const param = document.createElement("input");
		param.type = "hidden";
		param.name = hiddenParams[i];
		param.value = hiddenParams[i+1];
		form.appendChild(param);
	}
	
	
	document.body.appendChild(form);
	console.log(hiddenParams);
	form.submit();
	
	
	
}
