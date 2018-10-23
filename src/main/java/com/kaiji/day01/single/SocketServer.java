package com.kaiji.day01.single;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket 服务器端
 *
 * @author kaiji
 * @since 2018-10-23
 */
public class SocketServer {


    public static void main(String[] args) throws Exception {
        // 监听 10019端口
        ServerSocket serverSocket = new ServerSocket(10019);
        while(true) {
            Socket socket = serverSocket.accept();
            System.out.println("启动了一个客户端");
            handler(socket);
        }
    }

    private static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int len = 0;
                if ((len = inputStream.read(bytes)) != -1) {
                    String msg = new String(bytes, 0, len);
                    System.out.println("接收到的客户端消息: " + msg);
                    if ("给服务器说的话:ok".equals(msg)) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
