package com.ccloud.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

//    @Bean
//    public Docket createRestApi() {
//        System.out.println("-----------------------------初始化swagger");
//        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(enableSwagger)
//                .groupName("ccould后台api文档")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ccloud.main.controller"))
//                .paths(PathSelectors.any())
//                .build();
//
//    }

    @Bean
    public Docket createWxRestApi() {
        //添加head参数配置start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("CC-Authorization")
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();
        pars.add(tokenPar.build());


        ApiKey apiKey = new ApiKey("CC-Authorization", "CC-Authorization", "header");


        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .groupName("ccould后台api文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ccloud.main.controller"))
                .paths(PathSelectors.any())
                .build()
        .securitySchemes(Collections.singletonList(apiKey));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ccould后台api文档")
                .description("ccould后台api文档，简单优雅的Rest风格。")
                .termsOfServiceUrl("http://www.xxxxx.com/doc.html")
                .version("1.0")
                .build();
    }
}
