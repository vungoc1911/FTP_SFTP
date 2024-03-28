package com.example.ftl_sftl.client;

import java.io.InputStream;
import java.io.ObjectInputFilter;
import java.lang.module.Configuration;

public interface Client {
    void connect();
    int store(InputStream in, String path, String fileName) throws Exception;
}
