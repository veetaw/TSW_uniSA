<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
VinoBean product = (VinoBean) request.getAttribute("details");
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
	</head>
	<body style="padding: 0; margin: 0;" class="mdc-typography">
		<%@ include file="include/header.jsp" %>
		<main class="mdc-top-app-bar--fixed-adjust">
			<div style="display: flex;">
				<div style="flex: 1;">
					<img src="<%= product.getUrl() %>" style="margin-left: auto; margin-right: auto; display: block; margin-top: 32px; margin-bottom: 32px;">
				</div>
				<div style="flex: 1; margin-top: auto; margin-bottom: auto; display: block; margin-right: 32px;">
					<span class="mdc-typography--headline4">
                    	<%= product.getNome() %> <br>
	            	</span>
					<span class="mdc-typography--body1" style="color: red; font-weight: bold;">
                    	<%= String.format("%.2f", product.getPrezzo()) %> € <br> <br>
	            	</span>
	            	
	            	<span class="mdc-typography--caption">
	            		<%= product.getDescrizione() %>
	            	</span>
					
				</div>
			</div>
			
		</main>
		<%@ include file="include/footer.jsp" %>
		
	</body>
</html>