package com.cg.proxyim.Entity;

// ErrorDetails.java

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * errorResponse
 */
@lombok.Data
public class ErrorDetails {
    private Integer code;
    public ArrayList<DatumElement> data;
    private String msg;
}

// DatumElement.java




