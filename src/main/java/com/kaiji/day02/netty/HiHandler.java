package com.kaiji.day02.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HiHandler extends SimpleChannelHandler {

	/**
	 * 接收消息, 发送消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println("客户端接收到的消息:" + e.getMessage());
		super.messageReceived(ctx, e);
	}

	/**
	 * 发送异常信息
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("客户端异常消息:" + e.toString());
		super.exceptionCaught(ctx, e);
	}

	
	/**
	 * 正常连接一个服务端
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("正常连接服务端!");
		super.channelConnected(ctx, e);
	}

	/**
	 * 正常连接后断开
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("正常连接断开通道!");
		super.channelDisconnected(ctx, e);
	}

	/**
	 * 关闭资源, 断开连接, 释放资源!
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("关闭资源, 断开连接, 释放资源!");
		super.channelClosed(ctx, e);
	}
	

}
