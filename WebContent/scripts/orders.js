$(document).ready(function() {
	
	
	//retrieveOrders(null, "user_username")
	
	
});


function retrieveOrders(username, sort) {
	
	return $.post({url: contextPath + "/admin/ordersRecap", data: {username:username, sort:sort}, success:
	function(response) {
		
		console.log(response);
	}, dataType: "json"});
	
	
	
}