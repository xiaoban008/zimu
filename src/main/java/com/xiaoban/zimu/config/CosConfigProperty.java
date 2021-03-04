package com.xiaoban.zimu.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * cos配置
 * @author xiaoban
 * @create 2021/03/04 - 10:52
 */
@Data
@Component
public class CosConfigProperty {
    @Value("${xiaoban.cos.secretId}")
   private String secretId;
    @Value("${xiaoban.cos.secretKey}")
    private String secretKey ;
    @Value("${xiaoban.cos.region}")
    private String region;
    @Value("${xiaoban.cos.bucket}")
    private String bucket;
    private String baseUrl;

    public String getBaseUrl() {
        baseUrl = "https://"+bucket+".cos."+region+"+.myqcloud.com/";
        return baseUrl;
    }
}
