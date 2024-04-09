package com.example.ftl_sftl.core.telnet;

import com.example.ftl_sftl.core.sftp.SFTPClientPool;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.log4j.Logger;

public class TelnetClientPool {
    private static Logger logger = Logger.getLogger(SFTPClientPool.class);
    private GenericObjectPool<TelnetClient> pool;
    private TelnetClientFactory clientFactory;

    public TelnetClientPool(TelnetClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        pool = new GenericObjectPool<TelnetClient>(clientFactory, clientFactory.getConfig());
    }
}
