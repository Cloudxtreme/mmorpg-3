package rpg.server.core;

/**
 * 系统配置文件，配置一些系统参数
 */
public class SysConfig {
	private static final String RES_FILE = "sysConfig.xml";

	private static final SysConfig instance = new SysConfig();

	public static SysConfig getInstance() {
		return instance;
	}

	public void load() {

	}
}
