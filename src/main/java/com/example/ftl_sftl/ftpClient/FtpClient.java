package com.example.ftl_sftl.ftpClient;

import com.example.ftl_sftl.client.Client;
import com.example.ftl_sftl.exception.ClientException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
@Service
public class FtpClient implements Client {
    FTPClient ftpClient;
    @Override
    public void connect() {
        try {
            ftpClient = new FTPClient();
            ftpClient.connect("192.168.0.102", 21);
            verify();
            ftpClient.login("admin", "admin");
            verify();
        } catch (Exception e) {
            throw new ClientException("Failed to create", e);
        }
    }

    @Override
    public int store(InputStream in, String path, String fileName) throws Exception {
        String workDirectory = null;
        return 0;
    }

    private void verify() throws IOException {
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            throw new IOException("fail");
        }
    }
}
