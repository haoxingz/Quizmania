package quizmania;


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
		System.out.println(User.userLogin(userId1,PW_right, conn));
		System.out.println(User.userLogin(userId1,PW_wrong, conn));
		
	}

}
