package com.kaiji.day02.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HelloHandler extends SimpleChannelHandler {


	/**
	 * 获取从客户端发送来的消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
//		ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
//		String msg = new String(buffer.array());
//		System.out.println("获取的消息是: " + msg);
		System.out.println("获取的消息是: " + e.getMessage());
		super.messageReceived(ctx, e);
	}

	/**
	 * 报错的时候, 提示的异常信息
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("打印异常消息:" + e);
		super.exceptionCaught(ctx, e);
	}

	/**
	 * 新进来一个连接
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("创建一个新的客户端!");
		super.channelConnected(ctx, e);
	}

	/**
	 * 当前成功连接进来一个客户端的时候, 再断开socket的时候访问这个方法
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("断开连接!");
		super.channelDisconnected(ctx, e);
	}

	/**
	 * 不管是否成功连接一个客户端, 只要关闭socket都会访问这个方法
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("关闭客户端!");
		super.channelClosed(ctx, e);
	}
	
	

}
