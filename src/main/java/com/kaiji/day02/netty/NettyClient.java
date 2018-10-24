package com.kaiji.day02.netty;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * 客户端
 * @author kaiji
 *
 */
public class NettyClient {

	private static final String HOST_NAME = "localhost";
	private static final int PORT = 10010;

	public static void main(String[] args) {
		Scanner scanner = null;
		Channel channel = null;
		try {
			// 第一步: 创建客户端服务器
			ClientBootstrap clientBootstrap = new ClientBootstrap();

			ExecutorService workerExecutor = Executors.newCachedThreadPool();
			ExecutorService bossExecutor = Executors.newCachedThreadPool();
			// 第二步: 设置socket工厂
			clientBootstrap.setFactory(new NioClientSocketChannelFactory(bossExecutor, workerExecutor));
			
			// 第三步: 设置管道工厂
			clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				
				@Override
				public ChannelPipeline getPipeline() throws Exception {
					ChannelPipeline pipeline = Channels.pipeline();
					// 给接收消息转码
					pipeline.addLast("decoder", new StringDecoder());
					// 给发出消息转码
					pipeline.addLast("encoder", new StringEncoder());
					// 给socket一个处理业务的类
					pipeline.addLast("hiHandler", new HiHandler());
					return pipeline;
				}
			});
			
			// 第四步: 连接服务器端
			ChannelFuture channelFuture = clientBootstrap.connect(new InetSocketAddress(HOST_NAME, PORT));
			System.out.println("开启一个新的客户端!");
			
			// 获取客户端连接服务器的channel
			channel = channelFuture.getChannel();
			scanner = new Scanner(System.in);
			while (true) {
				System.out.println("请输入");
				channel.write(scanner.nextLine());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			
			if (channel != null) {
				channel.close();
			}
		}
		
		
		
	}
}
