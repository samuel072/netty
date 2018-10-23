package com.kaiji.day01.single;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * socket 客户端
 */
public class SocketClient {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            // 创建一个客户端, ip地址 localhost 端口号: 10019
            socket = new Socket("localhost", 10019);
            String serverMsg = "";
            while (true) {
//                // 读取从服务器端接收的字符流
//                InputStream inputStream = socket.getInputStream();
//                int len = 0;
//                byte[] buffer = new byte[1024];
//                if ((len = inputStream.read(buffer)) != -1) {
//                    serverMsg = new String(buffer, 0, len);
//                    System.out.println("服务器端消息:" + serverMsg);
//                }

                System.out.println("请说出要对服务器的话: ");
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                // 获取输出流
                OutputStream outputStream = socket.getOutputStream();
                // 发送消息给服务器端
//              String msg = "hello server, i am a socket client!";
                outputStream.write(("给服务器说的话:" + msg).getBytes());

                if (null != serverMsg && !"".equals(serverMsg) && "ok".equals(serverMsg)) {
                    // socket 断开连接
                    System.out.println("客户端断开连接!");
                    break;
                }
            }

            if (null != socket) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
