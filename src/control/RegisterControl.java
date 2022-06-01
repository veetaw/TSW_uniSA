package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserModelDS;

@WebServlet("/register")
public class RegisterControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static UserModelDS userModel;
	
	static {
		userModel = new UserModelDS();
	}
	
    public RegisterControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/register.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
