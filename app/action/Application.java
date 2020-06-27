package action;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Application {
    // 一行测试注释
    public static void main(String[] args) {


        System.out.println("000000000000000000");
        System.out.println("000000000000000000");
        System.out.println("000000000000000000");
        System.out.println("000000000000000000");
        System.out.println("000000000000000000");
        System.out.println("000000000000000000");
        Map map = new HashMap();
        map.put("1","");
        String  aa = map.get("2")+"";
        System.out.println(aa.equals("null"));
        String  bb = (String) map.get("2");
        System.out.println(bb.equals("n"));
        System.out.println( map.get("2")+"");
       System.out.println(map.get("1").toString());



    }
}
