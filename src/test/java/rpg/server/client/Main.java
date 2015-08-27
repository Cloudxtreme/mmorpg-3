package rpg.server.client;

import java.io.File;

public class Main {
	public static void main(String[] args) throws Exception {
		// 设置LOG相关
		File logConfig = new File(args[0], "log4j2.xml");
		if (!logConfig.exists()) {
			throw new Exception(logConfig.getAbsolutePath() + " not found.");
		}
		System.setProperty("log4j.configurationFile",
				"file://" + logConfig.getAbsolutePath());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Client().startup();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
