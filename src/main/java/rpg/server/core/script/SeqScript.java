package rpg.server.core.script;

import java.util.Map;

import rpg.server.core.obj.SimulateObject;

/**
 * 顺序加载执行的复合脚本实例<br/>
 * 加载：每次加载时根据当前进度获取配置中的对应子脚本的实例，加载并监听其执行情况 <br/>
 * 移除：同时负责移除当前子脚本实例 <br/>
 * 进度监听：当前子脚本实例执行完毕后，设置进度。判断是否已到达最后一个子脚本，如果没有，则重新加载自身 <br/>
 * 
 */
public class SeqScript extends ComplexGameScript {

	SeqScript(SeqScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
		this.config = config;
	}

	@Override
	void addTo(SimulateObject object) {
		if (getProgress() == -1 && (checkCount && count-- == 0 || // 如果有检测次数限制，且已达到，结束任务并从对象身上移除对应脚本
				checkOverdue && overdue < System.currentTimeMillis())) { // 如果有存在时间限制，且已过期，结束任务并从对象身上移除对应脚本
			remove();
			return;
		}
		setOwner(object);
		current = config.scripts[getProgress() + 1].getInstance(vars);
		current.setListner(this);
		current.addTo(object);
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
		setProgress(-1);
		super.remove();
	}

	public void onScriptDone(GameScript script) {
		if (script == current) { // 当前子脚本执行完毕
			int prog = getProgress() + 1;
			if (prog < config.scripts.length - 1) { // 尚未到达最后一步
				setProgress(prog); // 设置当前已完成的步骤
				// current.remove(); //移除当前子脚本
				current = null;
				addTo(getOwner()); // 重新把自己添加给对象
			} else { // 已成功执行一轮
				if (getListner() != null) { // 如果有监听者，则设置进度
					setProgress(Integer.MAX_VALUE);
				} else { // 否则，自己处理自己的重复性问题
					if (config.repeat) { // 重复
						current.remove(); // 清除原来的子脚本实例
						setProgress(-1);
						addTo(getOwner()); // 重新把自己加载给对象
					} else {
						if (config.removeWhenDone)
							remove();
					}
				}
			}
		}
	}

	private SeqScriptConfig config;

	private GameScript current; // 当前正在执行的子脚本

}
