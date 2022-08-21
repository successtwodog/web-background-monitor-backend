package com.cg.proxyim.Entity;

@lombok.Data
public class Datum {
    private LineDetails data;
    private String type;

    public Datum getOne(String type, String event_name) {
        LineDetails lineDetails = new LineDetails();
        Datum datum = new Datum();
        datum.setType(type);
        datum.setData(lineDetails.makeoneData(type, event_name));
        return datum;
    }

    public Datum getSeven(String type, String event_name) {
        LineDetails lineDetails = new LineDetails();
        Datum datum = new Datum();
        datum.setType(type);
        datum.setData(lineDetails.make7Data(type,event_name));
        return datum;
    }



}
