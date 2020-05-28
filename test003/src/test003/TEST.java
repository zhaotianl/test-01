package test003;

public class TEST {
    /**
     * （1）基本数据类型传值，对形参的修改不会影响实参；
     * （2）引用类型传引用，形参和实参指向同一个内存地址（同一个对象），所以对参数的修改会影响到实际的对象；
     * （3）String, Integer, Double等immutable的类型特殊处理，可以理解为传值，最后的操作不会修改实参对象。
     */
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("1111111111111111111111");
		System.out.println("1111111111111111111111");
		/**00000000000000000000000000000000000000000*/
		System.out.println("2222222222222222222222222");
		System.out.println("2222222222222222222222222");
		System.out.println("2222222222222222222222222");

//		String a = new String("张三");
//		TEST t = new TEST();
//		t.change(a);
//		System.out.println(a);
//		int num = 2;
//		//TEST t = new TEST();
//		changeNum(num);
//		System.out.println(num);
        //		//TEST t = new TEST();
//		changeNum(num);
//		System.out.println(num);

        User user = new User(22, "张三");
        changeUser(user);
        System.out.println(user.toString());

    }

    public void change(String a) {
        a = "李四";
    }

    public static void changeNum(int num) {
        num = 20;
    }

    public static void changeUser(User user) {
        user.age = 2;
        user.name = "王二麻子";
    }


}