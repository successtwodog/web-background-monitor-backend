// FetchLineData.java

package com.cg.proxyim.Entity;

import com.cg.proxyim.Utils.MockData;
import com.cg.proxyim.common.FetchDatas;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * lineResponse
 */
@lombok.Data
@ToString
public class FetchLineData {
    private Integer code;
    private ArrayList<Datum> data;
    private String[] date;
    private String msg;

//    private

    public FetchLineData getoneData(String type,String time_interval,String event_name) {
        String[] dates = new String[1];
        dates[0] = time_interval;
        this.setDate(dates);
        this.setMsg("success");
        Datum datum = new Datum();
        LineDetails lineDetails = new LineDetails();

        lineDetails.makeoneData(type,event_name);

        datum.setType(type);
        datum.setData(lineDetails);
        this.setCode(200);
        ArrayList<Datum> data1 = new ArrayList<>();
        data1.add(datum);
        this.setData(data1);
        return this;
    }

    public FetchLineData getOneTypeData(String type, String time_interval) {
        MockData mockData = new MockData();
        String[] dates = new String[1];
        dates[0] = time_interval;
        this.setDate(dates);
        this.setMsg("success");
        this.setCode(200);
        Datum datum = new Datum();
        ArrayList<Datum> data1 = new ArrayList<>();
//        LineDetails lineDetails = new LineDetails();
        FetchDatas fetchDatas = new FetchDatas();
        switch(type)
        {
            case "user" :

                for (String s : fetchDatas.getUserList()) {
                    data1.add(datum.getOne("user", s));
                }
                break;
            case "exception" :
                for (String s : fetchDatas.getExceptionList()) {
                    data1.add(datum.getOne("exception", s));
                }
                break;
            case "page" :
                for (String s : fetchDatas.getPageList()) {
                    data1.add(datum.getOne("page", s));
                }
                break;
            case "web" :
                for (String s : fetchDatas.getWebList()) {
                    data1.add(datum.getOne("web", s));
                }
                break;
            default :
                System.out.println("未知等级");
        }
        this.setData(data1);
        return this;
    }

    public FetchLineData getOneDayData(String time_interval) {
        String[] dates = new String[1];
        dates[0] = time_interval;
        this.setDate(dates);
        this.setMsg("success");
        this.setCode(200);
        Datum datum = new Datum();
        ArrayList<Datum> data1 = new ArrayList<>();

        FetchDatas fetchDatas = new FetchDatas();

        for (String s : fetchDatas.getUserList()) {
            data1.add(datum.getOne("user", s));
        }
        for (String s : fetchDatas.getExceptionList()) {
            data1.add(datum.getOne("exception", s));
        }
        for (String s : fetchDatas.getPageList()) {
            data1.add(datum.getOne("page", s));
        }
        for (String s : fetchDatas.getWebList()) {
            data1.add(datum.getOne("web", s));
        }

        this.setData(data1);
        return this;
    }
    
    public FetchLineData get7oneData(String type,String event_name) {
        MockData mockData = new MockData();
        this.setDate(mockData.get7date());
        this.setMsg("success");
        Datum datum = new Datum();
        LineDetails lineDetails = new LineDetails();

        lineDetails.makeoneData(type,event_name);

        datum.setType(type);
        datum.setData(lineDetails);
        this.setCode(200);
        ArrayList<Datum> data1 = new ArrayList<>();
        data1.add(datum);
        this.setData(data1);
        return this;
    }


    public FetchLineData get7TypeData(String type) {
        MockData mockData = new MockData();
        this.setDate(mockData.get7date());
        this.setMsg("success");
        this.setCode(200);
        Datum datum = new Datum();
        ArrayList<Datum> data1 = new ArrayList<>();
//        LineDetails lineDetails = new LineDetails();
        FetchDatas fetchDatas = new FetchDatas();
        switch(type)
        {
            case "user" :

                for (String s : fetchDatas.getUserList()) {
                    data1.add(datum.getSeven("user", s));
                }
                break;
            case "exception" :
                for (String s : fetchDatas.getExceptionList()) {
                    data1.add(datum.getSeven("exception", s));
                }
                break;
            case "page" :
                for (String s : fetchDatas.getPageList()) {
                    data1.add(datum.getSeven("page", s));
                }
                break;
            case "web" :
                for (String s : fetchDatas.getWebList()) {
                    data1.add(datum.getSeven("web", s));
                }
                break;
            default :
                System.out.println("未知等级");
        }
        this.setData(data1);
return this;
    }

    public FetchLineData getalldata() {
        MockData mockData = new MockData();
        this.setDate(mockData.get7date());
        this.setMsg("success");
        this.setCode(200);
        Datum datum = new Datum();
        ArrayList<Datum> data1 = new ArrayList<>();

        FetchDatas fetchDatas = new FetchDatas();

        for (String s : fetchDatas.getUserList()) {
            data1.add(datum.getSeven("user", s));
        }
        for (String s : fetchDatas.getExceptionList()) {
            data1.add(datum.getSeven("exception", s));
        }
        for (String s : fetchDatas.getPageList()) {
            data1.add(datum.getSeven("page", s));
        }
        for (String s : fetchDatas.getWebList()) {
            data1.add(datum.getSeven("web", s));
        }

        this.setData(data1);
        return this;
    }

}

// Datum.java


// LineDetails.java


// Empty.java


/**
 * 数据类型，如用户行为、网络等，每一个type会新建一个看板
 */
//enum Empty {
//    ERROR, NETWORK, OTHER, USER;
//
//    public String toValue() {
//        switch (this) {
//            case ERROR:
//                return "error";
//            case NETWORK:
//                return "network";
//            case OTHER:
//                return "other";
//            case USER:
//                return "user";
//        }
//        return null;
//    }
//
//    public static Empty forValue(String value) throws IOException {
//        if (value.equals("error")) return ERROR;
//        if (value.equals("network")) return NETWORK;
//        if (value.equals("other")) return OTHER;
//        if (value.equals("user")) return USER;
//        throw new IOException("Cannot deserialize Empty");
//    }
//}