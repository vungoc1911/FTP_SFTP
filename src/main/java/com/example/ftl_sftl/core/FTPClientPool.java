package com.example.ftl_sftl.core;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class FTPClientPool {
    private GenericObjectPool<FTPClient> pool;
    private FTPClientFactory clientFactory;

    public FTPClientPool(FTPClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        pool = new GenericObjectPool<FTPClient>(clientFactory, clientFactory.getFtpPoolConfig());
    }

    public FTPClientFactory getClientFactory() {
        return clientFactory;
    }

    public GenericObjectPool<FTPClient> getPool() {
        return pool;
    }

    public FTPClient borrowObject() throws Exception {
        FTPClient client = pool.borrowObject();
        return client;
    }

    public void returnObject(FTPClient ftpClient) {
        if (ftpClient != null) {
            pool.returnObject(ftpClient);
        }
    }
}
