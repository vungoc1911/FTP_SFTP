package com.example.ftl_sftl.core.sftp;

import com.example.ftl_sftl.core.ftp.FtpClientUtil;
import com.jcraft.jsch.ChannelSftp;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class SFTPClientUtil {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SFTPClientUtil.class);
    private SFTPClientPool pool;
    public SFTPClientUtil (SFTPClientPool pool) {
        this.pool = pool;
    }

    public void setPool(SFTPClientPool pool) {
        this.pool = pool;
    }

    public int mkdirs(String path) throws Exception {
        ChannelSftp channel = null;
        String workDirectory = null;
        try {
            channel = pool.borrowObject();
            long start = System.currentTimeMillis();
            checkPath(path);
            workDirectory = channel.pwd();
            File f = new File(workDirectory + path);
            List<String> names = new LinkedList<String>();
            while (f != null && f.toString().length() > 0) {
                names.add(0, f.toString().replaceAll("\\\\", "/"));
                f = f.getParentFile();
            }
            for (String name : names) {
                if (!exists(channel, name)) {
                    channel.mkdir(name);
                }
            }
            return (int) (System.currentTimeMillis()-start);
        } catch (Exception e) {
            throw e;
        } finally {
            if (channel != null) {
                pool.returnObject(channel);
            }
        }
    }

    public int store(File localFile, String path, String fileName) throws Exception {
        InputStream in = new FileInputStream(localFile);
        return store(in, path, fileName);
    }
    public int store(InputStream in, String path, String fileName) throws Exception {
        String workDirectory = null;
        ChannelSftp channel = null;
        try {
            channel = pool.borrowObject();
            long start = System.currentTimeMillis();
            checkPath(path);
            workDirectory = channel.pwd();
            synchronized (channel) {
                mkdirs(path);
                channel.cd(workDirectory + path);
                channel.put(in, fileName);
            }
            return (int) (System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw e;
        } finally {
            if (channel != null) {
                pool.returnObject(channel);
            }
        }
    }

    public int retrieve(String remote, File file) throws Exception {
        return retrieve(remote, new FileOutputStream(file));
    }
    public int retrieve(String remote, OutputStream out) throws Exception {
        ChannelSftp chanel = null;
        try {
            chanel = pool.borrowObject();
            long start = System.currentTimeMillis();
            InputStream in = chanel.get(remote);
            if (in != null) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
            return (int) (System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {}
            try {
                if (chanel != null) {
                    pool.returnObject(chanel);
                }
            } catch (Exception e) {}
        }
    }

    public boolean delete(String path) throws Exception {
        ChannelSftp channel = null;
        try {
            channel = pool.borrowObject();
            channel.rm(path);
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (channel != null) {
                pool.returnObject(channel);
            }
        }
    }
    private boolean exists(ChannelSftp channel, String path) {
        try {
            channel.lstat(path);
            return true;
        } catch (Exception e) {
            return false;
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
