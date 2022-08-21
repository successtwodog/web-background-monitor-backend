package com.cg.proxyim.Controller;

import com.alibaba.fastjson.JSONObject;
import com.cg.proxyim.Entity.MessageReq;
import com.cg.proxyim.Utils.IpUtils;
import com.cg.proxyim.Utils.ReqFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RestController
public class SocketioController {
    @Value("${target-node}")
    private String targetAddr ;
    @RequestMapping(value = "/socket.io", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void proxy(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {



        LocalDate date_of_today = LocalDate.now();
        String prefix=date_of_today.toString()+":";
        //Display the date
//        System.out.println(date_of_today);


        URI uri = new URI(request.getRequestURI());
        String path = uri.getPath();
        String query = request.getQueryString();
//        String target = targetAddr + path.replace("/proxy", "");
        String target = targetAddr + path;
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
