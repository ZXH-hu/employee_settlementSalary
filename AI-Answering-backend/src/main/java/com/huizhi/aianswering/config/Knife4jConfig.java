package com.huizhi.aianswering.config;

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
                        .title("慧知-AI应用答题平台接口文档")
                        .description("基于 Vue3 + Spring Boot + Redis + ChatGLM AI + RxJava + SSE的AI答题应用平台。" +
                                "用户可以基于AI快速制作并发布多种答题应用，支持检索和分享应用、在线答题并基于评分算法或AI得到回答总结；管理员可以审核应用、集中管理整站内容，并进行统计分析。")
                        .version("1.0")
                        .build())
                .select()
                // 指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.huizhi.aianswering.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
