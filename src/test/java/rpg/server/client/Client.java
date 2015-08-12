package rpg.server.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import rpg.server.net.Decoder;
import rpg.server.net.Encoder;

public class Client {
	public void startup() throws InterruptedException {
		Bootstrap b = new Bootstrap();
		b.group(new NioEventLoopGroup());
		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new Decoder(), new Encoder(), new ClientHandler());
			}
		});

		// Start the client.
		ChannelFuture f = b.connect(new InetSocketAddress(8888)).sync();
		f.channel().closeFuture().sync();
	}
}
