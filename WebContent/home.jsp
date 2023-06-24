<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<script src="scripts/jquery-3.6.3.js"></script>
		<link rel="stylesheet" href="${ctxPath}/styles/home.css"/>
		<title>Code Fanatic Homepage</title>
	

	</head>
	<body>
	
		<%@include file = "/view/BulkView.jsp"%>

		<section class = "main_section">
			<div id = "main">Start by <a href="korriban/selecct.html?lang=yuppi">selecting</a> one of our courses <br>and seeing what we have to offer!</div>
			
		
		</section>
		
	</body>
	</html>
	

























			<!--  Script for toggling the sidebar visibility -->
		<!--  <script> 
			const sidebar = document.getElementById("homesearch");
			const sidebar_toggle = document.getElementById("sidebar_toggle");
			sidebar.style.background = "green";
			const computedStyle = getComputedStyle(sidebar);
			var hasSlode = false;
			sidebar_toggle.addEventListener('click', () => {
				
				if (hasSlode == false) {
					
					$("#homesearch").slideToggle();
					hasSlode == true;
					$("homesearch").
				} else {
					
					$("#homesearch").slideToggle();
					hasSlode == false;
				}
				
		</script>
			})-->