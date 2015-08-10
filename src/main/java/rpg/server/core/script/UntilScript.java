package rpg.server.core.script;

import java.util.Map;

import rpg.server.core.SimulateObject;

public class UntilScript extends GameScript implements ScriptListner {

	UntilScript(UntilScriptConfig config, Map<String, Object> vars) {
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
		for (int i = 0; i < config.scripts.length; i++) {
			current = (DefaultGameScript) config.scripts[i].getInstance(vars);
			current.setOwner(object);
			if (current.check()) {
				current.setListner(this);
				current.addTo(object);
				break;
			} else {
				current = null;
			}
		}
		if (current == null) { // 没有任何一个子脚本检测成功，也算执行完一轮
			if (config.repeat) { // 重复
				addTo(object); // 重新把自己加载给对象
			} else {
				if (config.removeWhenDone)
					remove();
			}
		}
	}

	@Override
	GameScriptConfig getConfig() {
		return config;
	}

	@Override
	void remove() {
		if (current != null) {
			current.remove();
			current = null;
		}
		super.remove();
	}

	public void onScriptDone(GameScript script) {
		if (script == current) { // 选中子脚本被执行完毕，则本脚本执行完毕
			if (getListner() != null) // 如果有监听者，则设置进度
				setProgress(Integer.MAX_VALUE);
			else { // 否则，自己处理自己的重复性问题
				if (config.repeat) { // 重复
					// current.remove(); //清除原来的子脚本实例
					addTo(getOwner()); // 重新把自己加载给对象
				} else {
					if (config.removeWhenDone)
						remove();
				}
			}
		}
	}

	private UntilScriptConfig config;

	private DefaultGameScript current; // 当前的子脚本

}
