package com.cg.proxyim.Controller;

import com.cg.proxyim.Utils.IpUtils;
import com.cg.proxyim.Utils.MockData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public int[] hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding();
        LocalDate date_of_today = LocalDate.now();

        //Display the date
//        System.out.println(date_of_today);
//        return date_of_today.toString();

//        Map<String, Object> lineDetailmap = new HashMap<>();
        MockData mockData = new MockData();

//        System.out.println(mockData.get7date());
//        System.out.println();
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter writer = response.getWriter();
//        writer.println("日期:" + mockData.get7date() + "<br/>" +
//                "数字:" + mockData.get7int() + "<br/>"
////                "应用名字（上下文）:" + request.getContextPath() + "<br/>" +
////                "URI:" + request.getRequestURI() + "<br/>" +
////                "请求字符串:" + request.getQueryString() + "<br/>" +
////                "Servlet所映射的路径:" + request.getServletPath() + "<br/>" +
////                "客户端的完整主机名:" + request.getRemoteHost() + "<br/>" +
////                "自己算的ip地址:" + IpUtils.getIpAddr(request) +
////                "参数" + request.getQueryString()
//        );

        return mockData.get7int();

    }
}
