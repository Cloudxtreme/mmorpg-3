package rpg.server.core.script;

/**
 * 脚本进度监听器
 */
@FunctionalInterface
interface ScriptListner {

	/**
	 * 当脚本实例执行完毕时的处理
	 * @param script
	 */
	void onScriptDone(GameScript script);
	
}
