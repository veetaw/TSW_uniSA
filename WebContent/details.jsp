<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
VinoBean product = (VinoBean) request.getAttribute("details");
%>

<!DOCTYPE html>
<html>

	<%@ page contentType="text/html; 
	charset=UTF-8" import="java.util.*,model.VinoBean,model.Cart"%>
	
	<head>
	    <title>Catalogo La casa di Bacco</title>
	
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
	    
    	<script>
    	function addToCart(id) {
    		$.ajax({
    			url: 'cart',
    			type: 'POST',
    			data: jQuery.param({ action: 'add', id_prodotto: id}),
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					$("#plusonecart").show();
					setTimeout(function() {
						$("#plusonecart").hide();
					}, 1000);
				},
				error: function () {
					alert("error");
				}

    		});
    	}
		</script>
	    
	</head>
	<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust">
			<div style="display: flex;">
				<div style="flex: 1;">
					<img src="<%= product.getUrl() %>" style="margin-left: auto; margin-right: auto; display: block; margin-top: 32px; margin-bottom: 32px;">
				</div>
				<div style="flex: 1; margin-top: auto; margin-bottom: auto; display: block; margin-right: 32px;">
					<span class="mdc-typography--headline4">
                    	<%= product.getNome() %> <br>
	            	</span>	            	
					<span class="mdc-typography--body1" style="color: red; font-weight: bold;">
                    	<%= String.format("%.2f", product.getPrezzo()) %> € <br> <br>
	            	</span>
	            	
          			<button class="mdc-button mdc-card__action mdc-card__action--button mdc-button--icon-leading" onclick="addToCart(<%=product.getIdProdotto()%>)">
						<span class="mdc-button__ripple"></span>
						<i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #550024">add_shopping_cart</i>
						<span class="mdc-button__label" style="color: #550024">Aggiungi al carrello</span>
				  	</button>
				  	<br /><br /><br />
	            	
	            	<span class="mdc-typography--caption">
	            		<%= product.getDescrizione() %>
	            	</span>
					
				</div>
			</div>
			
		</main>
		<%@ include file="include/footer.jsp" %>
		
	</body>
</html>