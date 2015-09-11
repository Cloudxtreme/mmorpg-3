package rpg.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import rpg.server.gen.proto.MsgUtil;
import rpg.server.module.account.AccountManager;
import rpg.server.util.NumberUtil;
import rpg.server.util.log.Log;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

public class NetHandler extends ChannelInboundHandlerAdapter {
	// 当前的全部连接
	public static final ConcurrentLinkedQueue<NetHandler> handlers = new ConcurrentLinkedQueue<>();
	/** 接收到的消息队列 */
	private Queue<GeneratedMessage> datas = new ConcurrentLinkedQueue<GeneratedMessage>();
	/** 连接渠道 */
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
		// datas.add((byte[]) msg);
		try {
			byte[] buffer = (byte[]) msg;
			// int len = NumberUtil.bytesToInt(buffer, 0); // 消息长度
			int msgId = NumberUtil.bytesToInt(buffer, 4); // 消息ID
			// 取出消息体
			CodedInputStream in = CodedInputStream.newInstance(buffer, 8,
					buffer.length - 8);
			GeneratedMessage m = MsgUtil.parseFrom(msgId, in);
			this.datas.add(m);
			Log.net.info("rec msg.{}", m.toString());
		} catch (Exception e) {
			Log.net.error(e.getMessage());
		}
	}

	/**
	 * 建立新连接
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		this.channel = ctx.channel();
		allChannels.add(this.channel);
		AccountManager.getInstance().onAccept(this);
		Log.net.info("客户端建立连接.ip:{}.总数量:{}", this.channel.remoteAddress()
				.toString(), allChannels.size());
	}

	/**
	 * 关闭连接
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		// 关闭玩家连接,客户端断开
		this.close(CloseRaeson.CLIENT_CLOSED);
	}

	public void sendMsg(Message msg) {
		this.sendMsg(MsgUtil.getIdByClass(msg.getClass()), msg.toByteArray());
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
		this.close(CloseRaeson.CLIENT_EXCEPTION);
		Log.net.info("net handler exception.{}", cause.getMessage());
	}

	/**
	 * 关闭连接<br>
	 * 
	 * @param reason
	 *            关闭原因
	 * @see CloseRaeson
	 */
	public void close(CloseRaeson reason) {
		// 断开连接
		this.channel.close();
		// 移除
		handlers.remove(this);
		allChannels.remove(this.channel);
		Log.net.info("close net handler.reason:{}" + reason.toString());
	}

	public GeneratedMessage getMsg() {
		return datas.poll();
	}
}
