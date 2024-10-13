package com.huizhi.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Profile({"dev", "test"})
public class Knife4jConfig {
    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("员工计时工资统计功能接口文档")
                        .description("基于 Vue3 + Spring Boot + MySQL + Mybatis-Plus3" +
                                "具备管理员权限人员可以添加计时类型并维护，包括员工类型、计时工资基数、启用状态；员工计时表中，管理员对员工工时、不同类型工价数据进行维护，保存数据更新日期；然后即可根据员工的工时结算出每日、每月的工资数据，并进行数据维护。")
                        .version("1.0")
                        .build())
                .select()
                // 指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.huizhi.employee.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
