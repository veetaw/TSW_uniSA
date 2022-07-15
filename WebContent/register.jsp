<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"
	session="true"
	import="model.UserBean"%>
<!DOCTYPE html>
<html>
 	<%
			Boolean isUserLoggedIn = (Boolean) session.getAttribute("logged");
			if(isUserLoggedIn == null || session.getAttribute("login_info") == null) isUserLoggedIn = false;
		
			// Un utente già loggato non può accedere a quest'area.
			if(isUserLoggedIn) {
				response.sendRedirect("home");
			}
		%>
	<head>
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>Registrazione La Casa di Bacco</title>
		    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">
		    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
	    	<script
			src="https://code.jquery.com/jquery-3.6.0.min.js"
			integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
			crossorigin="anonymous"></script>
			
	</head>
		<body style="padding: 0; margin: 0;" class="mdc-typography">
		<div class="toast">
	        <div class="toast-content">
	            <div class="message">
	                <span class="text text-1">ERRORE</span>
	                <span class="text text-2">C'è stato un errore di validazione dell'email, della password, <br>oppure un problema generico di login. <br>Riprova, se non dovesse funzionare, contatta un amministratore.</span>
	            </div>
	        </div>
	        <div class="progress"></div>
	    </div>
	
	
		<div style="display: flex">
		  <div style="flex: 50%; background-color: #880e4f; height: 100vh; color: #FFF;" id="left-image">
		  	<center style="padding-top: 7%">
		  		<img src="assets/wine.svg">
		  	</center>
		  	<center style="padding-top: 7%;">
		  		<span class="mdc-typography--headline4">
		  			Benvenuto in 'La Casa di Bacco'<br>
		  		</span>
		  	</center>
		  	<center style="padding-top: 3%;">
		  		<span class="mdc-typography--body2">
		  			<b>Progetto a cura di:</b><br>
		  			Aldi Matteo<br>
		  			Marchese Marco Maria<br>
		  			Piegari Vito Gerardo<br>
		  		</span>
		  	</center>
		  </div>
		  <div style="flex: 50%; height: 100vh" class="cn">
		  	  <span class="mdc-typography--headline4">
		  	    REGISTRAZIONE<br>
		  	  </span>
			  <div style="display: grid;grid-template-columns: auto auto;">
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="email" autocapitalize='off' autocorrect='off' class='text-field--input' id='email' placeholder=" ">
				    <label class='text-field--label' for='input-text'>*Email</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="password" autocapitalize='off' autocorrect='off' class='text-field--input' id='password' placeholder=" ">
				    <label class='text-field--label' for='input-text'>*Password</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>				
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="text" autocapitalize='off' autocorrect='off' class='text-field--input' id='name' placeholder=" ">
				    <label class='text-field--label' for='input-text'>*Nome</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>				
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="text" autocapitalize='off' autocorrect='off' class='text-field--input' id='surname' placeholder=" ">
				    <label class='text-field--label' for='input-text'>*Cognome</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>				
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="text" autocapitalize='off' autocorrect='off' class='text-field--input' id='address' placeholder=" ">
				    <label class='text-field--label' for='input-text'>Indirizzo</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>				
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="number" min="1" max="99" autocapitalize='off' autocorrect='off' class='text-field--input' id='hnumber' placeholder=" ">
				    <label class='text-field--label' for='input-text'>Numero Civico</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>				
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="text" autocapitalize='off' autocorrect='off' class='text-field--input' id='city' placeholder=" ">
				    <label class='text-field--label' for='input-text'>Città</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>				
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="text" autocapitalize='off' autocorrect='off' class='text-field--input' id='state' placeholder=" ">
				    <label class='text-field--label' for='input-text'>Nazione</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>								
			  </div>
			  	<center><button class="md-btn" onclick="handleRegisterBtnClick()">REGISTRATI</button></center>
				<a href="login" class="pass-forgot">Hai già un account?</a>
		  </div>
		</div>
		<script>		
		const validateEmail = (email) => {
			  return String(email)
			    .toLowerCase()
			    .match(
			      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
			    );
			};
		
		let timer1, timer2;
		function showError() {
			$('.text-field--container').toggleClass("error");
		    const toast = document.querySelector(".toast");
		    const closeIcon = document.querySelector(".close");
		    const progress = document.querySelector(".progress");
		    
		    toast.classList.add("active");
	        progress.classList.add("active");
	        
	        timer1 = setTimeout(() => {
	            toast.classList.remove("active");
	        }, 5000);
	        
	        timer2 = setTimeout(() => {
	            progress.classList.remove("active");
	          }, 5300);		        
		}
		
		function handleRegisterBtnClick() {
			const email = $('#email').val();
			const password = $('#password').val();
			const nome = $('#name').val();
			const cognome = $('#surname').val();
			const indirizzo = $('#address').val();
			const numeroCivico = $('#hnumber').val();
			const citta = $('#city').val();
			const nazione = $('#state').val();
			
			if(!validateEmail(email)) {
				showError();
				return;
			}
			
			// campi obbligatori
			if(email === "" || password === "" || nome === "" || cognome === "") {
				showError();
				return;
			}
			
			
			const data = jQuery.param({email: email, password: password, nome: nome, cognome: cognome,
				indirizzo: indirizzo, numeroCivico: numeroCivico, citta: citta, nazione: nazione});
			
			console.log(email, password, nome, cognome, indirizzo, numeroCivico, citta, nazione);
			$.ajax({
				url: 'register',
				type: 'POST',
				data: data,
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (response) {
					if(response.status != 0) {
						showError();
						return;
					} else {
						setTimeout(function() {
							window.location.href = "login";
						}, 2000);
					}
				},
				error: function () {
					showError();
				}
			});
			
		}
		</script>
		<link rel="stylesheet" href="css/login.css">
	</body>
</html>