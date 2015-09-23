package test.com.bs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bs.api.modle.User;
import com.bs.api.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml", "classpath*:spring/dao-applicationContext.xml" })
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/*Context.xml");
		
		
		UserService userService1 = (UserService) context.getBean(UserService.class);
		
		User u = new User();
		u.setName("aaaa");
		u.setPassword("bbbbpwd");
		u.setAge(20);
		u.setSex(0);
		u.setJob("IT");
		u.setUniversity("ALIBABA UNIVERSITY");
		
		userService1.save(u,"111111");
		
		System.out.println("OK");
	}
	
	@Test
	public void insertTest(){
		
		User u = new User();
		u.setName("aaaa");
		u.setPassword("bbbbpwd");
		u.setAge(20);
		u.setSex(1);
		u.setJob("IT");
		u.setUniversity("QIANQUDUO UNIVERSITY");
		
//		userService.save(u,"222222");
		userService.set(u, "222222");
	}
	
	@Test
	public void read(){
//		User u = userService.queryUserBySessionId("111111");
//		User u = userService.queryUserByKey("222222");
		//8a55b4eb-e5e2-4256-8113-ed990581ec39
		User u = userService.queryUserByKey("8a55b4eb-e5e2-4256-8113-ed990581ec39");
		if(u!=null)
		 System.out.println(u.toString());
	}
	
	@Test
	public void read1(){
		User u = userService.queryUserByKey("111111");
//		User u = userService.queryUserByKey("222222");
		if(u!=null)
		 System.out.println(u.toString());
	}
	
	@Test
	public void delete(){
//		Object result = userService.delete("222222");
		Object result = null;
		userService.del("222222");
		System.out.println("delete OK >>>"+result);
	}
}
