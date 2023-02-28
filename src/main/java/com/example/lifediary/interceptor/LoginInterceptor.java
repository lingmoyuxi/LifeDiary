package com.example.lifediary.interceptor;

import com.example.lifediary.exception.CustomException;
import com.example.lifediary.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述:登录拦截器
 * 日期: 2023/2/5 17:28:33
 *
 * @author MoYuXi
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            return true;
        }
        String path = request.getRequestURL().toString();
        String token = request.getHeader("token");
        log.info("token-->"+token);
        if(token == null || token.isEmpty()){
            log.info("无权访问! ->" + path);
            throw new CustomException(401,"无权访问!");
        }
        Claims claims = JwtUtils.verifyJwt(token);
        if (claims == null) {
            log.info("token无效或已过期 ->" + path);
            throw new CustomException(401,"token无效或已过期，请重新登录！");
        }else {
            Integer userId = (Integer)claims.get("id");
            log.info("已经登录:用户ID -> {}", userId);
            return true;
        }
    }
}
