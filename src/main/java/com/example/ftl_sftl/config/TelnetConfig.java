package com.example.ftl_sftl.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

@Data
public class TelnetConfig extends GenericObjectPoolConfig {
    private String host = "10.15.175.20";
    private int port = 23;
}
