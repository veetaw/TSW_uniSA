package model;

import java.text.*;
import java.util.*;
import java.sql.*;

public class UserDAO {
	static Connection currentConnection = null;
	static ResultSet resultSet = null;
		
	public static UserBean doRetrieve(UserBean bean) {
		Statement statement = null;
		PreparedStatement preparedStatement = null;

		String username = bean.getUsername();
		String password = bean.getPassword();
		
		final String QUERY = "SELECT * FROM utente WHERE username = '" + username + "' AND password='" + password + "'" ;
				
		System.out.println("Il tuo USERNAME e': " + username);
		System.out.println("La tua PASSWORD e': " + password);
		System.out.println("Query: " + QUERY);

		try {
			Connection connection1 = DriverManagerConnectionPool.getConnection();

			preparedStatement = connection1.prepareStatement(QUERY);
			resultSet = preparedStatement.executeQuery(QUERY);

			boolean more = resultSet.next();

			if (!more) {
				System.out.println("Non sei un utente registrato! Registrati e riprova");
				bean.setValid(false);
			} else {
				if (more) {
					String firstName = resultSet.getString("nome");
					String lastName = resultSet.getString("cognome");

					System.out.println("Welcome " + firstName);

					bean.setFirstName(firstName);
					bean.setLastName(lastName);

					bean.setValid(true);
				}
			}
		} catch (Exception exception) {
			System.out.println("LogIn fallito! Errore: " + exception);
		}

		finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {

				}
				resultSet = null;
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {

				}
				statement = null;
			}

			if (currentConnection != null) {
				try {
					currentConnection.close();
				} catch (Exception e) {

				}
				currentConnection = null;
			}
		}
		return bean;
	}
}
