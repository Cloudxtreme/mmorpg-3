package rpg.server.core.script;

import java.util.Map;

/**
 * 简单脚本的实例 <br/>
 * 在被添加时：添加内嵌脚本 <br/>
 * 在被移除时：移除内嵌脚本 <br/>
 * 
 */
public abstract class SimpleGameScript extends GameScript implements
		ScriptListner {

	SimpleGameScript(GameScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
	}

	/**
	 * 自身脚本结束
	 */
	public void onScriptDone() {
		if (getListner() != null){ // 如果有监听者，则设置进度
			setProgress(Integer.MAX_VALUE);
		}else { // 否则，自己处理自己的重复性问题
			if (getConfig().removeWhenDone){
				remove();
			}
		}
	}

	public void onScriptDone(GameScript script) {
		if (script == this.script) {
			wait = false;
			if (getConfig().repeat) { // 重复
				addTo(getOwner()); // 重新把自己加载给对象
			}else{
				onScriptDone();
			}
		}
	}

	protected GameScript script;

	// 等待标示
	protected boolean wait;
}
