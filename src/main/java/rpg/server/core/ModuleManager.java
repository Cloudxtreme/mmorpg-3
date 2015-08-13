package rpg.server.core;

import rpg.server.util.io.ResourceListener;

public interface ModuleManager extends ResourceListener {
	void loadResource();

	/**
	 * 模块初始化
	 */
	void init();
}
