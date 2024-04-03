package com.example.ftl_sftl;

import com.example.ftl_sftl.config.SFTPConfig;
import com.example.ftl_sftl.core.sftp.SFTPClientFactory;
import com.example.ftl_sftl.core.sftp.SFTPClientPool;
import com.example.ftl_sftl.core.sftp.SFTPClientUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class FtlSftlApplication {

	public static void main(String[] args) throws Exception {

//		FtpClient ftpClient = new FtpClient();
//		ftpClient.connect();
		SpringApplication.run(FtlSftlApplication.class, args);
//		FtpPoolConfig config = new FtpPoolConfig();
//		FTPClientFactory factory = new FTPClientFactory(config);
//		FTPClientPool pool = new FTPClientPool(factory);
//		FtpClientUtil ftpClientUtil = new FtpClientUtil(pool);
//		ftpClientUtil.mkdirs("/test2/");
//		File localFile = new File("C:/Users/vungo/Documents/JS.docx") ;
//		ftpClientUtil.store( localFile, "/test2/", "JS.docx");

//		String remoteFilePath = "/test2/JS.docx"; // Đường dẫn tệp trên máy chủ FTP
//		String localFilePath = "G:/PROJECT_CA_NHAN/FTP_SFTP/JS.docx"; // Đường dẫn lưu trữ tệp tải về
//		File localFile = new File(localFilePath);
//		ftpClientUtil.retrieve(remoteFilePath, localFile);
//		ftpClientUtil.delete("/test2/JS.docx");


		SFTPConfig config = new SFTPConfig();
		SFTPClientFactory factory = new SFTPClientFactory(config);
		SFTPClientPool pool = new SFTPClientPool(factory);
		SFTPClientUtil client = new SFTPClientUtil(pool);
//		sftpClient1.connect();
//		client.mkdirs("/test/");
//		File localFile = new File("C:/Users/ngocvt20.FSOFT.FPT.VN/Documents/DeNghiCapVTTB.vm");
//		client.store(localFile, "/test/", "DeNghiCapVTTB.vm");

//		String remote = "/home/mobaxterm/test/DeNghiCapVTTB.vm";
//		String localFile = "C:/Users/ngocvt20.FSOFT.FPT.VN/Documents/DeNghiCapVTTB.vm";
//		File file = new File(localFile);
//		client.retrieve(remote, file);
		client.delete("/home/mobaxterm/test/DeNghiCapVTTB.vm");
	}

}
