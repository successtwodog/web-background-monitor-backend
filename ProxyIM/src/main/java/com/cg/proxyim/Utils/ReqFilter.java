package com.cg.proxyim.Utils;

import com.alibaba.fastjson.JSON;
import com.cg.proxyim.Entity.DatumElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Set;

public class ReqFilter {
//    @Resource
//    private  RedisTemplate redisTemplate;
    public  boolean doFilter(HttpServletRequest request,RedisTemplate redisTemplate) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("/proxy/api/messages/addmsg");
        strings.add("/proxy/api/messages/getmsg");
        strings.add("/proxy/api/auth/login");
        strings.add("/proxy/api/auth/register");
        strings.add("/proxy/api/auth/logout");
        strings.add("/proxy/api/auth/allusers");
        strings.add("/proxy/api/auth/setavatar");
//        strings.add("");
//        strings.add("");

        String requestURI = request.getRequestURI();
        for (String str : strings) {
            if (requestURI.startsWith(str)) {
                System.out.println("合理请求");
                return true;
            }
        }
        System.out.println("不合理请求");
        DatumElement datumElement = new DatumElement();
        datumElement.setErrorType("ApiError");
        datumElement.setErrorReason("请求路径错了");
        datumElement.setErrorMsg("错了");
        datumElement.setDeviceid("xp");
        datumElement.setUserid("1");
        datumElement.setUserip("123");
        datumElement.setErrorBundle(requestURI);
        datumElement.setTime(OffsetDateTime.now());
//        redisTemplate.opsForValue("error"+)
        System.out.println(datumElement);
        String jsonString = JSON.toJSONString(datumElement);

        Boolean error = redisTemplate.opsForValue().setIfAbsent("error", jsonString);
        System.out.println(error);

        String retdata= (String) redisTemplate.opsForValue().get("error");
        System.out.println(retdata);
        return false;

    }
}
