package rpg.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetServer {
	/** 单体实例 */
	private static final NetServer instance = new NetServer();
	private ServerBootstrap b = null;
	private boolean run = false;
	private int port = 8888;

	private NetServer() {
	}

	public static NetServer getInstance() {
		return instance;
	}

	public void init(String path) {

	}

	private void serverRun() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.childOption(ChannelOption.TCP_NODELAY, true);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			b.childHandler(new ChildChannelHandler());
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * 开启网络服务
	 */
	public void startup() {
		// 启动socket监听
		new Thread(new Runnable() {
			@Override
			public void run() {
				serverRun();
			}
		}).start();
		run = true;
	}

	/**
	 * 网络关闭
	 */
	public void shutdown() {
		// channel.close().awaitUninterruptibly();
		run = false;
	}

	public boolean isRun() {
		return run;
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			ChannelPipeline p = arg0.pipeline();
			p.addLast(new Decoder(), new Encoder(), new NetHandlerImpl());
		}
	}
}
