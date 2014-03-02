package quizmania;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Connection;

public class UserTest {

	@Test
	public void test(){
		AccessDB db = new AccessDB();
		String userId1 = "Haoxing";
		String userId2 = "Wei";
		Connection conn = (Connection) db.getConnection();
		if(!User.userExists(userId1, conn)){
			User.createUser(userId1, "123456", conn);
			User.promoteUserToAd(userId1, conn);
		}else{
			System.out.println(userId1 + " User exists!");
		}
		
		if(!User.userExists(userId2, conn)){
			User.createUser(userId2, "123456", conn);
		}else{
			System.out.println(userId2 + " User exists!");
		}
		
		
	}

}
