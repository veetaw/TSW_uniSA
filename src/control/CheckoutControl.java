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

import model.Cart;
import model.OrderModel;
import model.ProductModel;
import model.ProductModelDM;
import model.ProductModelDS;
import model.UserBean;
import utils.Constants;

@WebServlet("/checkout")
public class CheckoutControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ProductModel model;
	static OrderModel orderModel;
	
	static {
		if (Constants.IS_DATA_SOURCE) {
			model = new ProductModelDS(); // DataSource
			orderModel = new OrderModel();
		} else {
			model = new ProductModelDM(); // DriverManager
		}
	}

    public CheckoutControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/checkout/checkout.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("login_info");
		Cart cart = (Cart) session.getAttribute("cart");
		
		response.setContentType("application/json");
		String action = request.getParameter("action");
		
		if(action == null || action.isEmpty()) {
			doGet(request, response);
		}
				
		if(action.equals("new")) {
			try {
				orderModel.newOrder(user, cart.toCollection());
				
				JSONObject r = new JSONObject();
				r.put("status", 1);
				
				response.getWriter().print(r);
				
				cart.emptyCart();
			} catch(Exception e) {
				handlePostException(e, response);
			}
			
		}
	}
	
	private void handlePostException(Exception e, HttpServletResponse response) throws IOException {
		System.out.println("Errore");
		e.printStackTrace();

		JSONObject r = new JSONObject();
		try {
			// stato -1 errore nell'aggiunta del prodotto al carrello
			r.put("status", -1);
			r.put("error_message", e.getMessage());
		} catch (JSONException e1) {
			e1.printStackTrace();
			System.out.println("Errore nella creazione del json d'errore");
		}

		response.getWriter().print(r);

	}


}
