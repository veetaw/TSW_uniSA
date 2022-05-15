package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductModel;
import model.ProductModelDM;
import model.ProductModelDS;
import utils.Constants;

@WebServlet("/home")
public class HomePageProductsControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel model;

	static {
		if (Constants.IS_DATA_SOURCE) {
			model = new ProductModelDS(); // DataSource
		} else {
			model = new ProductModelDM(); // DriverManager
		}
	}

	public HomePageProductsControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sort = request.getParameter("sort");

		try {
			request.removeAttribute("prodotti");
			request.setAttribute("prodotti", model.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Errore:" + e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	// TODO: doPost dovrebbe essere usato per avere come tipo di ritorno un json
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		/*
		 * String action = request.getParameter("action");
		 * 
		 * switch(action) { case "list": String sort = request.getParameter("sort");
		 * 
		 * break; default: break; }
		 */ // TODO:
	}

}
