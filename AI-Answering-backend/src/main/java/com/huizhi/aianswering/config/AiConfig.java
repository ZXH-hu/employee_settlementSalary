package com.huizhi.aianswering.config;

import com.zhipu.oapi.ClientV4;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 指定配置信息前缀，记载信息就会从前缀中去取值
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {

    /**
     * AI 识别接口地址 apikey
     *
     */
    private String apikey;

    /**
     * 获取AI识别接口AI 识别接口地址 secretkey
     * @return
     */
    @Bean
    public ClientV4 getClientV4() {
        return new ClientV4.Builder(apikey).build();
    }
}
