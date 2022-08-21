package com.cg.proxyim.Entity;

import com.cg.proxyim.Utils.MockData;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * lineDetails
 */
@lombok.Data
@ToString
public class LineDetails {
    private int[] data;
    private String eventDescirption;
    private String eventName;
    private String units;
//    private HashMap<String,String> eventMap= new HashMap<String,String>(){
//        {
//            put("PV","页面浏览量");
//            put("UV", "独立访客");
//            put("IP", "独立IP数");
//        }
//    };

//    public LineDetails makeUserData(String eventName) {
//        this.setEventName(eventName);
//        this.setEventDescirption(eventMap.get(eventName));
//        MockData mockData = new MockData();
//        this.setData(mockData.get7int());
//        this.setUnits("times");
//        return this;
//    }

    public LineDetails makeoneData(String type,String event_name) {
        LineDetails lineDetails = new LineDetails();
        MockData mockData = new MockData();
        if (type.equals("user")) {
            lineDetails.setUnits("times");
        } else {
            lineDetails.setUnits("ms");
        }
        lineDetails.setEventName(event_name);
        int[] data = new int[7];
        data[0] = mockData.get1int();
        lineDetails.setData(data);
        lineDetails.setEventDescirption("描述");
        return lineDetails;
    }

    public LineDetails make7Data(String type,String event_name) {
        LineDetails lineDetails = new LineDetails();
        MockData mockData = new MockData();
        if (type.equals("user")) {
            lineDetails.setUnits("times");
        } else {
            lineDetails.setUnits("ms");
        }
        lineDetails.setEventName(event_name);

        lineDetails.setData(mockData.get7int());
        lineDetails.setEventDescirption("描述");
        return lineDetails;
    }


}
