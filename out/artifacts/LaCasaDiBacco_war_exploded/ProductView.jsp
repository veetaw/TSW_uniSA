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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="ProductStyle.css" rel="stylesheet" type="text/css">
		<title>Storage La Casa di Bacco</title>
	</head>
	
	<body>
		<h2>PRODOTTI:</h2>
		<a href="product">List</a>
		<table id="tabellaProdotti" border="1">
			<tr>
				<th> <a href="product?sort=code">CODICE</a></th>
				<th> <a href="product?sort=name">NOME</a></th>
				<th> <a href="product?sort=description">DESCRIZIONE</a></th>
				<th>Comandi</th>
			</tr>
			<%
				if (products != null && products.size() != 0) 
				{
					Iterator<?> it = products.iterator();
					
					while (it.hasNext()) 
					{
						ProductBean bean = (ProductBean) it.next();
			%>
			<tr>
				<td><%=bean.getCode()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getDescription()%></td>
				<td><a href="product?action=delete&id=<%=bean.getCode()%>">Cancella</a><br>
					<a href="product?action=read&id=<%=bean.getCode()%>">Dettagli</a><br>
					<a href="product?action=addC&id=<%=bean.getCode()%>">Aggiungi al Carrello</a>
				</td>
			</tr>
			
			<%
					}
				} 
				else 
				{
			%>
			<tr>
				<td colspan="6">Nessun prodotto disponibile!</td>
			</tr>
			<%
				}
			%>
		</table>
		
		
		<%
			if (product != null) 
			{
		%>
		<h2>Dettagli</h2>
		<table id="tabellaDettagli" border="1">
			<tr>
				<th>Codice</th>
				<th>Nome</th>
				<th>Descrizione</th>
				<th>Prezzo</th>
				<th>Quantita'</th>
			</tr>
			<tr>
				<td><%=product.getCode()%></td>
				<td><%=product.getName()%></td>
				<td><%=product.getDescription()%></td>
				<td><%=product.getPrice()%></td>
				<td><%=product.getQuantity()%></td>
			</tr>
		</table>
		<%
			}
		%>
		<br><h2>INSERISCI</h2>
		<form id="formInserisci" action="product" method="post">
			<input type="hidden" name="action" value="insert"> 
			
			<label for="name">Nome:</label><br>
			<input id="nomeInserisci" name="name" type="text" maxlength="20" required placeholder="Inserisci il nome del prodotto da aggiungere"><br><br>
			
			<label for="description">Descrizione:</label><br>
			<textarea name="description" maxlength="200" rows="3" required placeholder="Inserisci la descrizione del prodotto da aggiungere"></textarea><br><br>
			
			<label for="price">Prezzo del prodotto:</label>&nbsp &nbsp
			<label for="quantity">Quantita' del prodotto:</label><br>
			<input name="price" type="number" min="0" value="0" required>&nbsp &nbsp  
			<input name="quantity" type="number" min="1" value="1" required>&nbsp &nbsp &nbsp &nbsp
	
			<input type="submit" value="Aggiungi"> 
			<input type="reset" value="Reset">
		</form>
		
		<% if(cart != null) 
			{ %>
			
			<h2>Carrello</h2>
			<table border="1">
			<tr>
				<th>Codice</th>
				<th>Nome prodotto</th>
				<th>Comandi</th>
			</tr>
			
			<% List<ProductBean> prodcart = cart.getProducts(); 	
			   for(ProductBean beancart: prodcart) 
			   {
			%>
			<tr>
				<td><%=beancart.getCode()%></td>
				<td><%=beancart.getName()%></td>
				<td><a href="product?action=deleteC&id=<%=beancart.getCode()%>">Rimuovi dal Carrello</a></td>
			</tr>
			<% } %>
		</table>		
		<% } %>	
	</body>
	
</html>