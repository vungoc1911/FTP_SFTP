package com.example.ftl_sftl.core;

import com.example.ftl_sftl.exception.ClientException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
public class FtpClientUtil {
    private static org.apache.log4j.Logger logger = Logger.getLogger(FtpClientUtil.class);
    private FTPClientPool pool;
    public FtpClientUtil(FTPClientPool pool) {
        this.pool = pool;
    }

    public void setPool(FTPClientPool pool) {
        this.pool = pool;
    }

    public int mkdirs(String path) throws Exception {
        FTPClient client = null;
        String workDirectory = null;
        try {
            client = pool.borrowObject();
            long start = System.currentTimeMillis();
            checkPath(path);
            workDirectory = client.printWorkingDirectory();
            File f = new File(workDirectory+path);
            List<String> names = new LinkedList<String>();
            while(f!=null&&f.toString().length()>0) {
                names.add(0, f.toString().replaceAll("\\\\", "/"));
                f=f.getParentFile();
            }
            for(String name:names) {
                client.makeDirectory(name);
            }
            return (int) (System.currentTimeMillis()-start);
        }catch (Exception e) {
            throw e;
        }finally {
            if (client != null) {
                client.changeWorkingDirectory(workDirectory);
                pool.returnObject(client);
            }
        }
    }

    public int store(File localFile , String path, String fileName) throws Exception {
        InputStream in = new FileInputStream(localFile);
        return store(in, path, fileName);
    }

    public int store(InputStream in, String path, String fileName) throws Exception {
        FTPClient client = null;
        String workDirectory = null;
        try {
            client = pool.borrowObject();
            workDirectory = client.printWorkingDirectory();
            checkPath(path);
            long start = System.currentTimeMillis();
            synchronized (client) {
                mkdirs(path);
                client.changeWorkingDirectory(workDirectory + path);
                client.setFileType(FTP.BINARY_FILE_TYPE);
                client.storeFile(fileName, in);
            }
            return (int) (System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw e;
        } finally {
            if (client != null) {
                client.changeWorkingDirectory(workDirectory);
                pool.returnObject(client);
            }
        }
    }

    public boolean delete(String path) throws Exception {
        FTPClient ftpClient = null;
        try {
            ftpClient = pool.borrowObject();
            return ftpClient.deleteFile(path);
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                pool.returnObject(ftpClient);
            }
        }
    }
    public int retrieve(String remote, File file) throws Exception {
        return retrieve(remote, new FileOutputStream(file));
    }
    public int retrieve(String remote, OutputStream out) throws Exception {
        InputStream in = null;
        FTPClient client = null;
        try {
            client = pool.borrowObject();
            long start = System.currentTimeMillis();
            in = client.retrieveFileStream(remote);
            if (in != null) {
                byte [] buffer = new byte[1024];
                for (int len; (len = in.read(buffer)) != -1;) {
                    out.write(buffer,0, len);
                }
            } else {
                System.out.println("tai that bai");
            }
            return (int) (System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {}
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {}
            try {
                if (client != null) {
                    pool.returnObject(client);
                }
            } catch (Exception e) {}
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
