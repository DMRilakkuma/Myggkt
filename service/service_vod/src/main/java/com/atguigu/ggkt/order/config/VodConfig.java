package com.atguigu.ggkt.order.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: MC
 * @program: ggkt_parent
 * @create: 2022-09-14 23:05
 * @Description:
 */

@Configuration
@MapperScan("com.atguigu.ggkt.order.mapper")
public class VodConfig {

    /*
    * 分页插件
    * */

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new  PaginationInterceptor();
    }

}