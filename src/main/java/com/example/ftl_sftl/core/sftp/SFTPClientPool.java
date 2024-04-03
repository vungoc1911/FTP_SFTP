package com.example.ftl_sftl.core.sftp;

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.log4j.Logger;

public class SFTPClientPool {
    private static Logger logger = Logger.getLogger(SFTPClientPool.class);
    private GenericObjectPool<ChannelSftp> pool;
    private SFTPClientFactory clientFactory;

    public SFTPClientPool(SFTPClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        pool = new GenericObjectPool<ChannelSftp>(clientFactory, clientFactory.getConfig());
    }

    public SFTPClientFactory getClientFactory() {
        return clientFactory;
    }
    public GenericObjectPool<ChannelSftp> getPool() {
        return pool;
    }

    public ChannelSftp borrowObject() throws Exception {
        ChannelSftp client = pool.borrowObject();
        return client;
    }

    public void returnObject(ChannelSftp channelSftp) {
        if (channelSftp != null) {
            pool.returnObject(channelSftp);
        }
    }
}
