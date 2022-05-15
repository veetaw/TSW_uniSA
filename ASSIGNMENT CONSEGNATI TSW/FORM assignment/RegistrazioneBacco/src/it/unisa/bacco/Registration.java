package it.unisa.bacco;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Registration")

public class Registration extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Registration()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements())
		{
			String paramName = parameterNames.nextElement();
			out.write(paramName);
			out.write(" = \n");

			String[] paramValues = request.getParameterValues(paramName);
			
			for (int i = 0; i < paramValues.length; i++)
			{
				String paramValue = paramValues[i];
				out.write("\t" + paramValue);
				out.write("\n");
			}
			out.write("\n");
		}
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}