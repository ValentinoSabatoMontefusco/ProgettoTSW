$(document).ready(function() {
	
	var productBlocks = document.getElementsByClassName("product_block");
	
	var quantity;
	var totalPrice = 0;

	var buttonAdd;
	var buttonSub;
	var productID;
	var productPrice;

	
	for (let i = 0; i < productBlocks.length; i++) {
		
		quantity = parseInt(productBlocks[i].querySelector(".quantity").textContent);

		
		buttonAdd  = productBlocks[i].querySelector(".product_buttonAdd");
		buttonSub  = productBlocks[i].querySelector(".product_buttonSub");
		
		productID = productBlocks[i].querySelector(".product_id").value;
		productPrice = parseFloat(productBlocks[i].querySelector(".product_price").textContent);
		
		console.log("Test variabili -> quantity = " + quantity + ", productID = " + productID + ", productPrice = " + productPrice);
		
		totalPrice += (quantity * productPrice);
		
		// LISTENERS FOR +/- BUTTONS
		
		buttonAdd.addEventListener("click", function(event){
			
			let thisProductBlock = event.currentTarget.closest(".product_block");
			let thisPrID = thisProductBlock.querySelector(".product_id").value;

			let response = updateCart(thisPrID, "Add");
			response.done(function(response){
				
				updateProductBlock(thisProductBlock, response.productQuantity, 1);
			})
		});
		
		buttonSub.addEventListener("click", function(event) {
			
			let thisProductBlock = event.currentTarget.closest(".product_block");
			let thisPrID = thisProductBlock.querySelector(".product_id").value;
			let response = updateCart(thisPrID, "Sub");
			response.done(function(response){
				
				updateProductBlock(thisProductBlock, response.productQuantity, -1);
			})
		})
		
		
		
		
		
		
	}
	
	// CLEAR / CHECKOUT BUTTONS
	
	$("#empty_cart").on("click", function() {
			
			updateCart(0, "Clear").done(function() {
				
			clearCartVista();
			})
		})
		
		$("#buy_now").on("click", function() {
			
			var confirmed = confirm("Are you sure you want to finalize the purchase?");
			
			if (confirmed) {
				
				var jqxhr = updateCart(0, "Checkout");
				
				jqxhr.done(function() {
					
					
					clearCartVista();
					alert("Purchase completed successfully! Thanks for your support :)");
				})
				
				jqxhr.fail(function() {
					
					alert("Oops! Something went wrong!");
				})
				
			} else {
				
				alert("Purchase not completed");
			}
		})
	
	
	
	$("#total_price").text(totalPrice.toFixed(2));
	trimDescriptions();
	
	
	});
	
	
	function updateProductBlock(productBlock, productQuantity, delta) {
		
		console.log("updateProductBlock says productQuantity = " + productQuantity +  " and delta = " + delta);
		console.log(productBlock.innerHTML);
		
		var totalPrice = parseFloat($("#total_price").text());
		const price = parseFloat(productBlock.querySelector(".product_price").textContent.trim());
		
		if (productQuantity == 0) {
			
			//$(".product_container").removeChild(productBlock);
			productBlock.remove();
		} else {
			
			const subtotal = parseFloat(productBlock.querySelector(".product_subtotal").textContent.trim());
			productBlock.querySelector(".product_subtotal").textContent = (subtotal + (delta*price)).toFixed(2) + " $";
			productBlock.querySelector(".quantity").textContent = productQuantity;
		}
		
		totalPrice += (delta*price);
		
		$("#total_price").text(totalPrice.toFixed(2));
		
	}
	
	function clearCartVista() {
		
//			let productBlocks = document.getElementsByClassName("product_block");
//				for (let i = 0; productBlocks.length; i++)
//					productBlocks[i].remove();
//					
//				$("#total_price").text("0.00");
				
				
			$(".product_block").each(function() {
				
				this.remove();
			});
			$("#total_price").text("0.00");
	}
		
		