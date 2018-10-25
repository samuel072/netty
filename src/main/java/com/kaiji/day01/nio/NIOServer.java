package com.kaiji.day01.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

	private static Selector selector;
	
	public static void main(String[] args) throws IOException {
		// 第一步: 获取ServerSocket通道服务 
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 第二步: 设置该通道为非阻塞通道
		serverSocketChannel.configureBlocking(false);
		// 第三步: 绑定端口号
		serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 10010));
		// 第四步: 获取通道管理器
		selector = Selector.open();
		// 第五步: 将selector注册到ServerSocket服务通道中, 目的是为了监听该通道 设置连接权限
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		// 第六步: 轮询selector
		System.out.println("开启一个服务器!");
		while (true) {
			// 当注册事件到了的时候, 方法返回; 否则会一直阻塞
			selector.select();
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				// 开始业务处理
				handler(key);
			}
		}
	}

	/**
	 * 现在开始处理业务
	 * 
	 * @param key
	 * 		选择器的key		
	 * @throws IOException 
	 * 		IO异常
	 */
	private static void handler(SelectionKey key) throws IOException {
		// 连接事件
		if (key.isAcceptable()) { 
			handlerAccept(key);
		} else if (key.isReadable()) { // 读写事件
			handlerRead(key);
		}
	}

	/**
	 * 处理读写业务
	 * 
	 * @param key
	 * 		选择器的key
	 * @throws IOException 
	 * 		IO异常
	 */
	private static void handlerRead(SelectionKey key) throws IOException {
		// 获取客户端通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建缓存通道
		ByteBuffer dst = ByteBuffer.allocate(1024);
		// 读取缓存
		channel.read(dst);
		// 将缓存转换成String字符串
		String msg = new String(dst.array());
		System.out.println("接收客户端消息:" + msg);
		
		// 发送消息给客户端
		ByteBuffer byteBuffer = ByteBuffer.wrap("发送信息给客户端".getBytes());
		channel.write(byteBuffer);
	}

	/**
	 * 处理连接业务
	 * 
	 * @param key
	 * 		选择器的key
	 * @throws IOException 
	 */
	private static void handlerAccept(SelectionKey key) throws IOException {
		// 获取服务器通道管理
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		// 连接客户端
		SocketChannel channel = server.accept();
		// 设置成非阻塞模式
		channel.configureBlocking(false);
		// 将选择器注册到客户端, 设置读客户端权限
		channel.register(selector, SelectionKey.OP_READ);
	}
}
