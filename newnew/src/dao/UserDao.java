package dao;

import daointerface.OrderDaoInterface;
import model.User;

public class UserDao implements OrderDaoInterface{

	@Override
	public void create() {
		User user = new User();
		user.setAge(33);
		System.out.println("创建了User"+user.getName());
	}
	
	
	
}
