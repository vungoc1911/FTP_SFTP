package com.example.ftl_sftl.core.telnet;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

@Service
public class TelnetClientUtil {
    private final TelnetClient telnetClient;
    private InputStream inputStream;
    private PrintStream outputStream;

    public TelnetClientUtil() {
        telnetClient = new TelnetClient();
    }

    public String sendCommand(String serverAddress, int port, String command) throws IOException {
        try {
            telnetClient.connect(serverAddress, port);
            inputStream = telnetClient.getInputStream();
            outputStream = new PrintStream(telnetClient.getOutputStream());

            outputStream.println(command);
            outputStream.flush();
            String response = readResponse(); // Store the response
            System.out.println(response); // Print the stored response
            return response; // Return the stored response
        } finally {
            if (telnetClient.isConnected()) {
                telnetClient.disconnect();
            }
        }
    }

    private String readResponse() throws IOException {
        StringBuilder response = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            response.append(new String(buffer, 0, bytesRead));
        }
        return response.toString();
    }
    public void disconnect() throws IOException {
        telnetClient.disconnect();
    }
}
