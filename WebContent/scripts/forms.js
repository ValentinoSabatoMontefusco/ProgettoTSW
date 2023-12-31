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
	
	let pwd = document.getElementById("password");
	let pwdConfirm = document.getElementById("password_confirm");
	
	if (pwd != null && pwdConfirm != null) {
		
		document.getElementById("submit_button").addEventListener("click", function(event){
			
			if (pwd.innerText != pwdConfirm.innerText) {
				
				event.preventDefault();
				let errors = document.getElementsByClassName("error_container");
				errors[0].textContent = "Passwords don't match!"
			}
		})
	}
		
	
})



 
