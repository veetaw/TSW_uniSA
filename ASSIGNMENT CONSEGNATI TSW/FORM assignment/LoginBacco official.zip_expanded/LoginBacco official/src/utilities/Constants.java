package utilities;

public class Constants {
	public static final String IP_ADDR = "localhost";
	public static final String PORT = "3306";
	public static final String DB_NAME = "usersDB";
	public static final String USR_NAME = "root";
	public static final String PASSWORD = "root";
	
	public static String getJDBCAddr() {
		return "jdbc:mysql://" + IP_ADDR + ":" + PORT + "/" + DB_NAME;
	}
}