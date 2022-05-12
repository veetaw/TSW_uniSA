package coreservlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/TechBooksPage")

public class ViniRossiPage extends CatalogPage
{
	public void init()
	{
		String[] ids = { "vinoRosso001", "vinoRosso002" };
		setItems(ids);
		setTitle("VINI ROSSI");
	}
}