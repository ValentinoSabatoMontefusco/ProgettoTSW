$(document).ready(function() {
	
	let productBlocks = document.getElementsByClassName("product_block");
	
	let quantity;
	let totalPrice = 0;

	let buttonAdd;
	let buttonSub;
	let productID;
	let productPrice;
	let quantityBlock;

	

	for (const productBlock of productBlocks) {
		
		quantity = parseInt(productBlock.querySelector(".quantity").textContent);

		
		buttonAdd  = productBlock.querySelector(".product_buttonAdd");
		buttonSub  = productBlock.querySelector(".product_buttonSub");
		
		quantityBlock = productBlock.querySelector(".quantity_block");
		console.log("Quantity Block mi penzo dovresbe esistere: " + quantityBlock);
		
		
		productID = productBlock.querySelector(".product_id").value;
		productPrice = parseFloat(productBlock.querySelector(".product_price").textContent);
		
		console.log("Test variabili -> quantity = " + quantity + ", productID = " + productID + ", productPrice = " + productPrice);
		
		totalPrice += (quantity * productPrice);
		
		// LISTENERS FOR +/- BUTTONS
		
		buttonAdd.addEventListener("click", function(event){
			
			let thisProductBlock = event.currentTarget.closest(".product_block");
			let thisPrID = thisProductBlock.querySelector(".product_id").value;

			let response = updateCart(thisPrID, "Add", this);
			response.done(function(response){
				
				updateProductBlock(thisProductBlock, response.productQuantity, 1);
			})
		});
		
		buttonSub.addEventListener("click", function(event) {
			
			let thisProductBlock = event.currentTarget.closest(".product_block");
			let thisPrID = thisProductBlock.querySelector(".product_id").value;
			let response = updateCart(thisPrID, "Sub", this);
			response.done(function(response){
				
				updateProductBlock(thisProductBlock, response.productQuantity, -1);
			})
		})
		
	
		
		
		$(quantityBlock).on("cartUpdated", function(event, cartInfo) {
			
			let amountDiv = this.querySelector(".merch_amount");
			console.log("QuantityBlock listena cart updated? + " + JSON.stringify(cartInfo));
			
			console.log("amountDiv type = " + amountDiv.dataset.productType + " and amount = " + amountDiv.dataset.amount);
			if (amountDiv.dataset.productType == "Merchandise" && amountDiv.dataset.amount - cartInfo.productQuantity <= 5) {
				amountDiv.textContent = "Total Left: " + amountDiv.dataset.amount;
				$(amountDiv).slideDown(600);
				if(amountDiv.dataset.amount == cartInfo.productQuantity)
					this.querySelector(".product_buttonAdd").disabled = true;
				else
					this.querySelector(".product_buttonAdd").disabled = false;
			} else {
				
				$(amountDiv).slideUp(600);
				amountDiv.textContent = "";
			}
			
		})
		
		
		
		
	}
	

	
	// CLEAR / CHECKOUT BUTTONS
	
	$("#empty_cart").on("click", function() {
			
			updateCart(0, "Clear", this).done(function() {
				
			clearCartVista();
			})
		})
		
	$("#buy_now").on("click", function() {
		
		let confirmed = confirm("Are you sure you want to finalize the purchase?");
		
		if (confirmed) {
			
			let jqxhr = updateCart(0, "Checkout", this);
			
			jqxhr.done(function() {
				
				
				clearCartVista();
				alert("Purchase completed successfully! Thanks for your support :)");
				location.reload();
			})
			
			jqxhr.fail(function(jqXHR, textStatus, errorThrown) {
				
				alert("Oops! Something went wrong: " + jqXHR.responseText);
				location.reload();
			})
			
		} else {
			
			alert("Purchase not completed");
		}
	})
	
	
	
	
	$("#total_price").text(totalPrice.toFixed(2));
	if (productBlocks.length == 0)
		$("#buy_now").prop("disabled", true);
		
		
	trimDescriptions();
	
	
	});
	
	
	function updateProductBlock(productBlock, productQuantity, delta) {
		
		console.log("updateProductBlock says productQuantity = " + productQuantity +  " and delta = " + delta);

		
		let totalPrice = parseFloat($("#total_price").text());
		const price = parseFloat(productBlock.querySelector(".product_price").textContent.trim());
		
		if (productQuantity == 0) {
			

			productBlock.remove();
		} else {
			
			let subtotal = parseFloat(productBlock.querySelector(".subtotal_span").textContent.trim());
			console.log("Subtotal = " + subtotal + ", price = " + price + ", newSubtotal = " + (subtotal + (delta*price)));
			productBlock.querySelector(".subtotal_span").textContent = (subtotal + (delta*price)).toFixed(2);
			productBlock.querySelector(".quantity").textContent = productQuantity;
		}
		
		totalPrice += (delta*price);
		
		$("#total_price").text(totalPrice.toFixed(2));
		
		let qt = document.getElementsByClassName("product_block").length;
		if (qt == 0)
			$("#buy_now").prop("disabled", true);
		else
			$("buy_now").prop("disabled", false);
		
	}
	
	function clearCartVista() {
				
				
			$(".product_block").each(function() {
				
				this.remove();
			});
			$("#total_price").text("0.00");
			$("#buy_now").prop("disabled", true);
	}
		
		