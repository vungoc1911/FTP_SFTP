package com.example.ftl_sftl.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

@Data
public class SFTPConfig extends GenericObjectPoolConfig {
    private String host = "10.15.175.20";
    private int port = 22;
    private String username = "ngocvt20";
    private String password = "Banhcaytb@1234567";
    private int timeout = 120000;
    private String channel = "sftp";
}
