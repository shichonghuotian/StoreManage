import org.junit.Before;
import org.junit.Test;

import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.impl.UserDaoImpl;
import com.wy.store.domain.User;
import com.wy.store.domain.UserImage;
import static org.junit.Assert.*;  

//单元测试
public class UserDaoTest {

	UserDao dao;
	
	@Before
	public void setup() {
		
		dao = new UserDaoImpl();
	}
	
	@Test
	public void testGetUser() {
		String userId = "sz11112";

		User user = dao.getUser(userId);
		
		assertEquals(userId, user.getUserId());
	}
	
	@Test
	public void testIsExist() {
		
		String userId = "sz11112";
		
		boolean b = dao.isExist(userId);
		
		assertTrue(b);
	}
	
//	@Test
	public void testAddUser() {
		User user = new User("sz11112", "f_100001", "second User","123");
		
		UserImage image = new UserImage();
		image.setUser(user);
		image.setPath("111");

		boolean b = dao.addUser(user);
		
		if(!b) {
			fail("insert user fail");
		}
		
		assertTrue(b);
	}
}
