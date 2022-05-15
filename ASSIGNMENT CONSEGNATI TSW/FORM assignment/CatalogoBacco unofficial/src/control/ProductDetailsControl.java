package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductModel;
import model.ProductModelDM;
import model.ProductModelDS;

@WebServlet("/details")
public class ProductDetailsControl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	static boolean isDataSource = true;

	static ProductModel model;

	static
	{
		if (isDataSource)
		{
			model = new ProductModelDS(); // DataSource
		}
		else
		{
			model = new ProductModelDM(); // DriverManager
		}
	}

	public ProductDetailsControl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String id = request.getParameter("id");
		try
		{
			if (id != null && !id.isEmpty())
			{
				request.removeAttribute("details");
				request.setAttribute("details", model.doRetrieveByKey(id));
			}
		}
		catch (Exception e)
		{
			System.out.println("errore");
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/details.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
