package rpg.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetHandlerImpl extends ChannelInboundHandlerAdapter {
	// 当前的全部连接
	public static final ConcurrentLinkedQueue<NetHandlerImpl> handlers = new ConcurrentLinkedQueue<>();
	/** 接收到的消息队列 */
	private Queue<byte[]> datas = new ConcurrentLinkedQueue<byte[]>();
	/** 连接状态 */
	private ConnectionStatus connStatus = ConnectionStatus.LOGIN;

	private Channel channel;
	
	DefaultChannelGroup allChannels = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);

	/**
	 * 接收数据
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 记录数据
		datas.add((byte[]) msg);
	}

	/**
	 * 建立新连接
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		this.channel = ctx.channel();
		allChannels.add(this.channel);
	}

	/**
	 * 关闭连接
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		// 关闭玩家连接,客户端断开
		this.close(CloseRaeson.CLIENT_CLOSED);
	}

	public void sendMsg(int msgId, byte[] msg) {
		if (!channel.isActive())
			return;
		if (!channel.isWritable())
			return;
		ByteBuf head = channel.alloc().buffer(8);
		// 写数据
		// 数据总长度为协议体长度+消息ID长度
		head.writeInt(msg.length + 8);
		// 协议ID
		head.writeInt(msgId);
		channel.write(head);
		channel.write(msg);
		channel.flush();
	}

	/**
	 * 异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		this.close(CloseRaeson.CLIENT_CLOSED);
	}

	public void close(CloseRaeson reason) {
		connStatus = ConnectionStatus.LOSTED;
		handlers.remove(this);
		allChannels.remove(this.channel);
	}

	public ConnectionStatus getState() {
		return connStatus;
	}

	public byte[] receive() {
		return datas.poll();
	}

	public int getReceiveSize() {
		return datas.size();
	}
}
