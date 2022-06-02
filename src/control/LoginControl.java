package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import model.UserBean;
import model.UserModelDS;

@WebServlet("/login")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static UserModelDS userModel;
	
	static {
		userModel = new UserModelDS();
	}
	
    public LoginControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("login_info");
		session.setAttribute("logged", false);
		
		if(request.getParameter("logout") != null) {
			return;
		}
		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(email == null || email.isEmpty() || password == null || password.isEmpty()) {
			doGet(request, response);
			return;
		}
		
		// imposto content type su json
		response.setContentType("application/json");
		
		try {
			UserBean user = userModel.login(email, password);
			if(user == null) {
				JSONObject r = new JSONObject();
				try {
					r.put("status", -1);
					r.put("error_message", "user è null");
				} catch (JSONException e1) {
					e1.printStackTrace();
					System.out.println("Errore nella creazione del json d'errore");
				}

				response.getWriter().print(r);
				return;
			}
			
			session.setAttribute("login_info", user);
			session.setAttribute("logged", true);
			
			JSONObject r = new JSONObject();
			try {
				r.put("status", 0);
				r.put("message", "Login SUCCESS");
			} catch (JSONException e1) {
				e1.printStackTrace();
				System.out.println("Errore nella creazione del json d'errore");
			}

			response.getWriter().print(r);
			return;

		} catch (SQLException e) {
			e.printStackTrace();
			JSONObject r = new JSONObject();
			try {
				r.put("status", -1);
				r.put("error_message", "Errore SQL");
			} catch (JSONException e1) {
				e1.printStackTrace();
				System.out.println("Errore nella creazione del json d'errore");
			}

			response.getWriter().print(r);
			return;
		}
	}

}
