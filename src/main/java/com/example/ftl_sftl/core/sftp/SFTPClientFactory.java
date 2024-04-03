package com.example.ftl_sftl.core.sftp;

import com.example.ftl_sftl.config.SFTPConfig;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;

import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.log4j.Logger;

public class SFTPClientFactory extends BasePooledObjectFactory<ChannelSftp> {
    private static Logger logger = Logger.getLogger(SFTPClientFactory.class);

    private SFTPConfig config;
    public SFTPClientFactory(SFTPConfig config) {
        this.config = config;
    }
    public SFTPConfig getConfig() {
        return config;
    }
    public void setConfig(SFTPConfig config) {
        this.config = config;
    }
    Session session;
    @Override
    public ChannelSftp create() throws Exception {
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setPassword(config.getPassword());
            session.setTimeout(config.getTimeout());
            session.connect();
            Channel channel = session.openChannel(config.getChannel());
            channel.connect();
            return (ChannelSftp) channel;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }

    @Override
    public void destroyObject(PooledObject<ChannelSftp> p, DestroyMode destroyMode) throws Exception {
        ChannelSftp channelSftp = p.getObject();
        channelSftp.exit();
        super.destroyObject(p, destroyMode);
    }
}
