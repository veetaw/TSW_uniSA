<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*"%>
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
	</script>

		<header class="mdc-top-app-bar" style="background-color: #870d4c">
		    <div class="mdc-top-app-bar__row">
		        <section class="mdc-top-app-bar__section mdc-top-app-bar__section--align-start">
		            <span class="mdc-top-app-bar__title" onclick="window.location.href='http://localhost:8080/LaCasaDiBacco/'">La casa di bacco</span>
		        </section>
		        <section>
		        </section>
		        <section class="mdc-top-app-bar__section mdc-top-app-bar__section--align-end" role="toolbar">
		            <button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" aria-label="Ricerca" onclick="window.location.href = 'search'">search</button>
		            <button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" aria-label="Carrello" onclick="window.location.href = 'cart'">shopping_cart</button> <span id="plusonecart" style="display: none;">+1</span>
		        	<% if(session != null && session.getAttribute("login_info") != null) { %>
		        		<div class="material-icons mdc-top-app-bar__action-item">account_circle</div><span style="margin-right: 4px; margin-left: 8px;" class="mdc-typography--body1">Ciao, <%= ((UserBean) session.getAttribute("login_info")).getNome() %>!</span>
		        		<button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" onclick="logout()" aria-label="Esci">logout</div>
		        	<%} else {%>
		        		<button class="material-icons mdc-top-app-bar__action-item mdc-icon-button" onclick="window.location.href='login'" aria-label="Entra">login</div>
		        	<% } %>
		        </section>
		    </div>
		</header>
