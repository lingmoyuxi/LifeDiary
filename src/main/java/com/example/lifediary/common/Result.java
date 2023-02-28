package com.example.lifediary.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:返回结果封装
 * 日期: 2023/2/5 12:01:32
 *
 * @author MoYuXi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private Object data;

    public static Result<?> success(){
        return new Result<>(200,"操作成功",null);
    }
    public static Result<?> success(Object data){
        return new Result<>(200,"操作成功",data);
    }
    public static Result<?> success(String msg){
        return new Result<>(500,msg,null);
    }

    public static Result<?> error(Integer code,String msg){
        return new Result<>(code,msg,null);
    }

    public static Result<?> error(){
        return new Result<>(500,"系统错误，请联系管理员",null);
    }

    public static Result<?> error(String msg){
        return new Result<>(500,msg,null);
    }
}