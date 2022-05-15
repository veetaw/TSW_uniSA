package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;
import javax.servlet.annotation.*;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		try {
			request.getParameterMap().forEach((k, v) -> {
				System.out.println(k + " : " + v);
			});
			
			if(request.getParameterMap().isEmpty()) {
				response.sendRedirect("LoginPage.html");
			}

			UserBean user = new UserBean();
			user.setUserName(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user = UserDAO.doRetrieve(user);

			if (user.isValid()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
				response.sendRedirect("userLogged.jsp");
			} else {
				response.sendRedirect("userLogged.jsp");
			}
		} catch (Throwable theException) {
			System.out.println(theException);
		}
	}
}
