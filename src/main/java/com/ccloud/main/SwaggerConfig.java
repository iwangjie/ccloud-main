package com.ccloud.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 *
 * @author
 * @date 2018/4/15 20:46
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("true")
    private boolean enableSwagger = true;

    @Bean
    public Docket createRestApi() {
        System.out.println("-----------------------------初始化swagger");
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .groupName("ccould后台api文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ccloud.main.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    @Bean
    public Docket createWxRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .groupName("微信管理接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ccloud.main.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ccould后台api文档")
                .description("二手交易信用查询后台api，简单优雅的Rest风格。")
                .termsOfServiceUrl("http://www.cheatman.com/doc.html")
                .version("1.0")
                .build();
    }
}
