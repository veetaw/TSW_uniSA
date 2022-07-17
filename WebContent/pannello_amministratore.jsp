<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"
	session="true"
	import="model.UserBean"%>
<%
Collection<?> products = (Collection<?>) request.getAttribute("prodotti");
	if(products == null) 
	{
		response.sendRedirect("./home");
		return;
	}
%>
<!DOCTYPE html>
<html>
	<%@ page contentType="text/html; 
	charset=UTF-8" import="java.util.*"%>
	
 	<%
		Boolean isUserLoggedIn = (Boolean) session.getAttribute("logged");
 		UserBean utente = (UserBean) session.getAttribute("login_info");
		if(isUserLoggedIn == null || utente == null) isUserLoggedIn = false;
	
		// Un utente non loggato non può accedere a quest'area.
		if(!isUserLoggedIn) {
			response.sendRedirect("login");
		} else if(utente != null && !utente.getTipo().equals("amministratore")) {
			response.sendRedirect("404.html");
		}
	%>
	
	<head>
	    <link href="assets/favicon.ico" rel="icon" type="image/x-icon">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>Pannello amministratore</title>
	    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">
	    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
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
				
				.mdc-text-field {
					margin-top: 32px;
				}

				.component-wrapper {
				  margin-left: auto;
				  margin-right: auto;
				  width: 600px;
				  padding-top: 20px;
				}
				
				.mdc-select {
				  width: 400px;
				     
				  &__menu,
				  &__anchor {
				    width: 100%;
				  }
				  
				  .mdc-select__dropdown-icon {
				    // color: #121212;
				  }
				}

			</style>
	    
	    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
	    	<script
			src="https://code.jquery.com/jquery-3.6.0.min.js"
			integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			crossorigin="anonymous"></script>
			
			<script type="text/javascript">
			var selectedProductID = null;
			
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
                	Ordine effettuato il ` + ordine.data + ` dall'utente con user_ID: ` + ordine.username_utente + `
            	</span>
				<ul class="mdc-list mdc-list-custom" id="lista-prodotti-ordine-nr-` + count + `">
				</ul>
                <span class="mdc-typography--body1" style="color: green; font-weight: bold;">
                	` + ordine.stato.toUpperCase()  +`
        		</span>
				</li>`;
				
				return html;
			}
			
			function getOrders() {
	    		$.ajax({
	    			url: 'admin',
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
			function delete_wine(product_id) {
	    		$.ajax({
	    			url: 'admin',
	    			type: 'POST',
	    			data: jQuery.param({product: product_id, action: "delete"}),
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (response) {
						setTimeout(function() {
							window.location.reload();
						}, 1000);
					},

	    		});
			}
			
			function edit_wine(product_id) {
				$("#modifica_vino").show();
				selectedProductID = product_id;
				clearTab();
				
	    		$.ajax({
	    			url: 'admin',
	    			type: 'POST',
	    			data: jQuery.param({product: product_id, action: "info"}),
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (response) {
						$("#nome").val(response.vino.nome);
						$("#descrizione").val(response.vino.descrizione);
						$("#gradazione").val(response.vino.gradazione.toFixed(2));
						$("#prezzo").val(response.vino.prezzo.toFixed(2));
						$("#regione").val(response.vino.regione);
						$("#urlimmagine").val(response.vino.url);
						const colore = new  mdc.select.MDCSelect(document.querySelector('#mdc-select-colore'));
						const tipo = new  mdc.select.MDCSelect(document.querySelector('#mdc-select-tipo'));
						console.log(response.vino.tipo);
						colore.selectedIndex = response.vino.tipo.toLowerCase() == "rosso" ? 1 : 2;
						tipo.selectedIndex = response.vino.sapore.toLowerCase() == "secco" ? 1 : 2;
					},

	    		});
			}
			
				var currentTab = 0;
				
				function updateCurrentTab(tab) {
					if(currentTab == tab) {return;}
					$("#lista-principale").html("");
					currentTab = tab;					
					return drawTab();
				}
				
				var tabZeroHtml = `STORICO ORDINI`;
				var tabOneHtml = `GESTIONE VINI`;
				
				function clearTab() {
					$("#prodotti_disponibili").hide();
					$("#lista-principale").html("");
				}
				
				function drawTab() {
					$("#modifica_vino").hide();
					selectedProductID = null;
					if(currentTab == 0) {
						$("#prodotti_disponibili").hide();
						getOrders();
						return;
					} else {
						$("#prodotti_disponibili").show();
					}
				}
				
				function sendEditToDB() {
					console.log(selectedProductID);
					var nome = $("#nome").val();
					var descrizione = $("#descrizione").val();
					var gradazione = $("#gradazione").val();
					var prezzo = $("#prezzo").val();
					var regione = $("#regione").val();
					var url = $("#urlimmagine").val();
					const colore = new  mdc.select.MDCSelect(document.querySelector('#mdc-select-colore'));
					const sapore = new  mdc.select.MDCSelect(document.querySelector('#mdc-select-tipo'));
					var tipoString = colore.selectedIndex == 1 ? "rosso" : "bianco";
					var saporeString = sapore.selectedIndex == 1 ? "secco" : "dolce";
					console.log("ajax call");
		    		$.ajax({
		    			url: 'admin',
		    			type: 'POST',
		    			data: jQuery.param({
		    				action: "edit",
		    				product: selectedProductID,
		    				nome: nome,
		    				descrizione: descrizione,
		    				gradazione: gradazione,
		    				prezzo: prezzo,
		    				regione: regione,
		    				url: url,
		    				colore: tipoString,
		    				sapore: saporeString,
		    			}),
						contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (response) {
							
							setTimeout(function() {
								window.location.reload();
							}, 1000);
						
						},
						error: function () {
							alert("error");
						}

		    		});

				}
				
				
				$(document).ready(function() {
					const colore = new  mdc.select.MDCSelect(document.querySelector('#mdc-select-colore'));
					const tipo = new  mdc.select.MDCSelect(document.querySelector('#mdc-select-tipo'));

					colore.listen('MDCSelect:change', () => {
					  console.log(`Selected option at index ${select.selectedIndex} with value "${select.value}"`);
					});

					tipo.listen('MDCSelect:change', () => {
					  console.log(`Selected option at index ${select.selectedIndex} with value "${select.value}"`);
					});
				});
				$(document).ready(drawTab);
			</script>
	</head>
	<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust" style="height: 100%;">
			<div style="width: 30vh; height: 100vh; position: absolute; left: 0px; border-right: 1px solid rgba(27, 27, 27, 0.2);">
				<ul class="mdc-list">
				  <li class="mdc-list-item" tabindex="0" style="padding: 20px;" onclick="updateCurrentTab(0)">
				    <span class="mdc-list-item__ripple"></span>
					<span class="material-icons" style="margin-right: 16px;">
					manage_accounts
					</span>
				    <span class="mdc-list-item__text">Storico ordini</span>
				  </li>
				  <li class="mdc-list-item" style="padding: 20px;" onclick="updateCurrentTab(1)">
				    <span class="mdc-list-item__ripple"></span>
					<span class="material-icons" style="margin-right: 16px;">
					wine_bar
					</span>
				    <span class="mdc-list-item__text">Gestione vini</span>
				  </li>
				</ul>			
			</div>
			<div style="position: absolute; left: 30vh; padding: 32px;" id="content">
		<ul class="mdc-list" id="lista-principale">
			
		</ul>
						    <div class="mdc-layout-grid"id="prodotti_disponibili">
                <span class="mdc-typography--headline6">
                    Lista prodotti disponibili
	            </span>
	        <div class="mdc-layout-grid__inner">
	        	
	        	
	        <%
	        		        		        if (products.size() != 0)
	        		        		        		{
	        		        		        			Iterator<?> it = products.iterator();
	        		        		        			
	        		        		        			while (it.hasNext()) 
	        		        		        			{
	        		        		        				VinoBean bean = (VinoBean) it.next();
	        		        		        %>
	        	
	        		
						<%@ include file="include/simple_product.jsp" %>	        	
	        	
	       	<%
					}
				} 
				else 
				{
			%> 
				<p>Nessun prodotto disponibile!</p>
			<%
				}
			%>	        
	        
	        </div>
		
			</div>
			
			<div id="modifica_vino">
          			<button class="mdc-button mdc-card__action mdc-card__action--button mdc-button--icon-leading" onclick="drawTab()">
						<span class="mdc-button__ripple"></span>
						<i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #550024">arrow_back</i>
						<span class="mdc-button__label" style="color: #550024">INDIETRO</span>
				  	</button><br><br>

<span class="mdc-typography--body1">NOME VINO:</span>
<label class="mdc-text-field mdc-text-field--outlined">
  <span class="mdc-notched-outline">
    <span class="mdc-notched-outline__leading"></span>
    <span class="mdc-notched-outline__notch">
    </span>
    <span class="mdc-notched-outline__trailing"></span>
  </span>
  <input type="text" class="mdc-text-field__input" aria-labelledby="my-label-id" id="nome">
</label>
<br>
<span class="mdc-typography--body1">DESCRIZIONE VINO:</span>
<label class="mdc-text-field mdc-text-field--outlined mdc-text-field--textarea mdc-text-field--no-label">
  <span class="mdc-notched-outline">
    <span class="mdc-notched-outline__leading"></span>
    <span class="mdc-notched-outline__trailing"></span>
  </span>
  <span class="mdc-text-field__resizer">
    <textarea class="mdc-text-field__input" rows="8" cols="40" aria-label="Label" id="descrizione"></textarea>
  </span>
</label>
<br>
<span class="mdc-typography--body1">GRADAZIONE VINO:</span>
<label class="mdc-text-field mdc-text-field--outlined">
  <span class="mdc-notched-outline">
    <span class="mdc-notched-outline__leading"></span>
    <span class="mdc-notched-outline__notch">
    </span>
    <span class="mdc-notched-outline__trailing"></span>
  </span>
  °<input type="number" class="mdc-text-field__input" aria-labelledby="my-label-id" id="gradazione">
</label>
<br>
<span class="mdc-typography--body1">PREZZO VINO:</span>
<label class="mdc-text-field mdc-text-field--outlined">
  <span class="mdc-notched-outline">
    <span class="mdc-notched-outline__leading"></span>
    <span class="mdc-notched-outline__notch">
    </span>
    <span class="mdc-notched-outline__trailing"></span>
  </span>
  €<input type="number" class="mdc-text-field__input" aria-labelledby="my-label-id" id="prezzo">
</label>
<br>
<span class="mdc-typography--body1">REGIONE VINO:</span>
<label class="mdc-text-field mdc-text-field--outlined">
  <span class="mdc-notched-outline">
    <span class="mdc-notched-outline__leading"></span>
    <span class="mdc-notched-outline__notch">
    </span>
    <span class="mdc-notched-outline__trailing"></span>
  </span>
  <input type="text" class="mdc-text-field__input" aria-labelledby="my-label-id" id="regione">
</label>
<br>
<div class="component-wrapper">
  <div class="mdc-select mdc-select--filled" id="mdc-select-tipo">
    <div class="mdc-select__anchor">
      <span class="mdc-select__ripple"></span>
      <span class="mdc-select__selected-text"></span>
      <span class="mdc-select__dropdown-icon">
        <svg class="mdc-select__dropdown-icon-graphic" viewBox="7 10 10 5">
          <polygon class="mdc-select__dropdown-icon-inactive" stroke="none" fill-rule="evenodd" points="7 10 12 15 17 10">
          </polygon>
          <polygon class="mdc-select__dropdown-icon-active" stroke="none" fill-rule="evenodd" points="7 15 12 10 17 15">
          </polygon>
        </svg>
      </span>
      <span class="mdc-floating-label"><span class="mdc-typography--body1">TIPO VINO:</span></span>
      <span class="mdc-line-ripple"></span>
    </div>

    <div class="mdc-select__menu mdc-menu mdc-menu-surface mdc-menu-surface--fullwidth">
      <ul class="mdc-list">
        <li id="sapore" class="mdc-list-item mdc-list-item--selected" data-value="" aria-selected="true">
          <span class="mdc-list-item__ripple"></span>
        </li>
        <li class="mdc-list-item" data-value="bianco">
          <span class="mdc-list-item__ripple"></span>
          <span class="mdc-list-item__text">BIANCO</span>
        </li>
        <li class="mdc-list-item" data-value="rosso">
          <span class="mdc-list-item__ripple"></span>
          <span class="mdc-list-item__text">ROSSO</span>
        </li>
      </ul>
    </div>
  </div>
</div>
<br>
<span class="mdc-typography--body1">URL IMMAGINE VINO:</span>
<label class="mdc-text-field mdc-text-field--outlined">
  <span class="mdc-notched-outline">
    <span class="mdc-notched-outline__leading"></span>
    <span class="mdc-notched-outline__notch">
    </span>
    <span class="mdc-notched-outline__trailing"></span>
  </span>
  <input type="text" class="mdc-text-field__input" aria-labelledby="my-label-id" id="urlimmagine">
</label>
<br>

<div class="component-wrapper">
  <div class="mdc-select mdc-select--filled" id="mdc-select-colore">
    <div class="mdc-select__anchor">
      <span class="mdc-select__ripple"></span>
      <span class="mdc-select__selected-text"></span>
      <span class="mdc-select__dropdown-icon">
        <svg class="mdc-select__dropdown-icon-graphic" viewBox="7 10 10 5">
          <polygon class="mdc-select__dropdown-icon-inactive" stroke="none" fill-rule="evenodd" points="7 10 12 15 17 10">
          </polygon>
          <polygon class="mdc-select__dropdown-icon-active" stroke="none" fill-rule="evenodd" points="7 15 12 10 17 15">
          </polygon>
        </svg>
      </span>
      <span class="mdc-floating-label"><span class="mdc-typography--body1">SAPORE VINO:</span></span>
      <span class="mdc-line-ripple"></span>
    </div>

    <div class="mdc-select__menu mdc-menu mdc-menu-surface mdc-menu-surface--fullwidth">
      <ul class="mdc-list">
        <li class="mdc-list-item mdc-list-item--selected" data-value="" aria-selected="true">
          <span class="mdc-list-item__ripple"></span>
        </li>
        <li class="mdc-list-item" data-value="secco">
          <span class="mdc-list-item__ripple"></span>
          <span class="mdc-list-item__text">SECCO</span>
        </li>
        <li class="mdc-list-item" data-value="dolce">
          <span class="mdc-list-item__ripple"></span>
          <span class="mdc-list-item__text">DOLCE</span>
        </li>
      </ul>
    </div>
  </div>
</div>
<br>
          			<button class="mdc-button mdc-card__action mdc-card__action--button mdc-button--icon-leading" onclick="sendEditToDB()">
						<span class="mdc-button__ripple"></span>
						<i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #550024">edit</i>
						<span class="mdc-button__label" style="color: #550024">Invia modifiche</span>
				  	</button>
			</div>
		
		</main>
</body>
</html>