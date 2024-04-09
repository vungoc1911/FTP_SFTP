package com.example.ftl_sftl.core.telnet;

import com.example.ftl_sftl.config.TelnetConfig;
import com.example.ftl_sftl.core.sftp.SFTPClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.log4j.Logger;


public class TelnetClientFactory extends BasePooledObjectFactory<TelnetClient> {
    private static Logger logger = Logger.getLogger(SFTPClientFactory.class);
    private TelnetConfig config;
    public TelnetClientFactory(TelnetConfig config) {
        this.config = config;
    }
    public TelnetConfig getConfig() {
        return config;
    }
    public void setConfig(TelnetConfig config) {
        this.config = config;
    }
    @Override
    public TelnetClient create() throws Exception {
        try {
            TelnetClient client = new TelnetClient();
            client.connect(config.getHost(), config.getPort());
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    @Override
    public PooledObject<TelnetClient> wrap(TelnetClient telnetClient) {
        return new DefaultPooledObject<>(telnetClient);
    }

    @Override
    public void destroyObject(PooledObject<TelnetClient> p, DestroyMode destroyMode) throws Exception {
        TelnetClient telnetClient = p.getObject();
        telnetClient.disconnect();
        super.destroyObject(p, destroyMode);
    }
}
