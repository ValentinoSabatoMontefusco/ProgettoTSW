// Regular expressions for username, email, and password validation

const usernameRegex = /^[a-zA-Z0-9._]+$/;
const passwordRegex = /^[a-zA-Z0-9._]+$/;

$(document).ready(function() {
	
	const form = document.getElementsByTagName("form")[0];
	

	
	form.addEventListener("focusout", function(event) {
		
		if (!event.target.checkValidity()) {
			event.target.classList.add("invalid");
		} else {
			event.target.classList.remove("invalid");
		}
		
		

	})
		
	
})



 
