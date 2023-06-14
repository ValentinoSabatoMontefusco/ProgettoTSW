// Regular expressions for username, email, and password validation

const usernameRegex = /^[a-zA-Z0-9._]+$/;
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const passwordRegex = /^[a-zA-Z0-9._]+$/;

// Get the input fields and submit button

const usernameInput = document.getElementById('username');
//const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const submitButton = document.getElementById('submitButton');

// Add event listeners to the input fields

usernameInput.addEventListener('input', validateForm);
//emailInput.addEventListener('input', validateForm);
passwordInput.addEventListener('input', validateForm);

function validateForm() {
  const usernameValid = checkField(usernameInput, usernameRegex); 
  //const emailValid = checkField(emailInput, emailRegex);
  const passwordValid = checkField(passwordInput, passwordRegex);

  // Enable or disable the submit button based on the validation results
  submitButton.disabled = !(usernameValid && /*emailValid &&*/ passwordValid);
}

function checkField(inputField, inputRegex) {
	
	if(inputRegex.test(inputField.value)) {
	  inputField.classList.remove("invalid");
	  return true;
	} else {
		inputField.classList.add("invalid");
		return false;
	}

 }
