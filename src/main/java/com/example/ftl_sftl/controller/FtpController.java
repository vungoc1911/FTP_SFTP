package com.example.ftl_sftl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common-spro")
public class FtpController {


//    private final FtpClient ftpClient;
//    private final FtpClientUtil clientUtil;
//    @GetMapping("/test-connect")
//    public ResponseEntity<?> getAllUser(@RequestParam String path) throws Exception {
//        clientUtil.mkdirs(path);
//        return new ResponseEntity<>("Connected to FTP server", HttpStatus.OK);
//    }
}
