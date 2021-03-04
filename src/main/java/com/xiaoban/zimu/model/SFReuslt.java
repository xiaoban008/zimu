package com.xiaoban.zimu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoban
 * @create 2021/03/04 - 20:42
 */
@NoArgsConstructor
@Data
public class SFReuslt implements Serializable {

    /**
     * response : 200
     * url : /control
     * msg : 登录成功
     */
    @JsonProperty("response")
    private Integer response;
    @JsonProperty("url")
    private String url;
    @JsonProperty("msg")
    private Object msg;


    public boolean ok() {
        return this.response == 200;
    }
}
