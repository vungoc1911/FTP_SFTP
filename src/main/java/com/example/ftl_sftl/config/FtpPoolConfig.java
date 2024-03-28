package com.example.ftl_sftl.config;

import com.example.ftl_sftl.ftpClient.FtpClient;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class FtpPoolConfig extends GenericObjectPoolConfig<FtpClient> {

    private String host;
    private int port = 21;
    private String username;
    private String password;
    private int connectTimeOut = 5000;
    private String controlEncoding = "utf-8";
    private int bufferSize = 1024;
    private int fileType = 2;
    private int dataTimeout = 120000;
    private boolean useEPSVwithIPv4 = false;
    private boolean passiveMode = true;
    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getControlEncoding() {
        return controlEncoding;
    }

    public void setControlEncoding(String controlEncoding) {
        this.controlEncoding = controlEncoding;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getDataTimeout() {
        return dataTimeout;
    }

    public void setDataTimeout(int dataTimeout) {
        this.dataTimeout = dataTimeout;
    }

    public boolean isUseEPSVwithIPv4() {
        return useEPSVwithIPv4;
    }

    public void setUseEPSVwithIPv4(boolean useEPSVwithIPv4) {
        this.useEPSVwithIPv4 = useEPSVwithIPv4;
    }

    public boolean isPassiveMode() {
        return passiveMode;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }
}
