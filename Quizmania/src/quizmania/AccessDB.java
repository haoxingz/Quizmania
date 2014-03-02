package quizmania;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class AccessDB {
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
    private Connection conn;
	
	public AccessDB(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection
				( "jdbc:mysql://" + server, account ,password);
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	public void closeConnection() throws SQLException{
		conn.close();
	}
}
