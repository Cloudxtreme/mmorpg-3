package rpg.server.core.script;

import java.util.Map;

import rpg.server.core.obj.SimulateObject;
import rpg.server.util.task.TaskForSchedule;
import rpg.server.util.task.TaskManager;
import rpg.server.util.task.TaskManager.TaskType;

/**
 * 触发脚本条件检测的因素为时间的脚本实例 <br/>
 * 通过TimerTask调度实现
 * 
 */
public class TimeScript extends SimpleGameScript {

	boolean done;

	TimeScript(TimeScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
		this.config = config;
	}

	@Override
	void addTo(SimulateObject object) {
		setOwner(object);
		script = config.script.getInstance(vars);
		script.setListner(this);
		if (task == null) {
			task = new TaskForSchedule() {

				// 确定是否等待
				private boolean needWait = config.isWait();

				@Override
				public void run() {
					if (needWait && wait)
						return;
					if (getOwner() == null) { // 如果对象已经不存在，任务结束
						onScriptDone(TimeScript.this);
						return;
					}
					if (checkCount && count-- == 0
							|| // 如果有检测次数限制，且已达到，结束任务并从对象身上移除对应脚本
							checkOverdue
							&& overdue < System.currentTimeMillis()) { // 如果有存在时间限制，且已过期，结束任务并从对象身上移除对应脚本
						onScriptDone(TimeScript.this);
						return;
					}
					wait = true;
					script.addTo(getOwner());
				}
			};
			long firstDelay = config.getFirstDelay();
			long period = config.getPeriod();
			if (firstDelay >= 0) {
				if (period <= 0) {
					TaskManager.getInstance().addScheduledTask(task,
							TaskType.WORLD, firstDelay);
				} else {
					TaskManager.getInstance().addScheduledTask(task,
							TaskType.WORLD, firstDelay, period);
				}
			}
		}
	}

	@Override
	GameScriptConfig getConfig() {
		return config;
	}

	@Override
	void remove() {
		if (task != null) {
			task.cancel();
			task = null;
		}
		if (script != null) {
			script.remove();
			script = null;
		}
		super.remove();
	}

	private TimeScriptConfig config;

	private TaskForSchedule task;

}
