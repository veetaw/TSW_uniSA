<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) 
	{
		response.sendRedirect("./product");	
		return;
	}
	
	ProductBean product = (ProductBean) request.getAttribute("product");
	Cart cart = (Cart) request.getAttribute("cart");
%>

<!DOCTYPE html>
<html>

	<%@ page contentType="text/html; 
	charset=UTF-8" import="java.util.*, model.ProductBean, model.Cart"%>
	
	<head>
	    <title>Catalogo La casa di Bacco</title>
	
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">
	    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
	    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
	    <link rel="stylesheet" href="css/main.css">
	</head>
	
	<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust">
		    <div class="mdc-layout-grid">
                <span class="mdc-typography--headline6">
                    Lista prodotti disponibili
	            </span>
	        <div class="mdc-layout-grid__inner">
	        	
	        	
	        <%

				if (products != null && products.size() != 0) 
				{
					Iterator<?> it = products.iterator();
					
					while (it.hasNext()) 
					{
						ProductBean bean = (ProductBean) it.next();
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
	</body>
	
</html>