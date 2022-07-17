<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
Collection<?> result = (Collection<?>) request.getAttribute("result");
System.out.println(result);
%>
<html>

	<%@ page contentType="text/html; 
	charset=UTF-8" import="java.util.*,model.VinoBean,model.Cart"%>
	
	<head>
	    <link href="assets/favicon.ico" rel="icon" type="image/x-icon">
	    <title>Catalogo La casa di Bacco</title>
	
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">
	    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
	    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
	    <link rel="stylesheet" href="css/main.css">
	    <link rel="stylesheet" href="css/search_bar.css">
	    
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
			<div class='container'>
			  <div class='form-container'>
			    <div class='form-tab'>
			      <div class='search-field'>			        
			        	<form><input autocomplete="off" type='text' name='nome' id='input' class='text-field'></form>
			      </div>
			      <div class='search-btn'>
			        <p>CERCA</p>
			      </div>
			    </div>
			  </div>
			  </div>
			
			<div id="box_risultati" style="margin: 16px;">
				<span class="mdc-typography--headline6">Risultati:</span>
			</div>
					    <div class="mdc-layout-grid">
	        <div class="mdc-layout-grid__inner">
			
	        <%
	        if(result == null) {return;}
	        		        		        if (result.size() != 0)
	        		        		        		{
	        		        		        			Iterator<?> it = result.iterator();
	        		        		        			
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
				<p>Nessun prodotto corrisponde alla ricerca!</p>
			<%
				}
			%>
			</div></div>
		</main>
		<%@ include file="include/footer.jsp" %>
	</body>
</html>