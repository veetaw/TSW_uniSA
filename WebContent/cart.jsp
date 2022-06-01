<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<%@ page contentType="text/html; 
	charset=UTF-8" import="java.util.*,model.VinoBean, model.Cart"%>
	
	<head>
	    <title>Carrello</title>
	
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">
	    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
	    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
	    <link rel="stylesheet" href="css/main.css">
		<script
			src="https://code.jquery.com/jquery-3.6.0.min.js"
			integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			crossorigin="anonymous"></script>
			
		<script src="js/cart.js" type="javascript"></script>

		<script type="text/javascript">
		console.log("fessa gialla");
		$(document).ready(function() {
			$.ajax({
				url: 'cart',
				type: 'POST',
				data: jQuery.param({ action: "list"}) ,
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					console.log(response);
				},
				error: function () {
					alert("error");
				}
			});
		});
	</script>	
	</head>
	
	<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust">
		    <div class="mdc-layout-grid">
                <span class="mdc-typography--headline6">
                    Carrello
	            </span>
	        <div class="mdc-layout-grid__inner">
	        	
	        </div>
		
		</main>
		
		<%@ include file="include/footer.jsp" %>
	</body>
</html>
