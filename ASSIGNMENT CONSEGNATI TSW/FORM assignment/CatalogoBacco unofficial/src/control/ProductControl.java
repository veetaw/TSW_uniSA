package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ProductModel;
import model.ProductModelDM;
import model.ProductModelDS;
import model.Cart;
import model.ProductBean;

public class ProductControl extends HttpServlet
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

	public ProductControl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		if (cart == null)
		{
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}

		String action = request.getParameter("action");

		try
		{
			if (action != null)
			{
				if (action.equalsIgnoreCase("addC"))
				{
				}
				else
				{
					if (action.equalsIgnoreCase("deleteC"))
					{
					}
					else
					{
						if (action.equalsIgnoreCase("read"))
						{
						}
						else
						{
							if (action.equalsIgnoreCase("delete"))
							{
								int id = Integer.parseInt(request.getParameter("id"));
								model.doDelete(id);
							}
							else
							{
								if (action.equalsIgnoreCase("insert"))
								{
									String name = request.getParameter("name");
									String description = request.getParameter("description");
									int price = Integer.parseInt(request.getParameter("price"));
									int quantity = Integer.parseInt(request.getParameter("quantity"));

									ProductBean bean = new ProductBean();
									/*
									 * bean.setName(name); bean.setDescription(description); bean.setPrice(price);
									 * bean.setQuantity(quantity);
									 */
									model.doSave(bean);
								}
							}
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Errore:" + e.getMessage());
		}

		request.getSession().setAttribute("cart", cart);
		request.setAttribute("cart", cart);

		String sort = request.getParameter("sort");

		try
		{
			request.removeAttribute("products");
			request.setAttribute("products", model.doRetrieveAll(sort));
		}
		catch (SQLException e)
		{
			System.out.println("Errore:" + e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
