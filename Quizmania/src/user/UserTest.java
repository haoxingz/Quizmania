package user;


import static org.junit.Assert.*;

import org.junit.Test;

import com.mysql.jdbc.Connection;

public class UserTest {

	@Test 
	/*
	 * Test create new user
	 */
	public void test1(){
		AccessDB db = new AccessDB();
		String userId1 = "Haoxing";
		String userId2 = "Wei";
		String password = "123456";
		Connection conn = (Connection) db.getConnection();
		if(!User.userExists(userId1, conn)){
			User.createUser(userId1, password, conn);
			User.promoteUserToAd(userId1, conn);
		}else{
			System.out.println(userId1 + " User exists!");
		}
		
		if(!User.userExists(userId2, conn)){
			User.createUser(userId2, password, conn);
		}else{
			System.out.println(userId2 + " User exists!");
		}
	}
	
	@Test 
	/*
	 * Test user login
	 */
	public void test2(){
		AccessDB db = new AccessDB();
		String userId1 = "Haoxing";
		String PW_right = "123456";
		String PW_wrong = "12345";
		Connection conn = (Connection) db.getConnection();
		assertEquals(false, User.userLogin(userId1,PW_wrong, conn));
		assertEquals(true, User.userLogin(userId1,PW_right, conn));
	}

	@Test 
	/*
	 * Test change user password
	 */
	public void test3(){
		AccessDB db = new AccessDB();
		String userId = "Molly";
		
		String newPW = "dog";
		String oldPW = "Molly";
		Connection conn = (Connection) db.getConnection();
		User.changeUserPassword(userId, newPW, conn);
		assertEquals(false, User.userLogin(userId,oldPW, conn));
		assertEquals(true, User.userLogin(userId,newPW, conn));
		
	}
	
	@Test 
	/*
	 * Test isAdministrator
	 */
	public void test4(){
		AccessDB db = new AccessDB();
		String userId1 = "Molly";
		String userId2 = "Wei";
		Connection conn = (Connection) db.getConnection();
		assertEquals(false, User.isAdministrator(userId1, conn));
		assertEquals(true, User.isAdministrator(userId2, conn));
		
	}

	@Test 
	/*
	 * Test removeUser
	 */
	public void test5(){
		AccessDB db = new AccessDB();
		String userId1 = "Molly";
		Connection conn = (Connection) db.getConnection();
		assertEquals(true, User.removeUser(userId1, conn));
		
	}
}
