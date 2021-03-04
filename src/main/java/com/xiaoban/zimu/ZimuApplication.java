package com.xiaoban.zimu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZimuApplication {

    public static void main(String[] args) {
        web();
        SpringApplication.run(ZimuApplication.class, args);
    }

//    @PostConstruct
    private static void web(){
    }

}
