package com.ccloud.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * CCLOUD 启动类
 * @author wangjie
 */
@SpringBootApplication
@EnableCaching
@ServletComponentScan
@MapperScan("com.ccloud.main.mapper")  //配置mapper扫描
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
