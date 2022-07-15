<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,model.*"%>
<!DOCTYPE html>
<html>
	<%
			Boolean isUserLoggedIn = (Boolean) session.getAttribute("logged");
			if(isUserLoggedIn == null || session.getAttribute("login_info") == null) isUserLoggedIn = false;
		
			// Un utente già loggato non può accedere a quest'area.
			if(!isUserLoggedIn) {
				response.sendRedirect("login");
				return;
			}
			
			UserBean user = (UserBean) session.getAttribute("login_info");
	%>

	<%@ page contentType="text/html; 
	charset=UTF-8" import="java.util.*,model.VinoBean, model.Cart"%>
	<head>
		<title>Inserisci dati carta</title>
		<link rel="stylesheet" href="checkout/checkout.css">
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
		function getCartTotal(carrello) {
			var somma = 0;
			carrello.forEach(function(e) {somma += (e.count * e.vino.prezzo)});
			return somma;
		}

		function getCartItemTemplate(element) {
			return `<tr>
		      <td data-label="Nome articolo">` + element.vino.nome + `</td>
		      <td data-label="Quantità">` + element.count + `</td>
		      <td data-label="Costo">` + element.count * element.vino.prezzo + `</td>
		    </tr>`;
		}
		
		var cart;
		
		function getCart() {
			return $.ajax({
				url: 'cart',
				type: 'POST',
				data: jQuery.param({ action: "list"}) ,
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					if(response.carrello.length == 0) {
						window.location.href= "home";
						return;
					} else if(response.carrello.length > 0) {
						cart = response.carrello;
						response.carrello.forEach(function(element) {
							console.log(element);
							$("#cart-list").append(getCartItemTemplate(element));
						});
						 
						$("#costo").html("TOTALE: <b>" + getCartTotal(response.carrello).toFixed(2) + " €</b>"); 
					}
				},
				});
		}
		
		function checkout() {
			return $.ajax({
				url: 'checkout',
				type: 'POST',
				data: jQuery.param({ action: "new"}) ,
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					if(response.status == 1) {
						setTimeout(function() {
							alert("Grazie per l'acquisto"); // TODO: fare una pagina con scritto grazie
							window.location.href="home";
						}, 1000);
					}
				},
				});
		}
		
		$(document).ready(getCart);

		</script>
	</head>

	<body>
	
		<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="../include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust" style="height: 100%; text-align: center;">	
