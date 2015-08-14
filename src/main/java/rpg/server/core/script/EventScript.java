package rpg.server.core.script;

import java.util.Map;

import rpg.server.core.event.EventHandler;
import rpg.server.core.event.GameEvent;
import rpg.server.core.event.GameEventParaCondition;
import rpg.server.core.obj.GameObject;

/**
 * 触发脚本因素为事件的脚本实例 <br/>
 * 通过EventHandler实现 <br/>
 * 
 */
public class EventScript extends SimpleGameScript {

	EventScript(EventScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
		this.config = config;
	}

	@Override
	void addTo(GameObject object) {
		setOwner(object);
		script = config.script.getInstance(vars);
		script.setListner(this);
		if (handler == null) {
			handler = new EventHandler() {

				// 确定是否等待
				private boolean needWait = config.isWait();

				public void handleEvent(GameEvent event) {

					for (GameEventParaCondition c : config.getEventParaCond()) {
						if (!c.check(event))
							return;
					}
					if (needWait && wait)
						return;

					if (checkCount && count-- == 0
							|| // 如果有检测次数限制，且已达到，结束任务并从对象身上移除对应脚本
							checkOverdue
							&& overdue < System.currentTimeMillis()) { // 如果有存在时间限制，且已过期，结束任务并从对象身上移除对应脚本
						onScriptDone(EventScript.this);
						return;
					}

					wait = true;
					script.vars.put("eventSource", event.getSource());
					script.vars.put("eventTarget", event.getTarget());
					script.vars.put("eventPara", event.getParams());
					script.vars.put("event", event);
					switch (config.getWho()) {
					case 0:
						script.addTo(getOwner());
						break;
					case 1:
						script.addTo(event.getSource());
						break;
					case 2:
						script.addTo(event.getTarget());
						break;
					default:
						script.addTo(getOwner());
						break;
					}

				}
			};
			getOwner().registerEventHandler(config.getEventChannel(), handler,
					config.getEventType());
		}
	}

	@Override
	void remove() {
		if (handler != null) {
			getOwner().removeEventHandler(config.getEventChannel(), handler,
					config.getEventType());
			handler = null;
		}
		if (script != null) {
			script.remove();
			script = null;
		}
		super.remove();
	}

	/**
	 * 增加事件型脚本 重复次数检测功能
	 */
	public void onScriptDone(GameScript script) {
		if (script == this.script) {
			wait = false;
			if (getConfig().repeat) { // 重复
				if (this.checkCount && this.count <= 0) {
					onScriptDone();
				} else {
					addTo(getOwner()); // 重新把自己加载给对象
				}
			} else {
				onScriptDone();
			}
		}
	}

	@Override
	GameScriptConfig getConfig() {
		return config;
	}

	private EventScriptConfig config;

	private EventHandler handler;

}
