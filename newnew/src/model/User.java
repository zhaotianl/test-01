package model;

public class User {
	
	private String usercode;
	
	private int age;
	
	private String name;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		if(this.age>30) {
			return "大人";
		}else {
			return "小孩";
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	

}
