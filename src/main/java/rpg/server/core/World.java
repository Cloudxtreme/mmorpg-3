package rpg.server.core;

import java.io.File;

import rpg.server.core.action.GameActionManager;
import rpg.server.core.condition.GameConditionManager;
import rpg.server.core.formula.CalculatorConfig;
import rpg.server.core.formula.FunctionLib;
import rpg.server.core.task.TaskManager;
import rpg.server.db.DBServer;
import rpg.server.module.account.AccountManager;
import rpg.server.module.stage.StageManager;
import rpg.server.net.NetServer;
import rpg.server.util.log.Log;

public class World implements Runnable {

	/** 服务器ID */
	private String serverId;
	/** 资源路径 */
	private String resPath;
	/** 是否关闭 */
	private boolean shutdown = false;

	/** 单利 */
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
	 *            允许最多两个参数.1:server id.2:资源路径.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		switch (args.length) {
		case 2:// 资源路径
			getInstance().resPath = args[1];
		case 1:// 服务器ID
			getInstance().serverId = args[0];
		default:
			break;
		}
		// 启动
		getInstance().startup();
		// 增加退出应用程序的钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				getInstance().shutdown();
			}
		});
		Log.game.info("game server started.");
	}

	/**
	 * 加载资源
	 */
	private void loadResource() throws Exception {
		Log.game.info("load resource begin.");
		Log.game.info("==============载入系统配置==============");
		SysConfig.getInstance().load();
		Log.game.info("==============载入公式配置==============");
		CalculatorConfig.getInstance().load();
		FunctionLib.getInstance().load();
		Log.game.info("=============载入Condition配置=============");
		GameConditionManager.getInstance().load();
		Log.game.info("=============载入Action配置=============");
		GameActionManager.getInstance().load();
	}

	/**
	 * 开启
	 */
	private void startup() throws Exception {
		// 设置LOG相关
		File logConfig = new File(this.resPath, "log4j2.xml");
		if (!logConfig.exists()) {
			throw new Exception(logConfig.getAbsolutePath() + " not found.");
		}
		System.setProperty("log4j.configurationFile",
				"file://" + logConfig.getAbsolutePath());
		Log.game.info("game server startup.server id:{}.res path:{}.",
				serverId, resPath);
		// 载入资源
		this.loadResource();
		// 任务模块初始化
		TaskManager.getInstance().init();
		// 开启DB模块
		DBServer.getInstance().startup();
		// 开启网络模块
		NetServer.getInstance().startup();
		this.shutdown = false;
	}

	private void shutdown() {
		NetServer.getInstance().shutdown();
		DBServer.getInstance().shutdown();
	}

	@Override
	public void run() {
		if (!isShutdown()) {
			long beginTime = System.currentTimeMillis();
			// 场景单位tick
			StageManager.getInstance().tick();
			// 未进入游戏玩家tick
			AccountManager.getInstance().tick();
			// 周期行任务
			TaskManager.getInstance().tick();
			long t = System.currentTimeMillis() - beginTime;
			if (t > 500) {
				Log.game.warn("tick:{}", t);
			}
		}
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
