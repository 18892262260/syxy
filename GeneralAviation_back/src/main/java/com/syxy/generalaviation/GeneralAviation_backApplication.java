package com.syxy.generalaviation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2//扫描swagger2配置
@EnableTransactionManagement//开启事务管理
@MapperScan({"com.syxy.generalaviation.mapper"})
public class GeneralAviation_backApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneralAviation_backApplication.class, args);
    }

}
