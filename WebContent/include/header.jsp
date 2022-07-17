<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*"%>
	<script
			src="https://code.jquery.com/jquery-3.6.0.min.js"
			integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			crossorigin="anonymous"></script>
	<script>
		function logout() {
			return $.ajax({
				url: 'login',
				type: 'POST',
				data: jQuery.param({ logout: "true"}) ,
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					window.location.href="home";
				},
			});
		}
		
		var menu;
				
		$(document).ready(function() {
			menu = new mdc.menu.MDCMenu(document.querySelector('.mdc-menu'));
			document.querySelector("#more_btn").addEventListener("click", function() {
				menu.open = !menu.open;
			});

		});
	</script>

		<header class="mdc-top-app-bar" style="background-color: #870d4c;">
		    <div class="mdc-top-app-bar__row">
		        <section class="mdc-top-app-bar__section mdc-top-app-bar__section--align-start">
					<button class="material-icons mdc-top-app-bar__action-item mdc-icon-button mdc-menu-surface--anchor" id="more_btn">more_vert</button>
		            <span class="mdc-top-app-bar__title" onclick="window.location.href='home'" style="cursor: pointer;">La casa di bacco</span>
		        </section>
		        <section>
		        </section>
		        <section class="mdc-top-app-bar__section mdc-top-app-bar__section--align-end" role="toolbar">
		            <button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" aria-label="Ricerca" onclick="window.location.href = 'search'">search</button>
		            <button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" aria-label="Carrello" onclick="window.location.href = 'cart'">shopping_cart</button> <span id="plusonecart" style="display: none;">+1</span>
		        	<% if(session != null && session.getAttribute("login_info") != null) { %>
		        		<button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" onclick="window.location.href='orders'">account_circle</button><span style="margin-right: 4px; margin-left: 8px;" class="mdc-typography--body1">Ciao, <%= ((UserBean) session.getAttribute("login_info")).getNome() %>!</span>
		        		<button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" onclick="logout()" aria-label="Esci">logout</button>
		        	<%} else {%>
		        		<button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" onclick="window.location.href='login'" aria-label="Entra">login</button>
		        	<% } %>
		        </section>
		    </div>
		</header>
		
		<div class="mdc-menu mdc-menu-surface" style="margin: 8px;">
  <ul class="mdc-list" role="menu" aria-hidden="true" aria-orientation="vertical" tabindex="-1">
    <li class="mdc-list-item" role="menuitem" style="padding: 8px; margin: 4px;" onclick="window.location.href='search?tipo=rosso'">
      <span class="mdc-list-item__ripple"></span>
	  <div class="material-icons" style="margin-left: 8px; margin-right: 8px; color: #560027">local_bar</div>
      <span class="mdc-list-item__text">Vini rossi</span>
    </li>
    <li class="mdc-list-item" role="menuitem" style="padding: 8px; margin: 4px;" onclick="window.location.href='search?tipo=bianco'">
      <span class="mdc-list-item__ripple"></span>
	  <div class="material-icons" style="margin-left: 8px; margin-right: 8px; color: #ff8de3;">local_bar</div>
      <span class="mdc-list-item__text">Vini bianchi</span>
    </li>
  </ul>
</div>
		
