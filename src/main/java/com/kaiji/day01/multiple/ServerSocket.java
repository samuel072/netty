package com.kaiji.day01.multiple;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多个线程的socket服务器端
 *
 * @author kaiji
 * @since 2018-10-23
 */
public class ServerSocket {

    public static void main(String[] args) {
    	 java.net.ServerSocket socketServer = null;
    	try {
    		// 创建线程池
            ExecutorService executorService = Executors.newCachedThreadPool();

            socketServer = new java.net.ServerSocket(10010);

            while (true) {
                final Socket socket = socketServer.accept();
                System.out.println("开启一个新的客户端!");
                executorService.execute(new Runnable() {
                    public void run() {
                        // 获取客户端消息
                        getMsg(socket);
                        // 给客户端发消息
                        sendMsg(socket);
                    }
                });
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (socketServer != null) {
					socketServer.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
        
    }

    private static void sendMsg(Socket socket) {

    }

    private static void getMsg(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            // 获取客户端传来的消息的字节流
            InputStream inputStream = socket.getInputStream();
            int len = 0;
            while (true) {
                // 读取消息
                if ((len = inputStream.read(bytes)) != -1) {
                    String msg = new String(bytes, 0, len);
                    System.out.println("客户端消息:" + msg);
                    if ("ok".equals(msg)) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器端异常:" + e.getMessage());
        } finally {

            if (socket != null) {
                System.out.println("关闭服务器端!");
            }
        }

    }
}
