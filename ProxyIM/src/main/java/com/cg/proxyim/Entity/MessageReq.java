package com.cg.proxyim.Entity;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class MessageReq {
    String from;
    String to;
    String message;
}
