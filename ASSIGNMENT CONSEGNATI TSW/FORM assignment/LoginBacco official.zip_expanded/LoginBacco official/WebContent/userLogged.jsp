<%@ page language="java"
	contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"
	import="model.UserBean"
%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type"
		content="text/html; charset=windows-1256">
		<title> User logged successfully! </title>
	</head>
	
	<body>
			<% UserBean currentUser = (UserBean)
			(session.getAttribute("currentSessionUser"));
			if ((currentUser==null)||(!currentUser.isValid()))
			{
				response.sendRedirect("./invalidLogin.jsp");
				return;
			}%>
			Welcome <%= currentUser.getFirstName() 
			+ " " 
			+ currentUser.getLastName() %>
	</body>
</html>






















