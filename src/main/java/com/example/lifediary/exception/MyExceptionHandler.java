package com.example.lifediary.exception;

import com.example.lifediary.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 描述:全局异常捕获
 * 日期: 2023/2/5 14:34:21
 *
 * @author MoYuXi
 */
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public Result CustomerExceptionHandler(CustomException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return Result.error(collect.toString());
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        return Result.error(e.toString());
    }

}
