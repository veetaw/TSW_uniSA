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
		
		
		function getCartTotal(carrello) {
			var somma = 0;
			carrello.forEach(function(e) {somma += (e.count * e.vino.prezzo)});
			return somma;
		}
		
    	function addToCart(id) {
    		$.ajax({
    			url: 'cart',
    			type: 'POST',
    			data: jQuery.param({ action: 'add', id_prodotto: id}),
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					clearPage();
					initCart();
				},
				error: function () {
					alert("error");
				}

    		});
    	}
		
		function getCartItemTemplate(itemJson) {
			var html = `<li class="mdc-list-item" tabindex="0" style="padding: 20px;">
		    <span class="mdc-list-item" onclick="window.location.href='details?id=` + itemJson.vino.id_prodotto + `'">
		          <img src=` + itemJson.vino.url + ` style="max-width:350px;max-height:200px; width: auto; height: auto; display: block; margin-right: 20px;">
		    </span>
		    <span class="mdc-list-item__text" style="width: 100%;">
		      <span class="mdc-list-item__primary-text">` + itemJson.vino.nome + `</span>
		      <span class="mdc-list-item__secondary-text"> `+ itemJson.vino.descrizione.split(' ').slice(0,15).join(' ') + ` ... </span>
		      <button class="mdc-icon-button material-icons" onclick="removeOneItemFromCart(` +itemJson.vino.id_prodotto + `)">
		      	<div class="mdc-icon-button__ripple"></div>
		      	remove
		      </button>
		      <span class="mdc-typography--body2">` + itemJson.count + `</span>
		      <button class="mdc-icon-button material-icons" onclick="addToCart(` +itemJson.vino.id_prodotto + `)">
		        <div class="mdc-icon-button__ripple"></div>
		        add
		      </button>
		    </span>
		    <div class="mdc-list-item__meta">
		      <button class="mdc-icon-button material-icons" onclick="removeItemFromCart(` +itemJson.vino.id_prodotto + `)" data-mdc-auto-init="MDCRipple" data-mdc-ripple-is-unbounded>remove_shopping_cart</button>
		    </div>
		  </li>
		  <li role="separator" class="mdc-list-divider"></li>`;
		  
		  return html;
		}
				
		function initCart() {
			return $.ajax({
					url: 'cart',
					type: 'POST',
					data: jQuery.param({ action: "list"}) ,
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (response) {
						if(response.carrello.length == 0) {
							$("#empty-cart").show();
							return;
						}
						
						$("#cart-items-count-text").html("Sono presenti <b>" + response.carrello.length + "</b> elementi nel carrello.");
						$("#cart-items-count-text").show();
						
						$("#cart_sum").html("Il totale del carrello è <b>" + getCartTotal(response.carrello)) + "</b>";
						
						response.carrello.forEach(function(element) {
							console.log(element);
							$("#cart_list").append(getCartItemTemplate(element));
						});
					},
					error: function () {
						alert("error");
					}
				});
		}
		
		function clearPage() {
			$("#cart_list").empty();
		}
		
		function removeOneItemFromCart(id) {
    		$.ajax({
    			url: 'cart',
    			type: 'POST',
    			data: jQuery.param({ action: 'remove', id_prodotto: id}),
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					clearPage();
					initCart();
				},
				error: function () {
					alert("error");
				}

    		});
		}
		
		function removeItemFromCart(id) {
    		$.ajax({
    			url: 'cart',
    			type: 'POST',
    			data: jQuery.param({ action: 'delete', id_prodotto: id}),
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					console.log("rimosso");
					
					clearPage();
					initCart();
				},
				error: function () {
					alert("error");
				}

    		});
		}
		
		
		
		$(document).ready(initCart);
	</script>	
	</head>
	
	<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust" style="height: 100%;">
		    <div class="mdc-layout-grid">
                <span class="mdc-typography--headline6">
                    Carrello
	            </span>
	        <div>
	        	<span class="mdc-typography--headline5" id="empty-cart" style="display: none;">
	        		Il carrello è vuoto, torna alla homepage per aggiungere
	        		nuovi elementi che verranno mostrati qui.
	        	</span>
	        	
	        	<span class="mdc-typography--body1" id="cart-items-count-text" style="display: none;"></span>
	        </div>
	        
		<ul class="mdc-list mdc-list--two-line mdc-list--thumbnail-list" id="cart_list">
		
		</ul>
		
		<span id="cart_sum" class="mdc-typography--body1"></span>
	  <button class="mdc-button mdc-button--raised mdc-card__action mdc-card__action--button mdc-button--icon-leading" onclick="console.log('figa');" style="position: absolute; right: 16px; background-color: #550024">
		  <span class="mdc-button__ripple"></span>
		  <i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #FFF">add_shopping_cart</i>
		  <span class="mdc-button__label" style="color: #FFF">ACQUISTA</span>
	  </button>


</main>
		
		<%@ include file="include/footer.jsp" %>
	</body>
</html>
