package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import utils.Constants;

public class OrderModel {

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
	
	public synchronized void newOrder(UserBean user, Collection<VinoBean> products) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;

		String newOrderSQL = "INSERT INTO ordine (id_ordine, stato, data, username_utente) VALUES (?, ?, ?, ?)";

		try {
			String idOrdine = String.format("%.16s", UUID.randomUUID().toString());
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(newOrderSQL);
			
			statement.setString(1, idOrdine);
			statement.setString(2, "in elaborazione");
			statement.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
			statement.setString(4, user.getUsername());
			
			statement.executeUpdate();
			
			String newComposizioneOrdineSQL = "INSERT INTO composizione_ordine (id_ordine_cliente, id_prodotto_ordinato) VALUES (?, ?)";
			for (VinoBean prodotto : products) {
				statement = null;
				statement = connection.prepareStatement(newComposizioneOrdineSQL);
				
				statement.setString(1, idOrdine);
				statement.setString(2, prodotto.getIdProdotto());
				
				statement.executeUpdate();
			}
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
