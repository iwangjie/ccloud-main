package com.ccloud.main.config;

import com.baomidou.mybatisplus.extension.plugins.IllegalSQLInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-plus 配置
 *
 * @author wangjie
 */
@Configuration
@MapperScan("com.ccloud.main.mapper.*")
public class MybatisPlusConfig {

    /**
     * 分页拦截器
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 垃圾SQL拦截器
     *
     * @return
     */
    @Bean
    public IllegalSQLInterceptor illegalSQLInterceptor() {
        return new IllegalSQLInterceptor();
    }

    /**
     * 防止全表更新删除
     *
     * @return
     */
    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        return new SqlExplainInterceptor();
    }
}

