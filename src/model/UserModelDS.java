package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import utils.Constants;

public class UserModelDS {
	private static DataSource dataSource;

	// Blocco statico, viene eseguito una sola volta durante l'inizializzazione
	static {
		try {
			// Necessario lookup per accedere a datasource
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");

			dataSource = (DataSource) envContext.lookup(Constants.CONTEXT_PATH);
		} catch (NamingException e) {
			System.out.println("Errore:" + e.getMessage());
		}
	}

	// effettua il login dell'utente
	//
	// fa la query per controllare se l'utente esiste in primis e poi se email e
	// password sono corrette
	public synchronized UserBean login(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;

		final String userExistsQuery = "SELECT * FROM utente WHERE mail LIKE '" + email + "' AND password LIKE '"
				+ password + "'";

		try {
			connection = dataSource.getConnection();

			statement = connection.prepareStatement(userExistsQuery);

			ResultSet userExistsResult = statement.executeQuery();

			UserBean user = new UserBean();

			while (userExistsResult.next()) {
				user.setUsername(userExistsResult.getString("username"));
				user.setNome(userExistsResult.getString("nome"));
				user.setCognome(userExistsResult.getString("cognome"));
				user.setMail(userExistsResult.getString("mail"));
				user.setTipo(userExistsResult.getString("tipo"));
				user.setVia(userExistsResult.getString("via"));
				user.setNumeroCivico(userExistsResult.getInt("n_civico"));
				user.setCitta(userExistsResult.getString("citta"));
				user.setNazione(userExistsResult.getString("nazione"));
			}

			// se l'utente non ha dati allora ritorna null
			if (user.getNome() == null) {
				return null;
			}

			return user;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	// TODO: gestione errori
	public synchronized void register(UserBean user, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;

		final String insertQuery = "INSERT INTO utente"
				+ "(username, password, nome, cognome, mail, tipo, via, n_civico, citta, nazione)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?; ?, ?, ?)";

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertQuery);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, password);
			statement.setString(3, user.getNome());
			statement.setString(4, user.getCognome());
			statement.setString(5, user.getMail());
			statement.setString(6, user.getTipo());
			statement.setString(7, user.getVia());
			statement.setInt(8, user.getNumeroCivico());
			statement.setString(9, user.getCitta());
			statement.setString(10, user.getNazione());
			
			statement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}
}
