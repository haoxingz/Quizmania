package quizmania;

import java.security.*;
import java.sql.*;


public class User {
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private String userId;
	private String hashedPW;
	private Connection conn;
	private String database = MyDBInfo.MYSQL_DATABASE_NAME;
	private String salt;
	
	public User(String userId, String password) throws Exception{
		this.userId = userId;
		hashedPW = generateHashedPW(password);
		conn = new AccessDB().getConnection();
		if(!userExists()){
			addUserToDB();
		}else{
			System.out.println("User already exists!");
		}
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
	private String generateHashedPW(String password){
		String hashResult = null;
		byte[] saltByte =  generateSalt();
		salt = saltByte.toString();
        if(password == null) return null;
		
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(password.getBytes());
	        digest.update(saltByte);
			hashResult = hexToString(digest.digest());
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return hashResult;
	}
	
	// Write code to add salt into password
	
	private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
	
	/**
	 * Check if userId exists in the database
	 * @throws Exception 
	 */
	private boolean userExists() throws Exception{
		// Write code to check whether the chosen userId has been used in the database
		Statement stmt = null;
		ResultSet rs;
		stmt = (Statement) conn.createStatement();
		stmt.executeQuery("USE " + database);
		rs = stmt.executeQuery("SELECT * FROM users WHERE userId = \"" + userId + "\";");
		if(rs.next()){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Add userId and hashed password into database
	 * @throws SQLException 
	 */
	private void addUserToDB() throws SQLException{
		// Write cod to connect database and add user to the database
		System.out.println("Add user to Database!");
		Statement stmt = null;
		stmt = (Statement) conn.createStatement();
		stmt.executeQuery("USE " + database);
		System.out.println("INSERT INTO users VALUES (\"" + userId + "\","+ 
				"\"" + hashedPW +"\"," + 
				"\"" + salt + "\"," +
				"\""+ "no" +"\")" + ";");
		stmt.executeUpdate("INSERT INTO users VALUES (\"" + userId + "\","+ 
				"\"" + hashedPW +"\"," + 
				"\"" + salt + "\"," +
				"\""+ "no" +"\")" + ";");
		
	}
	
	public void promoteUserToAd() throws SQLException{
		Statement stmt = null;
		stmt = (Statement) conn.createStatement();
		stmt.executeQuery("USE " + database);
		stmt.executeUpdate("UPDATE users set administrator = \"yes\" "
				+ "WHERE userId = \"" + userId + "\"");
	}

}
