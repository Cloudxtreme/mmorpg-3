package rpg.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 消息结构<br>
 * 
 * <pre>
 * +---+---------+
 * | 4 | 消息长度 |
 * +---+---------+
 * | 4 | 消息ID  |
 * +---+---------+
 * | n | 消息长度 |
 * +---+---------+
 * </pre>
 * 
 * 总长度 = 4(长度) + 4(消息ID) + 主体数据
 */
public class Decoder extends LengthFieldBasedFrameDecoder {

	public Decoder() {
		super(Integer.MAX_VALUE, 0, 4, -4, 0);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buf)
			throws Exception {
		ByteBuf buffs = (ByteBuf) super.decode(ctx, buf);
		if (buffs == null)
			return null;

		// 主体数据
		byte[] decoded = new byte[buffs.readableBytes()];
		buffs.readBytes(decoded);
		buffs.release();

		return decoded;
	}
}
