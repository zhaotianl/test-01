package test003;

/**
 * @author handong
 * @date 2020/5/27 - 17:15
 */
public class User {
    public int age;
    public String name;

    public User(int age) {
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
