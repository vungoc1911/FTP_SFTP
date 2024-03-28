package com.example.ftl_sftl.controller;

import com.example.ftl_sftl.ftpClient.FtpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common-spro")
public class FtpController {


    private final FtpClient ftpClient;
    @GetMapping("/test-connect")
    public ResponseEntity<?> getAllUser() {
        ftpClient.connect();
        return new ResponseEntity<>("Connected to FTP server", HttpStatus.OK);
    }
}
