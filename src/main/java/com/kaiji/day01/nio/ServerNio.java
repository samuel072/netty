package com.kaiji.day01.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 优化后的NIOServer
 * 
 * @author kaiji
 * @version 1.1
 */
public class ServerNio {

	private static Selector selector;
	
	public static void main(String[] args) throws IOException {
		// 初始化NIO服务器, 绑定地址服务器, 端口号
		init("127.0.0.1", 10010);
		// 初始化监听器
		initLister();
	}

	/**
	 * 初始化NIO服务器
	 * 
	 * @param hostname
	 * 		监听的服务器地址
	 * @param port
	 * 		监听的服务器端口号
	 * @throws IOException
	 * 		IO异常
	 */
	private static void init(String hostname, int port) throws IOException {
		// 第一步: 获取服务器通道
		ServerSocketChannel server = ServerSocketChannel.open();
		// 第二步: 设置为非阻塞模式 
		server.configureBlocking(false);
		// 第三步: 绑定地址和端口号
		server.socket().bind(new InetSocketAddress(hostname, port));
		// 第四步: 获取选择器
		selector = Selector.open();
		// 第五步: 将服务器端通道注册到选择器中, 并设置连接权限
		server.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	/**
	 * 初始化监听器
	 * @throws IOException
	 */
	private static void initLister() throws IOException {
		System.out.println("开启服务器端!");
		while (true) {
			// 第一步: 连接监听事件
			selector.select();
			// 第二步: 遍历监听器里面key
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				// 处理业务
				handlerBusiness(key);
			}
		}
	}

	/**
	 * 处理监听器分发
	 * 
	 * @param key
	 * 		监听器的key
	 * @throws IOException
	 * 		IO异常 
	 */
	private static void handlerBusiness(SelectionKey key) throws IOException {
		// 连接客户端业务
		if (key.isAcceptable()) {
			handlerAccept(key);
		} else if (key.isReadable()) { // 读写事件业务
			handlerReadable(key);
		}
	}

	/**
	 * 获取客户端连接
	 * 
	 * @param key
	 * 		选择器的key
	 * @throws IOException
	 * 		IO异常
	 */
	private static void handlerAccept(SelectionKey key) throws IOException {
		// 获取服务器端通道
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		// 获取客户端通道
		SocketChannel channel = server.accept();
		// 设置客户端通道为非阻塞
		channel.configureBlocking(false);
		// 将客户端通道注册到选择器中, 并设置读的权限, 不然读取不到客户端信息
		channel.register(selector, SelectionKey.OP_READ);
	}
	
	/**
	 * 处理读写业务
	 * 
	 * @param key
	 * 		选择器的key
	 * @throws IOException
	 * 		IO异常
	 */
	private static void handlerReadable(SelectionKey key) throws IOException {
		// 获取客户端通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 设置缓存大小
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		// 读取缓存
		int read = channel.read(byteBuffer);
		
		if (read > 0) {
			String msg = new  String(byteBuffer.array());
			System.out.println("收到客户端消息:" + msg);
		} else {
			System.out.println("客户端关闭!");
			key.cancel();
		}
		
	}
}
