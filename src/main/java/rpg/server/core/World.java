package rpg.server.core;

import rpg.server.net.NetServer;

public class World {

	/** 服务器ID */
	private String serverId;
	/** 资源路径 */
	private String resPath;
	/** 是否关闭 */
	private boolean shutdown = false;

	private static final World instance = new World();

	private World() {
	}

	/**
	 * 获得实例<br>
	 * 单利模式
	 * 
	 * @param 游戏世界对象
	 */
	public static World getInstance() {
		return instance;
	}

	/**
	 * 主函数
	 * 
	 * @param args
	 *            允许最多两个参数.1:资源路径.2:server id
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		switch (args.length) {
		case 2:
			getInstance().resPath = args[1];
		case 1:
			getInstance().serverId = args[0];
		default:
			break;
		}
		getInstance().startup();
		// 增加退出应用程序的钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				getInstance().shutdown();
			}
		});
	}

	/**
	 * 加载资源
	 */
	private void loadResource() {

	}

	/**
	 * 开启
	 */
	private void startup() throws Exception {
		// 载入资源
		this.loadResource();
		// 开启网络模块
		NetServer.getInstance().startup();
		this.shutdown = false;
	}

	private void shutdown() {
		NetServer.getInstance().shutdown();

	}

	public boolean isShutdown() {
		return shutdown;
	}

	public String getServerId() {
		return serverId;
	}

	public String getResPath() {
		return resPath;
	}
}
