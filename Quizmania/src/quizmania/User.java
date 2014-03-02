package quizmania;

import java.security.*;
import java.sql.*;


public class User {
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();

	
	public static boolean createUser(String userId, String password, Connection conn){
		String salt = generateSalt().toString();
		String hashedPW = generateHashedPW(password, salt);
		try{
			addUserToDB(userId, hashedPW, salt, conn);
		}catch (SQLException e) {
            e.printStackTrace();
            return false;
        }	
		return true;		
	}
	
	/**
	 *Given a byte[] array, produces a hex String,
	 *such as "234a6f". with 2 chars for each byte in the array.
	 *(provided code)
	*/
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/**
	 * Encrypt password.
	 * @param password
	 * @return encrypted result
	 */
	private static String generateHashedPW(String password, String salt){
		String hashResult = null;
        if(password == null) return null;
		
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(password.getBytes());
	        digest.update(salt.getBytes());
			hashResult = hexToString(digest.digest());
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return hashResult;
	}
	
	// Write code to add salt into password
	
	private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
	
	/**
	 * Check if userId exists in the database
	 * @throws Exception 
	 */
	public static boolean userExists(String userId, Connection conn){
		// Write code to check whether the chosen userId has been used in the database
		Statement stmt = null;
		ResultSet rs;
		try{
			stmt = (Statement) conn.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM users WHERE userId = \"" + userId + "\";");
			if(rs.next()){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * Add userId and hashed password into database
	 * @throws SQLException 
	 */
	private static void addUserToDB(String userId, String hashedPW, String salt, Connection conn) throws SQLException{
		// Write cod to connect database and add user to the database
		Statement stmt = null;
		stmt = (Statement) conn.createStatement();
		stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
		stmt.executeUpdate("INSERT INTO users VALUES (\"" + userId + "\","+ 
				"\"" + hashedPW +"\"," + 
				"\"" + salt + "\"," +
				"\""+ "no" +"\")" + ";");
		
	}
	
	public static boolean promoteUserToAd(String userId, Connection conn){
		Statement stmt = null;
		try{
		stmt = (Statement) conn.createStatement();
		stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
		stmt.executeUpdate("UPDATE users set administrator = \"yes\" "
				+ "WHERE userId = \"" + userId + "\"");
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	


}
