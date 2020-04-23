package consoleControl;
import dao.DaoFactory;
import dao.OrderDao;
import dao.UserDao;
public class FactoryConsole {
	
	public static void main(String[] args) {
		DaoFactory userdao = new UserDao();
		DaoFactory orderdao = new OrderDao();
		userdao.create();
		orderdao.create();
	}
	

}
