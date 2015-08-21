package rpg.server.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpg.server.gen.proto.Account;
import rpg.server.gen.proto.MsgUtil;
import rpg.server.util.NumberUtil;
import rpg.server.util.log.Log;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	private Channel channel = null;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 建立连接
		Log.client.info("已连接上服务器.");
		this.channel = ctx.channel();
		this.sendLoginMsg();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		Log.client.info("连接已断开");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// 消息
		byte[] buf = (byte[]) msg;
		int len = NumberUtil.bytesToInt(buf, 0);
		int msgId = NumberUtil.bytesToInt(buf, 4);
		byte[] msgbuf = new byte[len - 8];
		System.arraycopy(buf, 8, msgbuf, 0, len - 8);
		this.channel = ctx.channel();
		Log.client.info("rec server msg.id:{}", msgId);
	}

	private void sendLoginMsg() {
		Account.C_LOGIN.Builder builder = Account.C_LOGIN.newBuilder();
		builder.setAccount("test");
		builder.setToken("abcdefg");
		builder.setChannel("test@test.com");
		builder.setClientVersion("1.0");
		builder.setResVersion(1);
		builder.setDevice("abc");
		builder.setUa("6");
		builder.setOs("ios");
		builder.setModel("");
		this.sendMsg(builder);
	}

	public void sendMsg(Builder builder) {
		this.sendMsg(builder.build());
	}

	public void sendMsg(Message msg) {
		if (channel == null) {
			Log.client.error("channel is null.");
			return;
		}
		int msgId = MsgUtil.getIdByClass(msg.getClass());
		byte[] buf = msg.toByteArray();
		byte[] header = new byte[8];
		NumberUtil.intToBytes(header, 0, buf.length + 8);
		NumberUtil.intToBytes(header, 4, msgId);
		channel.write(header);
		channel.writeAndFlush(buf);

		Log.client.info("send msg to server.id:{}", msgId);
	}
}
