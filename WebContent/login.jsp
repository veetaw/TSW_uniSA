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
	
		// Un utente già loggato non può accedere a quest'area.
		if(isUserLoggedIn) {
			response.sendRedirect("home");
		}
	%>
	
	
	<head>
	    <link href="assets/favicon.ico" rel="icon" type="image/x-icon">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>Login La Casa di Bacco</title>
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
		  			Bentornato in 'La Casa di Bacco'<br>
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
		  	    LOGIN<br>
		  	  </span>
			  <div class="inner">
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="email" autocapitalize='off' autocorrect='off' class='text-field--input' id='email' placeholder=" ">
				    <label class='text-field--label' for='input-text'>Email</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>
				<div class='text-field--container'>
				  <div class='text-field'>
				    <input type="password" autocapitalize='off' autocorrect='off' class='text-field--input' id='password' placeholder=" ">
				    <label class='text-field--label' for='input-text'>Password</label>
				    <div class='text-field--line'></div>
				    <div class='text-field--line--color'></div>
				  </div>
				</div>				
				<center><button class="md-btn" onclick="handleLoginBtnClick()">ACCEDI</button></center>
				<a href="register" class="pass-forgot">Non hai un account?</a>
				
			  </div>
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

		function handleLoginBtnClick() {
			var email = $('#email').val();
			var password = $('#password').val()
			var data = jQuery.param({ email: email, password: password})
			
			if(!validateEmail(email)){
				showError();	
				return;
			}
			
			
			$.ajax({
					url: 'login',
					type: 'POST',
					data: data ,
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (response) {
						if(response.status != 0) {
							showError();
							return;
						} else {
							setTimeout(function() {
								window.location.href = "home";
							}, 1000);
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