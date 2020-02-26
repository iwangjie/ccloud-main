package com.ccloud.main;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ServletComponentScan
@MapperScan("com.ccloud.main.mapper")  //配置mapper扫描
@Slf4j
/**
 * C-Cloud 启动类
 *  @author wangjie
 */
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("启动完毕\r\napi doc -> http://localhost:8080/ccloud/doc.html");
    }

}
