package com.leyou.seckill.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.seckill.access.AccessLimit;
import com.leyou.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 98050
 * @Time: 2018-11-23 23:45
 * @Feature: 接口限流拦截器
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null){
                return true;
            }

            //获取用户信息
            UserInfo userInfo = LoginInterceptor.getLoginUser();
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin){
                if (userInfo == null){
                    render(response, "用户没有登录");
                    return false;
                }
                key += "_" + userInfo.getId();
            }else {
                //不需要登录，则什么也不做
            }
            String count = redisTemplate.opsForValue().get(key);
            if (count == null){
                redisTemplate.opsForValue().set(key,"1",seconds, TimeUnit.SECONDS);
            }else if(Integer.valueOf(count) < maxCount){
                redisTemplate.opsForValue().increment(key,1);
            }else {
                render(response,"稍后再试");
            }

        }

        return super.preHandle(request, response, handler);
    }

    private void render(HttpServletResponse response, String str) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(str.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
