$(document).ready(function() {
	
	
	
	
	trimDescriptions();


});

function createProductBlock(JSONProduct, type) {
	
	var product = JSON.parse(JSONProduct);
	
	var productLogo = document.createElement("img");
	productLogo.className = "product_logo";
	productLogo.src = "/Code_Fanatic/images/logos/" + product.name.toLowerCase().replace(/\s/g, '') + ".png"
	
	var productName = document.createElement("div");
	productName.className = "product_name";
	productName.innerText = product.name;
	
	var productDescription = document.createElement("div");
	productDescription.className = "product_description";
	productDescription.innerText = product.description;
	
	var productPrice = document.createElement("div");
	productPrice.className = "product_price";
	productPrice.innerText = product.price + " $";
	
	var productButton = document.createElement("button");
	productButton.className = "product_button";
	productButton.innerText = "Add to Cart";
	productButton.addEventListener("click", function() {
		updateCart(product.id, "Add");
	});
	var productBlock = document.createElement("div");
	productBlock.className = "product_block";
	productBlock.appendChild(productLogo);
	productBlock.appendChild(productName);
	productBlock.appendChild(productDescription);
	productBlock.appendChild(productPrice);
	productBlock.appendChild(productButton);
	
	console.log(type.toLowerCase() + "_container");
	document.getElementById(type.toLowerCase() + "_container").appendChild(productBlock);
	
}

