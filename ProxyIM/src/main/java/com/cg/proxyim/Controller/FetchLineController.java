package com.cg.proxyim.Controller;

import com.cg.proxyim.Entity.Datum;
import com.cg.proxyim.Entity.FetchLineData;
import com.cg.proxyim.Entity.LineDetails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
public class FetchLineController {
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/api/charts/line")
    @ResponseBody
    public FetchLineData fetchLine(@RequestParam(required = false) String type, @RequestParam(required = false) String time_interval, @RequestParam(required = false) String event_name) {
        FetchLineData fetchLineData = new FetchLineData();
        if (time_interval != null) {
            if (type != null) {

                if (event_name != null) {
                    fetchLineData.getoneData(type, time_interval, event_name);
                } else {
                    fetchLineData.getOneTypeData(type, time_interval);
                }

            } else {
                fetchLineData.getOneDayData(time_interval);
            }

        } else {
            if (type != null) {

                if (event_name != null) {
                    fetchLineData.get7oneData(type, event_name);
                } else {
                    fetchLineData.get7TypeData(type);
                }

            } else {
                fetchLineData.getalldata();
            }
            fetchLineData.getalldata();
        }
        return fetchLineData;


//        LocalDate date_of_today = LocalDate.now();
//        String prefix=date_of_today.toString()+":";
//        String pv = (String) redisTemplate.opsForValue().get(prefix + "PV");
//        Long size = redisTemplate.opsForSet().size(prefix + "UV");
//
//        Long ip = redisTemplate.opsForHyperLogLog().size(prefix + "IP");
////        System.out.println(pv);
////        System.out.println(size);
////        System.out.println(ip);
//        LineDetails pvDetails = new LineDetails();
//        pvDetails.setUnits("times");








    }

}
