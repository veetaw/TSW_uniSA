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

import utils.Constants;


// DataSource sono factory di connessioni verso sorgenti dati fisiche
// rappresentate da oggetti di tipo javax.sql.DataSource
//
// Le risorse del datasource sono definite in WEB-INF/web.xml
public class ProductModelDS implements ProductModel {
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

	/*
		Funzione per salvare un vino.
		Da usare nella parte dell'amministratore quando inserisce un nuovo vino
	 */
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
            connection = dataSource.getConnection();
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
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }
	/*
		Funzione per ottenere un vino dall'ID
		Da usare per acquisti/cancellazioni/visualizzazione dettagli
	 */
    @Override
    public synchronized VinoBean doRetrieveByKey(String codice) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        VinoBean bean = new VinoBean();

        String selectSQL = "SELECT * FROM "
                + Constants.VINO_DB_TABLE_NAME
                + " WHERE id_prodotto = ?";

		// TODO: anche qui capire se gestire errore oppure va bene tornare un bean con valori nulli
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            preparedStatement.setString(1, codice);

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
                if (connection != null) {
                    connection.close();
                }
            }
        }

        return bean;
    }

	/*
		Funzione per "cancellazione" prodotto, imposta la flag `eliminato` a true
		Invece di cancellare definitivamente il prodotto abbiamo deciso di mantenerli
		tutti e in caso di cancellazione impostare questa flag a true. Questo ha come
		svantaggio un più rapido riempimento del database, ma come vantaggio ci permette
		di mantenere uno storico dei prodotti acquistati consistente anche dopo un'eliminazione.

		Per evitare spazzatura nel database potrebbe essere utile uno script che eseguito
		periodicamente vada a cancellare definitivamente i prodotti con la flag `eliminato`
		impostata su true, ma con zero acquisti collegati a esso.
	 */
    @Override
    public synchronized boolean doDelete(String code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;
		String deleteSQL = "UPDATE " + Constants.VINO_DB_TABLE_NAME
				+ " SET eliminato = true WHERE id_prodotto = ?";

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);

            preparedStatement.setString(1, code);

            result = preparedStatement.executeUpdate();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }

		// Se l'operazione è andata a buon fine il risultato sarà diverso da 1
        return (result != 0);
    }

	/*
		Funzione per prendere tutti i prodotti presenti nel database con
		la flag `eliminato` impostata su false, quindi i prodotti attualmente
		attivi.

		Da usare per il catalogo.
	 */
    @Override
    public synchronized Collection<VinoBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<VinoBean> products = new LinkedList<VinoBean>();

        String selectSQL = "SELECT * FROM "
                + Constants.VINO_DB_TABLE_NAME + " WHERE eliminato != true";

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

		// TODO: gestione degli errori
        try {
            connection = dataSource.getConnection();
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
                if (connection != null) {
                    connection.close();
                }
            }
        }

        return products;
    }

    @Override
    public synchronized Collection<VinoBean> doRetrieveAll() throws SQLException {
        return this.doRetrieveAll("");
    }

}