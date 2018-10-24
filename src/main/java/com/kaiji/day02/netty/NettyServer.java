package com.kaiji.day02.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class NettyServer {

	private static final String HOST_NAME = "localhost";
	private static final int PORT = 10010;
	
	public static void main(String[] args) {
		// 第一步: 创建服务器类
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		// 第二步: 创建nioScoket工厂
		ExecutorService bossExecutor = Executors.newCachedThreadPool();
		ExecutorService workerExecutor = Executors.newCachedThreadPool();
		bootstrap.setFactory(new NioServerSocketChannelFactory(bossExecutor, workerExecutor));
		
		// 第三步: 创建管道工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline channelPipeline = Channels.pipeline();
				// 将管道接收到的流转换成 String, 这样就不需要在messageReceived方法中
				// ChannelBuffer字节转成String字符串
				// 读数据的第二种方式
				channelPipeline.addLast("strDecoder", new StringDecoder());
				
				// 写数据的第二种方式
				channelPipeline.addLast("strEncoder", new StringEncoder());
				channelPipeline.addLast("handler", new HelloHandler());
				return channelPipeline;
			}
		});
		
		// 第四步: 绑定地址和端口号
		bootstrap.bind(new InetSocketAddress(HOST_NAME, PORT));
		System.out.println("启动一个服务器端!");
	}
}
