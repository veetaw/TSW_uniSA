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
import model.ProductModelDS;
import model.UserBean;
import model.VinoBean;

/**
 * Servlet implementation class AdminControl
 */
@WebServlet("/admin")
public class AdminControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static OrderModel orderModel;
	static ProductModel model;

	static {
		orderModel = new OrderModel();
		model = new ProductModelDS();
	}

	public AdminControl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.removeAttribute("prodotti");
			request.setAttribute("prodotti", model.doRetrieveAll(""));
		} catch (SQLException e) {
			System.out.println("Errore:" + e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/pannello_amministratore.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("login_info");
		response.setContentType("application/json");
		String productID;

		System.out.println("edit: " + request.getParameter("edit") + " product: ");

		String action = request.getParameter("action");
		if (action != null && !action.isEmpty()) {
			switch (action) {

			case "info":
				productID = request.getParameter("product");
				System.out.println("product info");
				try {
					VinoBean vino = model.doRetrieveByKey(productID);
					if (vino == null || vino.getIdProdotto() == null) {
						throw new Exception("impossibile trovare un prodotto con questo id");
					}
					JSONObject r = new JSONObject();

					r.put("status", 1);
					r.put("vino", vino.toJSONMap());

					response.getWriter().print(r);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "edit":
				System.out.println("ricevuta richiesta di modifica");
				productID = request.getParameter("product");
				try {
					VinoBean vino = new VinoBean();
					vino.setNome(request.getParameter("nome"));
					vino.setDescrizione(request.getParameter("descrizione"));
					vino.setGradazione(Float.valueOf(request.getParameter("gradazione")));
					vino.setPrezzo(Float.valueOf(request.getParameter("prezzo")));
					vino.setRegione(request.getParameter("regione"));
					vino.setUrl(request.getParameter("url"));
					vino.setTipo(request.getParameter("colore"));
					vino.setSapore(request.getParameter("sapore"));
					model.doDelete(productID);
					model.doSave(vino);
					JSONObject r = new JSONObject();
					r.put("status", 1);
					response.getWriter().print(r);

					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "delete":
				productID = request.getParameter("product");
				try {
					model.doDelete(productID);
					JSONObject r = new JSONObject();
					r.put("status", 1);
					response.getWriter().print(r);

					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "addnew":
				try {
					System.out.println(request.getParameter("gradazione"));
					VinoBean vino = new VinoBean();
					vino.setNome(request.getParameter("nome"));
					vino.setDescrizione(request.getParameter("descrizione"));
					vino.setGradazione(Float.valueOf(request.getParameter("gradazione")));
					vino.setPrezzo(Float.valueOf(request.getParameter("prezzo")));
					vino.setRegione(request.getParameter("regione"));
					vino.setUrl(request.getParameter("url"));
					vino.setTipo(request.getParameter("colore"));
					vino.setSapore(request.getParameter("sapore"));

					model.doSave(vino);
					JSONObject r = new JSONObject();
					r.put("status", 1);
					response.getWriter().print(r);

					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;

			}
		}

		if (user != null) {
			try {
				Collection<OrderBean> orders = orderModel.getAllOrdersAdmin();

				JSONObject r = new JSONObject();
				ArrayList<Object> tempList = new ArrayList<>();

				for (OrderBean order : orders) {
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
			} catch (Exception e) {
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
