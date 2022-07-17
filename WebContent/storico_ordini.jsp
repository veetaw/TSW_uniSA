<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"
	session="true"
	import="model.UserBean"%>

<!DOCTYPE html>
<html>
	<%@ page contentType="text/html; 
	charset=UTF-8" import="java.util.*"%>
	
 	<%
		Boolean isUserLoggedIn = (Boolean) session.getAttribute("logged");
		if(isUserLoggedIn == null || session.getAttribute("login_info") == null) isUserLoggedIn = false;
	
		// Un utente non loggato non può accedere a quest'area.
		if(!isUserLoggedIn) {
			response.sendRedirect("login");
		}
	%>
	
	<head>
	    <link href="assets/favicon.ico" rel="icon" type="image/x-icon">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>Storico Ordini</title>
		    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">
		    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
	    	<script
			src="https://code.jquery.com/jquery-3.6.0.min.js"
			integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			crossorigin="anonymous"></script>
			
			<script>
			
			function getProductHtmlTemplate(product, count) {
				var template = `<div class="mdc-layout-grid__cell" style="width: 250px;">
                <div class="mdc-card">
                <div class="mdc-card__primary-action" tabindex="1" style="padding: 16px;" onclick="window.location.href = 'details?id=` + product.id_prodotto + `'">
                    <div class="my-card__media mdc-card__media mdc-card__media--16-9" style="background-image: url('` + product.url + `'); background-size: contain; height: 150px; width: 250px;"></div>
                    <div>
                        <span class="mdc-typography--headline6">` + product.nome + `</span>
                        <div class="mdc-card__ripple"></div>
                    </div>
                </div>
                <div class="mdc-card__actions">
                  <span class="mdc-typography--body1" style="font-weight: bold;">Quantità: ` + count + ` pz</span>				  
				</div>
           		</div>
        	    </div>`;
        	    
        	    return template;
			}
			
			function getListaCompletaTemplate(ordine, count) {
				var html = `<li class="material-card-ordine container-ordine-` + count + `">
                <span class="mdc-typography--body1" style="font-weight: bold;">
                	Ordine effettuato il ` + ordine.data + `
            	</span>
				<ul class="mdc-list mdc-list-custom" id="lista-prodotti-ordine-nr-` + count + `">
				</ul>
                <span class="mdc-typography--body1" style="color: green; font-weight: bold;">
                	` + ordine.stato.toUpperCase()  +`
        		</span>
				
				<button class="mdc-button mdc-card__action mdc-card__action--button mdc-button--icon-leading button-fattura" onclick="window.location.href='invoice?order_id=` + ordine.id_ordine + `'" style="float: right;">
				<span class="mdc-button__ripple"></span>
				<i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #550024">add_shopping_cart</i>
				<span class="mdc-button__label" style="color: #550024">SCARICA FATTURA</span>
			  	</button>

				
				</li>`;
				
				return html;
			}
			
			function getOrders() {
	    		$.ajax({
	    			url: 'orders',
	    			type: 'POST',
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (response) {
						response.orders.forEach((ordine, index) => {
							$("#lista-principale").append(getListaCompletaTemplate(ordine, index));
							
							ordine.vini.forEach((prodotti_ordine) => {
								$("#lista-prodotti-ordine-nr-" + index).append(getProductHtmlTemplate(prodotti_ordine.vino, prodotti_ordine.count));
							});
						});
					},
					error: function () {
						alert("error");
					}

	    		});
			}
			
			$(document).ready(getOrders);
			</script>
			<style>
				.mdc-list-custom {
				  list-style-type: none;
				  overflow: hidden;
				}
				
				.mdc-layout-grid__cell {
				  float: left;
				  margin: 8px;
				  
				}
				
				.material-card-ordine {
				  background-color: #fff;
				  border-radius: 8px;
				  box-shadow: -1px 2px 4px rgba(0,0,0,.25), 1px 1px 3px rgba(0,0,0,.1);
				  margin: 16px;
				  padding: 16px;
				}
			</style>
	</head>
	<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust">
		<span class="mdc-typography--headline6" style="margin-left: 16px; margin-top: 32px;">
		       Lista ordini effettuati
		</span>
		<ul class="mdc-list" id="lista-principale">
		</ul>
		</main>
		
		<%@ include file="include/footer.jsp" %>
		
	</body>
</html>