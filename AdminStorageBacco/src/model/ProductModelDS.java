package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductModelDS implements ProductModel
{
	private static DataSource dataSource;

	static
	{
		try
		{
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");

			dataSource = (DataSource) envContext.lookup("jdbc/storage");
		}
		catch (NamingException e)
		{
			System.out.println("Errore:" + e.getMessage());
		}
	}

	private static final String TABLE_NAME = "product";

	@Override
	public synchronized void doSave(ProductBean product) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + ProductModelDS.TABLE_NAME
							+ " (NAME, DESCRIPTION, PRICE, QUANTITY) VALUES (?, ?, ?, ?)";

		try
		{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setInt(3, product.getPrice());
			preparedStatement.setInt(4, product.getQuantity());

			preparedStatement.executeUpdate();

			connection.commit();
		}
		finally
		{
			try
			{
				if (preparedStatement != null)
				{
					preparedStatement.close();
				}
			}
			finally
			{
				if (connection != null)
				{
					connection.close();
				}
			}
		}
	}

	@Override
	public synchronized ProductBean doRetrieveByKey(int code) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ProductBean bean = new ProductBean();

		String selectSQL = "SELECT * FROM " 
							+ ProductModelDS.TABLE_NAME 
							+ " WHERE CODE = ?";

		try
		{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setInt(1, code);

			ResultSet resulSet = preparedStatement.executeQuery();

			while (resulSet.next())
			{
				bean.setCode(resulSet.getInt("CODE"));
				bean.setName(resulSet.getString("NAME"));
				bean.setDescription(resulSet.getString("DESCRIPTION"));
				bean.setPrice(resulSet.getInt("PRICE"));
				bean.setQuantity(resulSet.getInt("QUANTITY"));
			}
		}
		finally
		{
			try
			{
				if (preparedStatement != null)
				{
					preparedStatement.close();
				}
			}
			finally
			{
				if (connection != null)
				{
					connection.close();
				}
			}
		}
		return bean;
	}

	@Override
	public synchronized boolean doDelete(int code) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " 
							+ ProductModelDS.TABLE_NAME 
							+ " WHERE CODE = ?";

		try
		{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			
			preparedStatement.setInt(1, code);

			result = preparedStatement.executeUpdate();
		}
		finally
		{
			try
			{
				if (preparedStatement != null)
				{
					preparedStatement.close();
				}
			}
			finally
			{
				if (connection != null)
				{
					connection.close();
				}
			}
		}
		return (result != 0);
	}

	@Override
	public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ProductBean> products = new LinkedList<ProductBean>();

		String selectSQL = "SELECT * FROM " 
							+ ProductModelDS.TABLE_NAME;

		if (order != null && !order.equals(""))
		{
			selectSQL += " ORDER BY " + order;
		}

		try
		{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())
			{
				ProductBean bean = new ProductBean();

				bean.setCode(resultSet.getInt("CODE"));
				bean.setName(resultSet.getString("NAME"));
				bean.setDescription(resultSet.getString("DESCRIPTION"));
				bean.setPrice(resultSet.getInt("PRICE"));
				bean.setQuantity(resultSet.getInt("QUANTITY"));
				
				products.add(bean);
			}
		}
		finally
		{
			try
			{
				if (preparedStatement != null)
				{
					preparedStatement.close();
				}
			}
			finally
			{
				if (connection != null)
				{
					connection.close();
				}
			}
		}
		return products;
	}

}