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
		
		quantityBlock = productBlocks[i].querySelector(".quantity_block");
		console.log("Quantity Block mi penzo dovresbe esistere: " + quantityBlock);
		
		
		productID = productBlocks[i].querySelector(".product_id").value;
		productPrice = parseFloat(productBlocks[i].querySelector(".product_price").textContent);
		
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
		
		var amountDiv = this.querySelector(".merch_amount");
		
		
		$(quantityBlock).on("cartUpdated", function(event, cartInfo) {
			
			console.log("QuantityBlock listena cart updated? + " + cartInfo + event.detail);
			
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
		
		var confirmed = confirm("Are you sure you want to finalize the purchase?");
		
		if (confirmed) {
			
			var jqxhr = updateCart(0, "Checkout", this);
			
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
		//console.log(productBlock.innerHTML);
		
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
		
		let qt = document.getElementsByClassName("product_block").length;
		if (qt == 0)
			$("#buy_now").prop("disabled", true);
		else
			$("buy_now").prop("disabled", false);
		
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
			$("#buy_now").prop("disabled", true);
	}
		
		