package com.example.ftl_sftl.core;

import com.example.ftl_sftl.client.Client;
import com.example.ftl_sftl.exception.ClientException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SFTPClient implements Client {
    Session session;
    ChannelSftp channelSftp;

    @Override
    public void connect()  {
        try {
            JSch sch = new JSch();
            session = sch.getSession("ngocvt20", "10.15.175.20", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setPassword("Banhcaytb@1234567");
            session.setTimeout(120000);
            session.connect();
            Channel ch = session.openChannel("sftp");
            ch.connect();
            this.channelSftp = (ChannelSftp) ch;
        } catch (Exception e) {
            throw new ClientException("fail", e);
        }
    }

    public int mkdirs(String path) throws Exception {
        connect();
        String workDirectory = null;
        try {
            checkPath(path);
            long start = System.currentTimeMillis();
            workDirectory = channelSftp.pwd();
            File f = new File(workDirectory + path);
            List<String> names = new LinkedList<String>();
            while (f != null && f.toString().length() > 0) {
                names.add(0, f.toString().replaceAll("\\\\", "/"));
                f = f.getParentFile();
            }
            for (String name : names) {
                try {
                    channelSftp.mkdir(name);
                } catch (Exception e) {
                    // Ignore the exception if the directory already exists
                    if (!"Failure".equals(e.getMessage())) {
                        throw e;
                    }
                }
            }
            return (int) (System.currentTimeMillis()-start);
        } catch (Exception e) {
            throw e;
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
    private static void checkPath(String path) {
        if (path.contains("\\")) {
            throw new RuntimeException("'\\' không hợp lệ, hãy sử dung '/'");
        }
        if (!path.startsWith("/")) {
            throw new RuntimeException("hãy sử dung '/'");
        }
        if (!path.endsWith("/")) {
            throw new RuntimeException("đừng kết thúc vời '/'");
        }
    }
}
