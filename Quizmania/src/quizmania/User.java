package quizmania;

import java.security.*;
import java.sql.*;


public class User {
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();

	
	public static boolean createUser(String userId, String password, Connection conn){
		String salt = generateSalt().toString();
		String hashedPW = generateHashedPW(password, salt);
		addUserToDB(userId, hashedPW, salt, conn);	
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
	 * add salt and hash password with salt
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
	
	/**
	 * Generate salt 
	 */
	private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
	
	/**
	 * Check if userId exists in the database
	 * @return true if user exists; false if not. 
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
	 * @return true if successfully add user; false if not.
	 */
	private static boolean addUserToDB(String userId, String hashedPW, String salt, Connection conn){
		// Write cod to connect database and add user to the database
		Statement stmt = null;
		try{
		stmt = (Statement) conn.createStatement();
		stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
		stmt.executeUpdate("INSERT INTO users VALUES (\"" + userId + "\","+ 
				"\"" + hashedPW +"\"," + 
				"\"" + salt + "\"," +
				"\""+ "no" +"\")" + ";");
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	
	
	/**
	 * Add userId and hashed password into database
	 * @return true if successfully promote userId; false if not 
	 */
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
	
	/**
	 * Check userId and password for login
	 * @return true if userId and password combination is correct; false if not
	 */
	public static boolean userLogin(String userId, String password, Connection conn){
		Statement stmt = null;
		ResultSet rs = null;
		String salt = null;
		String hashedPW = null;
		String hashedInputPW = null;
		
		try{
			stmt = (Statement) conn.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM users where userId = \"" + userId + "\"");
			
			while(rs.next()){
				hashedPW = rs.getString("password");
				salt = rs.getString("salt");
			}
			
			try{
				MessageDigest digest = MessageDigest.getInstance("SHA");
				digest.update(password.getBytes());
				digest.update(salt.getBytes());
				hashedInputPW = hexToString(digest.digest());
			}catch (Exception e) {
			    e.printStackTrace();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		if(hashedInputPW.equals(hashedPW)){
			return true;
		}
		return false;
	}
	/**
	 * Change password for given userId
	 * @return true if change password succeed; false if not
	 */
	public static boolean changeUserPassword(String userId, String password, Connection conn){
		String newSalt = generateSalt().toString();
		String newHashedPW = generateHashedPW(password, newSalt);
		Statement stmt = null;
		
		try{
			stmt = (Statement) conn.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			stmt.executeUpdate("UPDATE users set password = \"" +newHashedPW + "\""
				+ "WHERE userId = \"" + userId + "\"");
			stmt.executeUpdate("UPDATE users set salt = \"" +newSalt + "\""
					+ "WHERE userId = \"" + userId + "\"");
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	
		return true;
	}

	/**
	 * Check whether userId is administrator
	 * @return true if is administrator; false elsewise.
	 */
	public static boolean isAdministrator(String userId, Connection conn){
		Statement stmt = null;
		ResultSet rs = null;
		String isAd = null;
		
		try{
			stmt = (Statement) conn.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM users where userId = \"" + userId + "\"");
			while(rs.next()){
				isAd = rs.getString("administrator");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(isAd.equals("yes")){
			return true;
		}
		return false;
	}
	
	/**
	 * Remove userId from database
	 * @return true if succeed; false elsewise.
	 */
	public static boolean removeUser(String userId, Connection conn){
		
		Statement stmt = null;
		
		try{
			stmt = (Statement) conn.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			stmt.executeUpdate("DELETE FROM users where userId = \"" + userId + "\"");
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}


}
