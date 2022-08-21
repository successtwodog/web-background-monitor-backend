package com.cg.proxyim.Controller;

import com.alibaba.fastjson.JSONObject;
import com.cg.proxyim.Entity.MessageReq;
import com.cg.proxyim.Utils.IpUtils;
import com.cg.proxyim.Utils.ReqFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@RestController
public class ProxyController {
    @Value("${target-node}")
    private String targetAddr ;
    @Resource
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/**", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void proxy(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
        // String url = URLDecoder.decode(request.getRequestURL().toString(), "UTF-8");
        ReqFilter reqFilter = new ReqFilter();
        boolean b = reqFilter.doFilter(request,redisTemplate);


        LocalDate date_of_today = LocalDate.now();
        String prefix=date_of_today.toString()+":";
        //Display the date
//        System.out.println(date_of_today);
        String ipAddr = IpUtils.getIpAddr(request);
        redisTemplate.opsForHyperLogLog().add(prefix+"IP", ipAddr);
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(prefix + "PV", 1);
        System.out.println("pv return得是"+aBoolean);
        if (Boolean.TRUE.equals(aBoolean)) {

        } else {
            redisTemplate.opsForValue().increment(prefix + "PV");
        }


        URI uri = new URI(request.getRequestURI());
        String path = uri.getPath();
        String query = request.getQueryString();
        String target = targetAddr + path.replace("/proxy", "");
//        String target = targetAddr + path;
        if (query != null && !query.equals("") && !query.equals("null")) {
            target = target + "?" + query;
        }
        URI newUri = new URI(target);
        // 执行代理查询
        String methodName = request.getMethod();
//        System.out.println(request);
        System.out.println(request.getRequestURI());
//        request.setCharacterEncoding("utf-8"); //获取请求body数据，先设置编码
//        InputStream is = request.getInputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        // 简单暴力 直接转换为 map对象
//        Map json = mapper.readValue(is, Map.class);
//        System.out.println("获取的json字符串转换的map:" + json);
//        System.out.println(request.getMethod());
//        System.out.println(request.getHeaderNames());
//        System.out.println(request.getAuthType());
//        System.out.println(request.getContextPath());
//        System.out.println(request.getCookies());
//        System.out.println(request.getHttpServletMapping());
//        System.out.println(request.getSession());
//        System.out.println(request.getInputStream());
        HttpMethod httpMethod = HttpMethod.resolve(methodName);
        if (httpMethod == null) {
            return;
        }
        ClientHttpRequest delegate = new SimpleClientHttpRequestFactory().createRequest(newUri, httpMethod);
        Enumeration<String> headerNames = request.getHeaderNames();
        // 设置请求头
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> v = request.getHeaders(headerName);
            List<String> arr = new ArrayList<>();
            while (v.hasMoreElements()) {
                arr.add(v.nextElement());
            }
            delegate.getHeaders().addAll(headerName, arr);
        }
//        StreamUtils.copy(is, delegate.getBody());
        StreamUtils.copy(request.getInputStream(), delegate.getBody());

        if (request.getRequestURI().equals("/proxy/api/messages/addmsg")  || request.getRequestURI().equals("/proxy/api/messages/getmsg")) {
            System.out.println("收到或发送消息");
            System.out.println(delegate.getBody());
            MessageReq messageReq = JSONObject.parseObject(delegate.getBody().toString(), MessageReq.class);
            redisTemplate.opsForSet().add(prefix + "UV", messageReq.getFrom());
//            System.out.println(messageReq.getFrom());
            Boolean member = redisTemplate.opsForSet().isMember(prefix + "UV", messageReq.getFrom());
            System.out.println("redisset里面有吗"+member);
        }
        if (request.getRequestURI().startsWith("/proxy/api/auth/setavatar") || request.getRequestURI().startsWith("/proxy/api/auth/allusers") || request.getRequestURI().startsWith("/proxy/api/auth/logout")) {
            String req = request.getRequestURI();
            String[] res = req.split("/");
            String s = res[res.length-1];
            System.out.println(s);
            redisTemplate.opsForSet().add(prefix + "UV", s);
//            System.out.println(messageReq.getFrom());
            Boolean member = redisTemplate.opsForSet().isMember(prefix + "UV", s);
            System.out.println("redisset里面有吗"+member);
        }

//        if (request.getRequestURI().equals(""))



        // 执行远程调用
        ClientHttpResponse clientHttpResponse = delegate.execute();
        response.setStatus(clientHttpResponse.getStatusCode().value());
        // 设置响应头
        clientHttpResponse.getHeaders().forEach((key, value) -> value.forEach(it -> {
            response.setHeader(key, it);
        }));
        StreamUtils.copy(clientHttpResponse.getBody(), response.getOutputStream());
    }

}
