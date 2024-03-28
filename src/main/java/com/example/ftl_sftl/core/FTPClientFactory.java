package com.example.ftl_sftl.core;

import com.example.ftl_sftl.config.FtpPoolConfig;
import com.example.ftl_sftl.exception.ClientException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.log4j.Logger;

public class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {
    private static Logger logger = Logger.getLogger(FTPClientFactory.class);

    private FtpPoolConfig ftpPoolConfig;
    public FTPClientFactory(FtpPoolConfig config) {
        this.ftpPoolConfig = config;
    }

    public FtpPoolConfig getFtpPoolConfig() {
        return ftpPoolConfig;
    }

    public  void setFtpPoolConfig(FtpPoolConfig ftpPoolConfig) {
        this.ftpPoolConfig = ftpPoolConfig;
    }
    @Override
    public FTPClient create() throws Exception {
        FTPClient ftpClient = new FTPSClient();
        ftpClient.setConnectTimeout(5000);
        try {
            ftpClient.connect(ftpPoolConfig.getHost(), ftpPoolConfig.getPort());
            int reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.error("connect thất bại");
                return null;
            }
            boolean result = ftpClient.login(ftpPoolConfig.getUsername(), ftpPoolConfig.getPassword());
            if (!result) {
                logger.error("sai tk mk");
            }

            ftpClient.setControlEncoding(ftpPoolConfig.getControlEncoding());
            ftpClient.setBufferSize(ftpPoolConfig.getBufferSize());
            ftpClient.setFileType(ftpPoolConfig.getFileType());
            ftpClient.setDataTimeout(ftpPoolConfig.getConnectTimeOut());
            ftpClient.setUseEPSVwithIPv4(ftpPoolConfig.isUseEPSVwithIPv4());
            ftpClient.enterLocalPassiveMode();
        } catch (Exception e) {
            throw new ClientException("fail", e);
        }
        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<FTPClient>(ftpClient);
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
       FTPClient ftpClient = p.getObject();
       ftpClient.logout();
       super.destroyObject(p);
    }
}
