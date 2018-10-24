package com.kaiji.day01.multiple;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerClient {

    public static void main(String[] args) {

        Socket socket = null;
        Scanner scanner = null;

        try {
            socket = new Socket("localhost", 10010);
            while (true) {

                System.out.println("请输入对服务器消息:");
                scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                // 获取发送消息字节流
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(msg.getBytes());

                if ("ok".equals(msg)) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端异常: " + e.getMessage());
        } finally {
        	if (scanner != null) {
        		scanner.close();
        	}
            if (socket != null) {
                try {
                    System.out.println("关闭客户端");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
