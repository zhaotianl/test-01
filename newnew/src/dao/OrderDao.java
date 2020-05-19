package dao;

import daointerface.OrderDaoInterface;
import model.Order;

public class OrderDao implements OrderDaoInterface {

	@Override
	public void create() {
		Order order = new Order();
		order.setId(1111111111l);
		order.setName("订单1");
		System.out.println("创建了Order"+order.getName());
	}

	
}