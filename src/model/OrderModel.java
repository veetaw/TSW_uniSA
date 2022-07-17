package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			statement.setDate(3, new Date(System.currentTimeMillis()));
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

	public synchronized Collection<OrderBean> getAllOrdersFromUser(UserBean user) throws SQLException {
		ArrayList<OrderBean> ordini = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;

		String _getAllOrdersByID = "SELECT * FROM ordine WHERE username_utente = '" + user.getUsername() + "' ORDER BY data DESC";

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(_getAllOrdersByID);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				OrderBean order = new OrderBean();

				String usernameUtente = resultSet.getString("username_utente");
				String idOrdine = resultSet.getString("id_ordine");
				order.setIdOrdine(idOrdine);
				order.setStato(resultSet.getString("stato"));
				order.setDate(resultSet.getDate("data"));
				order.setUsernameUtente(usernameUtente);

				String _getInfoVinoSQL = "select v.* " + "from composizione_ordine as c " + "join vino as v "
						+ "on c.id_prodotto_ordinato = v.id_prodotto " + "WHERE c.id_ordine_cliente = '" + idOrdine
						+ "';";
				PreparedStatement statement2 = null;
				try {
					statement2 = connection.prepareStatement(_getInfoVinoSQL);
					ResultSet prodInfoResSet = statement2.executeQuery();

					while (prodInfoResSet.next()) {
						VinoBean bean = new VinoBean();

						bean.setIdProdotto(prodInfoResSet.getString("id_prodotto"));
						bean.setNome(prodInfoResSet.getString("nome"));
						bean.setDescrizione(prodInfoResSet.getString("descrizione"));
						bean.setGradazione(prodInfoResSet.getFloat("gradazione"));
						bean.setPrezzo(prodInfoResSet.getFloat("prezzo"));
						bean.setRegione(prodInfoResSet.getString("regione"));
						bean.setUrl(prodInfoResSet.getString("url"));
						bean.setTipo(prodInfoResSet.getString("tipo"));
						bean.setSapore(prodInfoResSet.getString("sapore"));

						order.addProduct(bean);
					}
				} finally {
					if (statement2 != null) {
						statement2.close();
					}
				}

				ordini.add(order);
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

		return ordini;
	}

	public synchronized Collection<OrderBean> getAllOrdersAdmin() throws SQLException {
		ArrayList<OrderBean> ordini = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;

		String _getAllOrdersByID = "SELECT * FROM ordine";

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(_getAllOrdersByID);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				OrderBean order = new OrderBean();

				String usernameUtente = resultSet.getString("username_utente");
				String idOrdine = resultSet.getString("id_ordine");
				order.setIdOrdine(idOrdine);
				order.setStato(resultSet.getString("stato"));
				order.setDate(resultSet.getDate("data"));
				order.setUsernameUtente(usernameUtente);

				String _getInfoVinoSQL = "select v.* " + "from composizione_ordine as c " + "join vino as v "
						+ "on c.id_prodotto_ordinato = v.id_prodotto " + "WHERE c.id_ordine_cliente = '" + idOrdine
						+ "';";
				PreparedStatement statement2 = null;
				try {
					statement2 = connection.prepareStatement(_getInfoVinoSQL);
					ResultSet prodInfoResSet = statement2.executeQuery();

					while (prodInfoResSet.next()) {
						VinoBean bean = new VinoBean();

						bean.setIdProdotto(prodInfoResSet.getString("id_prodotto"));
						bean.setNome(prodInfoResSet.getString("nome"));
						bean.setDescrizione(prodInfoResSet.getString("descrizione"));
						bean.setGradazione(prodInfoResSet.getFloat("gradazione"));
						bean.setPrezzo(prodInfoResSet.getFloat("prezzo"));
						bean.setRegione(prodInfoResSet.getString("regione"));
						bean.setUrl(prodInfoResSet.getString("url"));
						bean.setTipo(prodInfoResSet.getString("tipo"));
						bean.setSapore(prodInfoResSet.getString("sapore"));

						order.addProduct(bean);
					}
				} finally {
					if (statement2 != null) {
						statement2.close();
					}
				}

				ordini.add(order);
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

		return ordini;
	}
	
	public OrderBean getOrderByID(String orderID, UserBean user) throws SQLException {
		OrderBean order = new OrderBean();
		Connection connection = null;
		PreparedStatement statement = null;
		String _getOrderByID = null;
		// se non faccio così un utente può vedere le fatture degli altri, però se passo
		// null io lo può fare, passerò null solo nelle pagine amministratore
		if (user != null) {
			_getOrderByID = "SELECT * FROM ordine WHERE username_utente = '" + user.getUsername()
					+ "' AND id_ordine = '" + orderID + "';";
		} else {
			_getOrderByID = "SELECT * FROM ordine WHERE id_ordine = '" + orderID + "';";
		}

		System.out.println(_getOrderByID);

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(_getOrderByID);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				String usernameUtente = resultSet.getString("username_utente");
				String idOrdine = resultSet.getString("id_ordine");
				order.setIdOrdine(idOrdine);
				order.setStato(resultSet.getString("stato"));
				order.setDate(resultSet.getDate("data"));
				order.setUsernameUtente(usernameUtente);

				String _getInfoVinoSQL = "select v.* " + "from composizione_ordine as c " + "join vino as v "
						+ "on c.id_prodotto_ordinato = v.id_prodotto " + "WHERE c.id_ordine_cliente = '" + idOrdine
						+ "';";
				PreparedStatement statement2 = null;
				try {
					statement2 = connection.prepareStatement(_getInfoVinoSQL);
					ResultSet prodInfoResSet = statement2.executeQuery();

					while (prodInfoResSet.next()) {
						VinoBean bean = new VinoBean();

						bean.setIdProdotto(prodInfoResSet.getString("id_prodotto"));
						bean.setNome(prodInfoResSet.getString("nome"));
						bean.setDescrizione(prodInfoResSet.getString("descrizione"));
						bean.setGradazione(prodInfoResSet.getFloat("gradazione"));
						bean.setPrezzo(prodInfoResSet.getFloat("prezzo"));
						bean.setRegione(prodInfoResSet.getString("regione"));
						bean.setUrl(prodInfoResSet.getString("url"));
						bean.setTipo(prodInfoResSet.getString("tipo"));
						bean.setSapore(prodInfoResSet.getString("sapore"));

						order.addProduct(bean);
					}
				} finally {
					if (statement2 != null) {
						statement2.close();
					}
				}
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

		return order;
	}
}
