package com.xiaoban.zimu.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaoban
 * @create 2021/03/02 - 20:27
 */
@Data
@Builder
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.code;
    }

    public static Result SUCCESS = new Result(ResultCode.SUCCESS);
    public static Result FAIL = new Result(ResultCode.FAIL);

    public  enum  ResultCode{
        /*** 成功*/
         SUCCESS(200),
        /*** 失败*/
         FAIL(400);
         public final Integer code;

         ResultCode(Integer code) {
             this.code = code;
         }

     }
}
