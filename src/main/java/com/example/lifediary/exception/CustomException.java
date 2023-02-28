package com.example.lifediary.exception;

import lombok.Getter;

/**
 * 描述:自定义异常
 * 日期: 2023/2/5 14:32:08
 *
 * @author MoYuXi
 */
@Getter
public class CustomException extends RuntimeException {
    private Integer code;

    public CustomException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public CustomException(String msg) {
        super(msg);
    }
}
