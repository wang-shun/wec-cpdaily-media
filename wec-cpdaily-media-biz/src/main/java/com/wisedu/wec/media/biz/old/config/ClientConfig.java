package com.wisedu.wec.media.biz.old.config;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {


    @Value("${im.url}")
    private String imUrl;

    @Value("${im.appKey}")
    private String appKey;

    @Value("${im.appSecret}")
    private String appSecret;

    @Bean
    public TaobaoClient getImClient() {
        return new DefaultTaobaoClient(imUrl, appKey, appSecret);
    }

}
