package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import utils.Constants;

public class DriverManagerConnectionPool {
    // Lista che contiene le connessioni disponibili
    private static List<Connection> freeDbConnections;

    static {
        freeDbConnections = new LinkedList<Connection>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver della Base di Dati non trovato! Errore:" + e.getMessage());
        }
    }

    /*
        Funzione che si occupa di creare una nuova connessione al database

        I parametri del DB (username, password, etc) sono nel file utils/Constants.java
     */
    private static synchronized Connection createDBConnection() throws SQLException {
        Connection newConnection = null;

        newConnection = DriverManager.getConnection("jdbc:mysql://"
                        + Constants.DB_ADDR
                        + ":"
                        + Constants.DB_PORT
                        + "/"
                        + Constants.DB_NAME
                        + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                Constants.DB_USERNAME,
                Constants.DB_PASSWORD
        );

        newConnection.setAutoCommit(false);

        return newConnection;
    }

    /*
        Funzione che si occupa di prendere la prima connessione disponibile all'interno della
        lista freeDbConnections, oppure in caso non ce ne siano crearne una nuova.
     */
    public static synchronized Connection getConnection() throws SQLException {
        Connection connection;

        if (!freeDbConnections.isEmpty()) {
            connection = (Connection) freeDbConnections.get(0);
            freeDbConnections.remove(0);

            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                connection.close();
                connection = getConnection();
            }
        } else {
            connection = createDBConnection();
        }

        return connection;
    }

    /*
        Libera una connessione per il prossimo utilizzo.
     */
    public static synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            freeDbConnections.add(connection);
        }
    }

}
