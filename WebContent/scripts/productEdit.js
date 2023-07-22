

$(document).ready(function() {
	
	// CREAZIONE PARTE FORM DIPENDENTE DAL TIPO VARIABILE
	
	console.log(product);
	
	let insertBefore = document.getElementById("product_id");
	let parent = document.getElementById("product_fieldset");
	
	if (isEdit)
		switch(product.type) {
			
			case "Merchandise": parent.insertBefore(createMerchandiseBlock(product), insertBefore);
								break;
								
			case "Course": parent.insertBefore(createCourseBlock(product), insertBefore);
							break;
							
			default: break;
			
		} else {
		
		let select = document.getElementById("type_input");
		parent.insertBefore(createCourseBlock(), insertBefore);
		
		select.addEventListener("input", function(event) {
			
			if ($("#specifics_block").length > 0 ) {
				console.log("Specifics Block rilevato esistente");
				parent.removeChild(document.getElementById("specifics_block"));
			}
				
			
			switch (select.value) {
				
				case "Merchandise": parent.insertBefore(createMerchandiseBlock(), insertBefore);
									break;
									
				case "Course": parent.insertBefore(createCourseBlock(), insertBefore);
								break;
				
				default: break;
			
			}
		});
	}
	
	
	// CHECKING FOR NAME CHANGE WITHOUT IMAGE LOAD
	
	const logoInput  = document.getElementById("logo_input");
	const startingName = document.getElementById("name_input").value;
	console.log("Nome iniziale prodotto: " + startingName + " / " + product.name);
	
	if (isEdit) {
		
		document.getElementById("submit_button").addEventListener("click", function(event) {
		
		if (logoInput.files.length <= 0 && document.getElementById("name_input").value) {
			
			let oldnameInput = document.createElement("input");
			oldnameInput.type = "hidden";
			oldnameInput.name = "oldname_input";
			oldnameInput.value = startingName;
			
			console.log(oldnameInput.innerHTML);
			
			parent.appendChild(oldnameInput);
		}
	
	});
	
		
	}
	
	
	
});


function createMerchandiseBlock(product) {
	
	let specificsBlock = document.createElement("div");
	specificsBlock.id = "specifics_block";
	
	let amountInput = document.createElement("input");
	amountInput.type = "number";
	amountInput.id = "amount_input";
	amountInput.name = "amount_input";
	amountInput.step = 1;
	amountInput.setAttribute ("min", "0");
	if (isEdit)
		amountInput.value = product.amount;
	else
		amountInput.value = 5;
	
	let amountLabel = document.createElement("label");
	amountLabel.setAttribute("for", "amount_input");
	amountLabel.textContent = "Amount: ";
	
	specificsBlock.appendChild(amountLabel);
	specificsBlock.appendChild(amountInput);
	
	return specificsBlock;
		
}

function createCourseBlock(product) {
	
	let specificsBlock = document.createElement("div");
	specificsBlock.id = "specifics_block";
	
	let lescountInput = document.createElement("input");
	lescountInput.type = "number";
	lescountInput.id = "lescount_input";
	lescountInput.name = "lescount_input";
	lescountInput.step = 1;
	lescountInput.setAttribute ("min", "1");
	lescountInput.setAttribute("max", "30");
	if (isEdit) 
		lescountInput.value = product.lesson_count;
	else
		lescountInput.value = 3;
	
	let lescountLabel = document.createElement("label");
	lescountLabel.setAttribute("for", "lescount_input");
	lescountLabel.textContent = "Lesson Count: ";
	
	specificsBlock.appendChild(lescountLabel);
	specificsBlock.appendChild(lescountInput);
	
	return specificsBlock;
		
}