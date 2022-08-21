package com.cg.proxyim.common;

import java.util.ArrayList;

public class FetchDatas {
    private ArrayList<String> userList;
    private ArrayList<String> exceptionList;
//    private ArrayList<String> frontendList;
    private ArrayList<String> pageList;
    private ArrayList<String> webList;

    public ArrayList<String> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<String> userList) {
        this.userList = userList;
    }

    public ArrayList<String> getExceptionList() {
        return exceptionList;
    }

    public void setExceptionList(ArrayList<String> exceptionList) {
        this.exceptionList = exceptionList;
    }

    public ArrayList<String> getPageList() {
        return pageList;
    }

    public void setPageList(ArrayList<String> pageList) {
        this.pageList = pageList;
    }

    public ArrayList<String> getWebList() {
        return webList;
    }

    public void setWebList(ArrayList<String> webList) {
        this.webList = webList;
    }

    public FetchDatas() {
        userList.add("PV");
        userList.add("UV");
        userList.add("IP");

        exceptionList.add("InterfaceException");
        exceptionList.add("frontException");

        pageList.add("FP");
        pageList.add("FCP");
        pageList.add("LCP");
        pageList.add("FMP");
        pageList.add("DCL");
        pageList.add("L");
        pageList.add("TTI");
        pageList.add("FID");

        webList.add("dns_lookup_time");
        webList.add("tcp_connect_time");
        webList.add("ssl_connect_time");
        webList.add("ttfb_request_time");
        webList.add("data_transfer_time");
        webList.add("resource_load_time");
        webList.add("redirect_time");

    }
}
