$(document).ready(function() {
	
	
	
	if ($(".product_new") != undefined) 
		$(".product_new").on("click", function(event) {
			
			fakePostRequest(event, "productCreate", "product_id", -1, "type", "retrieve");
		});
	trimDescriptions();


});


function createProductBlock(JSONProduct, type, admin, inCart) {
	
	const product = JSON.parse(JSONProduct);
	
	if (testCounter++ == 0)
		console.log(JSONProduct);
	
	
	let productLogo = document.createElement("a");
	productLogo.href = contextPath + "/product?name=" + product.name;
	productLogo.addEventListener("click", function(event) {
		event.preventDefault();
		fakePostRequest(event, productLogo.href, "product_id", product.id, "type", "retrieve")});
	let productImage = document.createElement("img");
	productImage.className = "product_logo hoverable";
	productImage.src = "/Code_Fanatic/images/logos/" + product.name.toLowerCase().replace(/\s/g, '') + ".png"
	productLogo.appendChild(productImage);
	
	let productName = document.createElement("a");
	productName.className = "product_name hoverable";
	productName.innerText = product.name;
	productName.href = contextPath + "/product?name=" +  product.name;
	productName.addEventListener("click", function(event) {
		event.preventDefault();
		fakePostRequest(event, productName.href,  "product_id", product.id, "type", "retrieve")});
	
	let productDescription = document.createElement("div");
	productDescription.className = "product_description";
	productDescription.innerText = product.description;
	
	let productPrice = document.createElement("div");
	productPrice.className = "product_price";
	productPrice.innerText = product.price + " $";
	
	
	
	let productBlock = document.createElement("div");
	productBlock.className = "product_block";
	productBlock.setAttribute("data-product-type", product.type);
	if (product.type === "Merchandise")
		productBlock.setAttribute("data-amount", product.amount);
	productBlock.appendChild(productLogo);
	productBlock.appendChild(productName);
	productBlock.appendChild(productDescription);
	productBlock.appendChild(productPrice);
	if (admin === false) {
		let productButton = createProductButton(product, inCart);
		productBlock.appendChild(productButton);
	}
		
	
	console.log(type.toLowerCase() + "_container");
	document.getElementById(type.toLowerCase() + "_container").appendChild(productBlock);
	
}

function createProductButton(product, inCart) {
	
	let productButton = document.createElement("button");
		productButton.className = "product_button";
		if (product.type == "Course")
			productButton.innerText = inCart ? "Already in Cart" : "Add to Cart";
		else if (product.type == "Merchandise")
			productButton.innerText = inCart ? "Can't buy more" : "Add to Cart";
		productButton.disabled = inCart;
		productButton.addEventListener("click", function(event) {
			let response = updateCart(product.id, "Add", this);
			let control = this.closest("div.product_block").dataset;
			let thisButton = this;
			response.done(function () {
				
				console.log("ResponseJSON: " + JSON.stringify(response.responseJSON));
				
				if (control.productType === "Course") {
					thisButton.disabled = true;
					thisButton.textContent = "Already in Cart";
				
				} else if (control.productType === "Merchandise") {
				
				
					console.log("Shop.js says the current cart quantity of the item is: " + response.responseJSON.productQuantity + " and " + 
					"the maximum amount is: " + control.amount)
					if(response.responseJSON.productQuantity >= control.amount) {
						thisButton.disabled = true;
						thisButton.textContent = "Can't buy more";
				}
						
			}
				
			})	
	
		});
		
		return productButton;
}

