<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Collection<?> products = (Collection<?>) request.getAttribute("prodotti");
	if(products == null) 
	{
		response.sendRedirect("./home");
		return;
	}
	
	VinoBean product = (VinoBean) request.getAttribute("prodotto");
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
	    <link rel="stylesheet" href="css/carousel.css">
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
		
		
		<div class="carousel">
  <div class="slides">
    <img src="assets/offertatramier.jpeg" alt="slide image" class="slide" onclick="window.location.href='details?id=9'">
    <img src="assets/offerta.jpeg" alt="slide image" class="slide"> <!-- TODO qua puntare a search?categoria=bianco -->
  </div>
  <div class="controls">
    <div class="control prev-slide">&#9668;</div>
    <div class="control next-slide">&#9658;</div>
  </div>
</div>



		
		    <div class="mdc-layout-grid">
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
	        	
	        		
						<%@ include file="include/product_card.jsp" %>	        	
	        	
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
	       
	        <%
				if (product != null) 
				{
			%>
				info prodotto
			<%
				}
			%>
	        
	        
	        </div>
		
		</main>
		
		<%@ include file="include/footer.jsp" %>
		<script>
		
		const delay = 3000; //ms

		const slides = document.querySelector(".slides");
		const slidesCount = slides.childElementCount;
		const maxLeft = (slidesCount - 1) * 100 * -1;

		let current = 0;

		function changeSlide(next = true) {
		  if (next) {
		    current += current > maxLeft ? -100 : current * -1;
		  } else {
		    current = current < 0 ? current + 100 : maxLeft;
		  }

		  slides.style.left = current + "%";
		}

		let autoChange = setInterval(changeSlide, delay);
		const restart = function() {
		  clearInterval(autoChange);
		  autoChange = setInterval(changeSlide, delay);
		};

		// Controls
		document.querySelector(".next-slide").addEventListener("click", function() {
		  changeSlide();
		  restart();
		});

		document.querySelector(".prev-slide").addEventListener("click", function() {
		  changeSlide(false);
		  restart();
		});

		</script>
	</body>
</html>