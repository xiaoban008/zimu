package com.xiaoban.zimu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaoban
 * @create 2021/03/02 - 20:44
 */
@Data
public class SrtConfig implements Serializable {
    private Integer id;
    private String appId;
    private String secretKey;

    public SrtConfig(String appId, String secretKey) {
        this.appId = appId;
        this.secretKey = secretKey;
    }
}
