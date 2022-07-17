package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.OrderBean;
import model.OrderModel;
import model.UserBean;

@WebServlet("/invoice")
public class InvoiceControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static OrderModel orderModel;
	
	static {
		orderModel = new OrderModel();
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String orderID = request.getParameter("order_id");
		UserBean user = (UserBean) session.getAttribute("login_info");
		
		if(orderID == null || orderID.isEmpty() || user == null) {
			response.sendRedirect("home");
			return;
		}
		
		try {
			OrderBean order = orderModel.getOrderByID(orderID, user);
			
			session.removeAttribute("ordine_fattura");
			session.setAttribute("ordine_fattura", order);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/fattura.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
