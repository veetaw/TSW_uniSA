package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import model.UserBean;
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String indirizzo = request.getParameter("indirizzo");
		String numeroCivico = request.getParameter("numeroCivico");
		String citta = request.getParameter("citta");
		String nazione = request.getParameter("nazione");
		
		
		if(email == null || email.isEmpty() || password == null || password.isEmpty()) {
			doGet(request, response);
			return;
		}

		
		// imposto content type su json
		response.setContentType("application/json");

		try {
			UserBean user = new UserBean(
				(String) null, nome, cognome, email, "registrato", indirizzo,
				Integer.parseInt(numeroCivico), citta, nazione
			);
			userModel.register(user, password);
			
			JSONObject r = new JSONObject();
			try {
				r.put("status", 0);
				r.put("message", "Register SUCCESS");
			} catch(JSONException e) {
				e.printStackTrace();
				System.out.println("Errore nella creazione del json");
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