<div class="checkout">
  <div class="credit-card-box">
    <div class="flip">
      <div class="front">
        <div class="chip"></div>
        <div class="logo">
          <svg version="1.1" id="visa" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
               width="47.834px" height="47.834px" viewBox="0 0 47.834 47.834" style="enable-background:new 0 0 47.834 47.834;">
            <g>
              <g>
                <path d="M44.688,16.814h-3.004c-0.933,0-1.627,0.254-2.037,1.184l-5.773,13.074h4.083c0,0,0.666-1.758,0.817-2.143
                         c0.447,0,4.414,0.006,4.979,0.006c0.116,0.498,0.474,2.137,0.474,2.137h3.607L44.688,16.814z M39.893,26.01
                         c0.32-0.819,1.549-3.987,1.549-3.987c-0.021,0.039,0.317-0.825,0.518-1.362l0.262,1.23c0,0,0.745,3.406,0.901,4.119H39.893z
                         M34.146,26.404c-0.028,2.963-2.684,4.875-6.771,4.875c-1.743-0.018-3.422-0.361-4.332-0.76l0.547-3.193l0.501,0.228
                         c1.277,0.532,2.104,0.747,3.661,0.747c1.117,0,2.313-0.438,2.325-1.393c0.007-0.625-0.501-1.07-2.016-1.77
                         c-1.476-0.683-3.43-1.827-3.405-3.876c0.021-2.773,2.729-4.708,6.571-4.708c1.506,0,2.713,0.31,3.483,0.599l-0.526,3.092
                         l-0.351-0.165c-0.716-0.288-1.638-0.566-2.91-0.546c-1.522,0-2.228,0.634-2.228,1.227c-0.008,0.668,0.824,1.108,2.184,1.77
                         C33.126,23.546,34.163,24.783,34.146,26.404z M0,16.962l0.05-0.286h6.028c0.813,0.031,1.468,0.29,1.694,1.159l1.311,6.304
                         C7.795,20.842,4.691,18.099,0,16.962z M17.581,16.812l-6.123,14.239l-4.114,0.007L3.862,19.161
                         c2.503,1.602,4.635,4.144,5.386,5.914l0.406,1.469l3.808-9.729L17.581,16.812L17.581,16.812z M19.153,16.8h3.89L20.61,31.066
                         h-3.888L19.153,16.8z"/>
              </g>
            </g>
          </svg>
        </div>
        <div class="number"></div>
        <div class="card-holder">
          <label>Intestatario</label>
          <div><%= user.getNome() + " " + user.getCognome() %></div>
        </div>
        <div class="card-expiration-date">
          <label>Scadenza</label>
          <div></div>
        </div>
      </div>
      <div class="back">
        <div class="strip"></div>
        <div class="logo">
          <svg version="1.1" id="visa" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
               width="47.834px" height="47.834px" viewBox="0 0 47.834 47.834" style="enable-background:new 0 0 47.834 47.834;">
            <g>
              <g>
                <path d="M44.688,16.814h-3.004c-0.933,0-1.627,0.254-2.037,1.184l-5.773,13.074h4.083c0,0,0.666-1.758,0.817-2.143
                         c0.447,0,4.414,0.006,4.979,0.006c0.116,0.498,0.474,2.137,0.474,2.137h3.607L44.688,16.814z M39.893,26.01
                         c0.32-0.819,1.549-3.987,1.549-3.987c-0.021,0.039,0.317-0.825,0.518-1.362l0.262,1.23c0,0,0.745,3.406,0.901,4.119H39.893z
                         M34.146,26.404c-0.028,2.963-2.684,4.875-6.771,4.875c-1.743-0.018-3.422-0.361-4.332-0.76l0.547-3.193l0.501,0.228
                         c1.277,0.532,2.104,0.747,3.661,0.747c1.117,0,2.313-0.438,2.325-1.393c0.007-0.625-0.501-1.07-2.016-1.77
                         c-1.476-0.683-3.43-1.827-3.405-3.876c0.021-2.773,2.729-4.708,6.571-4.708c1.506,0,2.713,0.31,3.483,0.599l-0.526,3.092
                         l-0.351-0.165c-0.716-0.288-1.638-0.566-2.91-0.546c-1.522,0-2.228,0.634-2.228,1.227c-0.008,0.668,0.824,1.108,2.184,1.77
                         C33.126,23.546,34.163,24.783,34.146,26.404z M0,16.962l0.05-0.286h6.028c0.813,0.031,1.468,0.29,1.694,1.159l1.311,6.304
                         C7.795,20.842,4.691,18.099,0,16.962z M17.581,16.812l-6.123,14.239l-4.114,0.007L3.862,19.161
                         c2.503,1.602,4.635,4.144,5.386,5.914l0.406,1.469l3.808-9.729L17.581,16.812L17.581,16.812z M19.153,16.8h3.89L20.61,31.066
                         h-3.888L19.153,16.8z"/>
              </g>
            </g>
          </svg>

        </div>
        <div class="ccv">
          <label>cvv</label>
          <div></div>
        </div>
      </div>
    </div>
  </div>
  <form class="form" autocomplete="off" novalidate action="javascript:void(0);">
    <fieldset>
      <label for="card-number">Numero carta</label>
      <input type="num" id="card-number" class="input-cart-number" maxlength="4"/>
      <input type="num" id="card-number-1" class="input-cart-number" maxlength="4" />
      <input type="num" id="card-number-2" class="input-cart-number" maxlength="4" />
      <input type="num" id="card-number-3" class="input-cart-number" maxlength="4" />
    </fieldset>
    <fieldset>
      <label for="card-holder">Intestatario carta</label>
      <input type="text" id="card-holder"/ value="<%= user.getNome() + " " + user.getCognome() %>">
    </fieldset>
    <fieldset class="fieldset-expiration">
      <label for="card-expiration-month">Data scadenza</label>
      <div class="select">
        <select id="card-expiration-month">
          <option></option>
          <option>01</option>
          <option>02</option>
          <option>03</option>
          <option>04</option>
          <option>05</option>
          <option>06</option>
          <option>07</option>
          <option>08</option>
          <option>09</option>
          <option>10</option>
          <option>11</option>
          <option>12</option>
        </select>
      </div>
      <div class="select">
        <select id="card-expiration-year">
          <option></option>
          <option>2023</option>
          <option>2024</option>
          <option>2025</option>
          <option>2026</option>
          <option>2027</option>
        </select>
      </div>
    </fieldset>
    <fieldset class="fieldset-ccv">
      <label for="card-ccv">CVV</label>
      <input type="text" id="card-ccv" maxlength="3" />
    </fieldset>
    <fieldset>
      <label for="card-holder">Indirizzo di consegna</label>
      <input type="text" id="card-holder"/>
    </fieldset>
    <button class="btn" onclick="checkout()"><i class="fa fa-lock"></i> PAGA E CONCLUDI ORDINE</button>
  </form>
</div>
	

	<table style="color: black; width: 100vh; text-align: left; margin: 20px;">
  <caption><span class="mdc-typography--headline6" style="color: black;">
		RIASSUNTO CARRELLO:
	</span></caption>
  <thead>
    <tr>
      <th scope="col">Nome articolo</th>
      <th scope="col">Quantità</th>
      <th scope="col">Costo</th>
    </tr>
  </thead>
  <tbody id ="cart-list">
  </tbody>
</table>
<span class="mdc-typography--headline6" style="color: black; left: 0;" id="costo">
		COSTO TOTALE DA PAGARE: 
	</span>
</main>
		<script src="checkout/checkout.js"></script>
	</body>
</html>