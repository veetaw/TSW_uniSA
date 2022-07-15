package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import utils.Constants;

// Driver manager
public class ProductModelDM implements ProductModel {
	@Override
	public synchronized void doSave(VinoBean product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + Constants.VINO_DB_TABLE_NAME
				+ " (id_prodotto, nome, descrizione, gradazione, prezzo, regione, url, tipo, sapore)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// TODO: gestione errori in caso di prodotto già presente ed altro
		// in caso di errore dovrà essere mostrato poi nel sito web
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, product.getIdProdotto());
			preparedStatement.setString(2, product.getNome());
			preparedStatement.setString(3, product.getDescrizione());
			preparedStatement.setFloat(4, product.getGradazione());
			preparedStatement.setFloat(5, product.getPrezzo());
			preparedStatement.setString(6, product.getRegione());
			preparedStatement.setString(7, product.getUrl());
			preparedStatement.setString(8, product.getTipo());
			preparedStatement.setString(9, product.getSapore());

			preparedStatement.executeUpdate();

			connection.commit();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	@Override
	public synchronized VinoBean doRetrieveByKey(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		VinoBean bean = new VinoBean();

		String selectSQL = "SELECT * FROM " + Constants.VINO_DB_TABLE_NAME + " WHERE id_prodotto = ?";

		// TODO: anche qui capire se gestire errore oppure va bene tornare un bean con
		// valori nulli
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			preparedStatement.setString(1, code);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				bean.setIdProdotto(resultSet.getString("id_prodotto"));
				bean.setNome(resultSet.getString("nome"));
				bean.setDescrizione(resultSet.getString("descrizione"));
				bean.setGradazione(resultSet.getFloat("gradazione"));
				bean.setPrezzo(resultSet.getFloat("prezzo"));
				bean.setRegione(resultSet.getString("regione"));
				bean.setUrl(resultSet.getString("url"));
				bean.setTipo(resultSet.getString("tipo"));
				bean.setSapore(resultSet.getString("sapore"));
			}
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}

		return bean;
	}

	@Override
	public synchronized boolean doDelete(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;
		String deleteSQL = "UPDATE " + Constants.VINO_DB_TABLE_NAME + " SET eliminato = true WHERE id_prodotto = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);

			preparedStatement.setString(1, code);

			result = preparedStatement.executeUpdate();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}

		// Se l'operazione è andata a buon fine il risultato sarà diverso da 1
		return (result != 0);
	}

	@Override
	public synchronized Collection<VinoBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<VinoBean> products = new LinkedList<VinoBean>();

		String selectSQL = "SELECT * FROM " + Constants.VINO_DB_TABLE_NAME + " WHERE eliminato != true";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		// TODO: gestione degli errori
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				VinoBean bean = new VinoBean();

				bean.setIdProdotto(resultSet.getString("id_prodotto"));
				bean.setNome(resultSet.getString("nome"));
				bean.setDescrizione(resultSet.getString("descrizione"));
				bean.setGradazione(resultSet.getFloat("gradazione"));
				bean.setPrezzo(resultSet.getFloat("prezzo"));
				bean.setRegione(resultSet.getString("regione"));
				bean.setUrl(resultSet.getString("url"));
				bean.setTipo(resultSet.getString("tipo"));
				bean.setSapore(resultSet.getString("sapore"));

				products.add(bean);
			}
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}

		return products;
	}

	@Override
	public synchronized Collection<VinoBean> doRetrieveAll() throws SQLException {
		return this.doRetrieveAll("");
	}
	
	@Override
	public synchronized Collection<VinoBean> doSearch(String nome, String regione, String tipo, String gradazione, String orderByPrezzo) throws SQLException {
		return null; // TODO:
	}

}