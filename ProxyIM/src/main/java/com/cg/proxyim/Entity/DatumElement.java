package com.cg.proxyim.Entity;

import lombok.ToString;

import java.time.OffsetDateTime;

/**
 * errorDetails
 */
@lombok.Data
@ToString
public class DatumElement {
    private String deviceid;
    private String errorBundle;
    private String errorMsg;
    private String errorReason;
    private String errorType;
    private String osType = "windows";
    private String osVersion = "xp";
    private OffsetDateTime time;
    private String userid;
    private String userip;
}
