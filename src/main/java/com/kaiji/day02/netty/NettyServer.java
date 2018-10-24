package com.kaiji.day02.netty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyServer {

	public static void main(String[] args) {
		// 第一步: 创建服务器类
		ServerBootstrap bootstrap = new ServerBootstrap();
		// 第二步: 创建nioScoket工厂
		ExecutorService bossExecutor = Executors.newCachedThreadPool();
		ExecutorService workerExecutor = Executors.newCachedThreadPool();
		bootstrap.setFactory(new NioServerSocketChannelFactory(bossExecutor, workerExecutor));
		// 第三步: 创建管道工厂
	}
}
