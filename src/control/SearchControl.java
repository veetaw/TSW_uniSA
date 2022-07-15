package control;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductModel;
import model.ProductModelDM;
import model.ProductModelDS;
import model.VinoBean;
import utils.Constants;

@WebServlet("/search")
public class SearchControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ProductModel model;

	static {
		if (Constants.IS_DATA_SOURCE) {
			model = new ProductModelDS(); // DataSource
		} else {
			model = new ProductModelDM(); // DriverManager
		}
	}

	public SearchControl() {
		super();
	}

	// La ricerca sarà possibile per nome, sarà possibile filtrare per regione,
	// gradazione, tipo e sapore, inoltre
	// sarà possibile ordinare per il prezzo in modo decrescente o crescente
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String regione = request.getParameter("regione");
		String gradazione = request.getParameter("gradazione");
		String tipo = request.getParameter("tipo"); // rosso, bianco
		String sapore = request.getParameter("sapore"); // secco, dolce
		String orderByPrezzo = request.getParameter("orderByPrezzo"); // potrà essere NULL (default), ASC per crescente,
																		// DESC per decrescente

		if(nome == null || nome.isEmpty()) {
			request.setAttribute("status", "Ricerca non valida");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/search.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		try {
			Collection<VinoBean> result = model.doSearch(nome, regione, tipo, gradazione, orderByPrezzo);
			request.removeAttribute("result");
			request.setAttribute("result", result);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/search.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
