package com.example.ftl_sftl.core.telnet;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.SocketTimeoutException;

@Service
public class TelnetClientUtil {
    private final String telnetServerHost = "10.15.175.20";
    private final int telnetServerPort = 23;

    public void connectAnExecuteCommand(String command) {
        try {
            TelnetClient telnetClient = new TelnetClient();
            telnetClient.connect(telnetServerHost, telnetServerPort);
            telnetClient.setSoTimeout(100000);

            InputStream inputStream = telnetClient.getInputStream();
            PrintStream outputStream = new PrintStream(telnetClient.getOutputStream());

            sendCommand(command, outputStream);
            String response = readResponse(inputStream);

            handleResponse(response);

            telnetClient.disconnect();
        } catch (SocketTimeoutException e) {
            handleTimeout();
        } catch (IOException e) {
            handleIOError(e);
        }
    }

    private String readResponse(InputStream inputStream) throws IOException {
       BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
       StringBuilder response = new StringBuilder();
       String line;
       while ((line = reader.readLine()) != null) {
           response.append(line).append("\n");
           if (line.contains("/home/mobaxterm")) {
               break; // Kết thúc việc đọc dữ liệu
           }
       }
       reader.close();
       return response.toString();

//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        StringBuilder response = new StringBuilder();
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//            response.append(new String(buffer, 0, bytesRead));
//        }
//        return response.toString();
    }
    private void sendCommand(String command, PrintStream outputStream) {
        outputStream.println("");
        outputStream.flush();

        outputStream.println(command);
        outputStream.flush();
    }
    private void handleResponse(String response) {
        System.out.println("Phản hồi từ máy chủ Telnet: " + response);
    }
    private void handleTimeout() {
        System.err.println("Hết thời gian chờ khi đợi phản hồi từ máy chủ Telnet.");
    }

    private void handleIOError(IOException e) {
        e.printStackTrace();
    }
}
