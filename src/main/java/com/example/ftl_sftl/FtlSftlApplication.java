package com.example.ftl_sftl;

import com.example.ftl_sftl.config.FtpPoolConfig;
import com.example.ftl_sftl.core.FTPClientFactory;
import com.example.ftl_sftl.core.FTPClientPool;
import com.example.ftl_sftl.core.FtpClientUtil;
import com.example.ftl_sftl.ftpClient.FtpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SpringBootApplication
public class FtlSftlApplication {

	public static void main(String[] args) throws Exception {

//		FtpClient ftpClient = new FtpClient();
//		ftpClient.connect();
		SpringApplication.run(FtlSftlApplication.class, args);
		FtpPoolConfig config = new FtpPoolConfig();
		FTPClientFactory factory = new FTPClientFactory(config);
		FTPClientPool pool = new FTPClientPool(factory);
		FtpClientUtil ftpClientUtil = new FtpClientUtil(pool);
//		ftpClientUtil.mkdirs("/test2/");
//		File localFile = new File("C:/Users/vungo/Documents/JS.docx") ;
//		ftpClientUtil.store( localFile, "/test2/", "JS.docx");

//		String remoteFilePath = "/test2/JS.docx"; // Đường dẫn tệp trên máy chủ FTP
//		String localFilePath = "G:/PROJECT_CA_NHAN/FTP_SFTP/JS.docx"; // Đường dẫn lưu trữ tệp tải về
//		File localFile = new File(localFilePath);
//		ftpClientUtil.retrieve(remoteFilePath, localFile);
		ftpClientUtil.delete("/test2/JS.docx");
	}

}
