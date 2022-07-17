package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import model.OrderBean;
import model.OrderModel;
import model.ProductModel;
import model.ProductModelDM;
import model.ProductModelDS;
import model.UserBean;
import model.VinoBean;
import utils.Constants;

/**
 * Servlet implementation class OrderPageControl
 */
@WebServlet("/orders")
public class OrderPageControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static OrderModel orderModel;
	
	static {
		orderModel = new OrderModel();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderPageControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/storico_ordini.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("login_info");
		
		response.setContentType("application/json");

		if(user != null) {
			try {
				Collection<OrderBean> orders = orderModel.getAllOrdersFromUser(user);
				
				JSONObject r = new JSONObject();
				ArrayList<Object> tempList = new ArrayList<>();
				
				for(OrderBean order : orders) {
					JSONObject temp = new JSONObject();
					temp.put("data", order.getDate());
					temp.put("id_ordine", order.getIdOrdine());
					temp.put("stato", order.getStato());
					temp.put("username_utente", order.getUsernameUtente());
					
					temp.put("vini", this.getJson(order.getProducts()));
					
					tempList.add(temp);
				}
				r.put("status", 1);
				r.put("orders", tempList);
				
				response.getWriter().print(r);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<Object> getJson(HashMap<VinoBean, Integer> map) {
		ArrayList<Object> list = new ArrayList<>();
		for (Map.Entry<VinoBean, Integer> entry : map.entrySet()) {
			HashMap<String, Object> temp = new HashMap<>();
			temp.put("count", entry.getValue());
			temp.put("vino", entry.getKey().toJSONMap());
			list.add(temp);
		}

		return list;
	}

}
