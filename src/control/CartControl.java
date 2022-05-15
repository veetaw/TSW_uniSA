package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import model.ProductModel;
import model.ProductModelDM;
import model.ProductModelDS;
import model.VinoBean;
import model.Cart;
import utils.Constants;


@WebServlet("/cart")
public class CartControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel model;
	static Cart cart;

	static
	{
		if (Constants.IS_DATA_SOURCE)
		{
			model = new ProductModelDS(); // DataSource
		}
		else
		{
			model = new ProductModelDM(); // DriverManager
		}
		
		cart = new Cart();
	}
    public CartControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cart.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// imposto content type su json
		response.setContentType("application/json");
		
		String action = request.getParameter("action");
		
		// L'utente ha richiesto di mostrare il carrello
		if(action == null || action.isEmpty()) {
			doGet(request, response);
			return;
		}
				
		switch(action) {
		// Se viene chiamato questo l'utente ha richiesto di aggiungere un prodotto al carrello
		case "add":
			try {
				String idProdotto = request.getParameter("id_prodotto");

				if(idProdotto == null || idProdotto.isEmpty()) {
					throw new Exception("id_prodotto deve contenere un id prodotto valido");
				}
				
				VinoBean bean = model.doRetrieveByKey(idProdotto);

				if(bean == null || bean.getIdProdotto() == null) {
					throw new Exception("impossibile trovare un prodotto con questo id");
				}
				
				cart.addProduct(bean);
				
				JSONObject r = new JSONObject();
				
				// stato 1 prodotto aggiunto correttamente al carrello
				r.put("status", 1);
				r.put("vino", bean.toJSONMap());
				
				response.getWriter().print(r);
			} catch(Exception e) {
				handlePostException(e, response);
				return;
			}			
			break;
		// L'utente ha richiesto di listare gli elementi presenti nel carrello
		case "list":
			try {
				JSONObject r = new JSONObject();
				r.put("status", 1);
				
					
				r.put("carrello", this.getCartList());

				response.getWriter().print(r);
			} catch(Exception e) {
				handlePostException(e, response);
				return;
			}
			break;
		// L'utente ha richiesto di rimuovere un elemento dal carrello (se ci sono n prodotti, dopo ce ne saranno n-1)
		case "remove":
			try {
				String idProdotto = request.getParameter("id_prodotto");

				if(idProdotto == null || idProdotto.isEmpty()) {
					throw new Exception("id_prodotto deve contenere un id prodotto valido");
				}
				
				VinoBean bean = model.doRetrieveByKey(idProdotto);
				
				if(bean == null || bean.getIdProdotto() == null) {
					throw new Exception("impossibile trovare un prodotto con questo id");
				}
				
				if(!cart.contains(bean)) {
					throw new Exception("questo prodotto non è presente nel carrello");
				}
				
				cart.removeOneProduct(bean);
				
				JSONObject r = new JSONObject();
				
				// stato 1 prodotto rimosso correttamente al carrello
				r.put("status", 1);
				r.put("vino", bean.toJSONMap()); // TODO: magari ritornare il carrello invece che il vino cancellato?
				
				response.getWriter().print(r);
			} catch(Exception e) {
				handlePostException(e, response);
				return;
			}			
			break;
		// L'utente ha richiesto di cancellare tutti i prodotti con quell'ID dal carrello
		case "delete":
			try {
				String idProdotto = request.getParameter("id_prodotto");

				if(idProdotto == null || idProdotto.isEmpty()) {
					throw new Exception("id_prodotto deve contenere un id prodotto valido");
				}
								
				VinoBean bean = model.doRetrieveByKey(idProdotto);
				
				if(bean == null || bean.getIdProdotto() == null) {
					throw new Exception("impossibile trovare un prodotto con questo id");
				}
				
				if(!cart.contains(bean)) {
					throw new Exception("questo prodotto non è presente nel carrello");
				}
				
				cart.deleteProduct(bean);
				
				JSONObject r = new JSONObject();
				
				// stato 1 prodotto rimosso correttamente al carrello
				r.put("status", 1);
				r.put("vino", bean.toJSONMap()); 
				
				response.getWriter().print(r);
			} catch(Exception e) {
				handlePostException(e, response);
				return;
			}
			break;
		// L'utente ha richiesto di cancellare tutto il carrello
		case "empty":
			try {
				cart.emptyCart();
				
				JSONObject r = new JSONObject();
				r.put("status", 1);
				r.put("carrello", this.getCartList());
				
				response.getWriter().print(r);
			} catch(Exception e) {
				handlePostException(e, response);
				return;
			}
			break;
		// L'utente ha richiesto un operazione non riconosciuta
		default:
			
			doGet(request, response);
			break;
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
			System.out.println("Errore nella creazione del json d'errore");				}
		
		response.getWriter().print(r);

	}
	
	private ArrayList<Object> getCartList() {
		HashMap<VinoBean, Integer> map = cart.getProducts();
		ArrayList<Object> list = new ArrayList<>();
		for(Map.Entry<VinoBean, Integer> entry : map.entrySet()) {
			HashMap<String, Object> temp = new HashMap<>();
			temp.put("count", entry.getValue());
			temp.put("vino", entry.getKey().toJSONMap());
			list.add(temp);
		}
		
		return list;
	}
}
