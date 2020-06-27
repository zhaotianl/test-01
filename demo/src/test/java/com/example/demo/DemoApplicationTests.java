package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
class DemoApplicationTests {
    synchronized
    @Test
    void contextLoads() {
        String st = "aaa";
        String stt = new String("aaa");
        System.out.println(st.equals(stt));

    }

}
