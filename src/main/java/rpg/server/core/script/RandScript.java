package rpg.server.core.script;

import java.util.Map;

import rpg.server.core.SimulateObject;

/**
 * 随机加载执行的复合脚本实例 <br/>
 * 加载：每次加载时随机获取配置中的一个子脚本的实例，加载并监听其执行情况 <br/>
 * 移除：同时负责移除当前选中的子脚本 <br/>
 * 进度监听：当前选中子脚本实例执行完毕后，视为整个脚本实例执行完毕
 * 
 */
public class RandScript extends ComplexGameScript {

	RandScript(RandScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
		this.config = config;
	}

	@Override
	void addTo(SimulateObject object) {
		if (checkCount && count-- == 0 || // 如果有检测次数限制，且已达到，结束任务并从对象身上移除对应脚本
				checkOverdue && overdue < System.currentTimeMillis()) { // 如果有存在时间限制，且已过期，结束任务并从对象身上移除对应脚本
			remove();
			return;
		}
		setOwner(object);
		chosen = config.rand().getInstance(vars);
		chosen.setListner(this);
		chosen.addTo(object);
	}

	@Override
	GameScriptConfig getConfig() {
		return config;
	}

	@Override
	void remove() {
		if (chosen != null) {
			chosen.remove();
			chosen = null;
		}
		super.remove();
	}

	public void onScriptDone(GameScript script) {
		if (script == chosen) { // 选中子脚本被执行完毕，则本脚本执行完毕
			if (getListner() != null) // 如果有监听者，则设置进度
				setProgress(Integer.MAX_VALUE);
			else { // 否则，自己处理自己的重复性问题
				if (config.repeat) { // 重复
					// chosen.remove(); //清除原来的子脚本实例
					addTo(getOwner()); // 重新把自己加载给对象
				} else {
					if (config.removeWhenDone)
						remove();
				}
			}
		}
	}

	private RandScriptConfig config;

	private GameScript chosen; // 选中的子脚本

}
