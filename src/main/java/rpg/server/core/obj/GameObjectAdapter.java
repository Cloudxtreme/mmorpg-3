package rpg.server.core.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.event.EventHandler;
import rpg.server.core.event.GameEventChannel;
import rpg.server.core.event.GameEventType;
import rpg.server.core.module.IAgent;
import rpg.server.gen.agent.AgentActionDispatcher;

public abstract class GameObjectAdapter implements GameObject {

	private long id;

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	/**
	 * 自身事件关注列表<br>
	 * key:事件类别,value:事件处理器映射表
	 */
	private Map<GameEventType, List<EventHandler>> eventHandlerMapS = new HashMap<GameEventType, List<EventHandler>>();
	/**
	 * 邻居事件关注列表<br>
	 * key:事件类别,value:事件处理器映射表
	 */
	private Map<GameEventType, List<EventHandler>> eventHandlerMapN = new HashMap<GameEventType, List<EventHandler>>();

	/**
	 * 代理列表<br>
	 * key:代理类标识,value:代理
	 */
	@SuppressWarnings("rawtypes")
	protected Map<Class, IAgent> agentMap = new HashMap<Class, IAgent>();

	/**
	 * 获取代理对象<br>
	 * 
	 * @param cla
	 *            代理类标识
	 * @return 代理对象
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T extends IAgent> T getAgent(Class<T> cla) {
		IAgent ia = this.agentMap.get(cla);
		return ia == null ? null : (T) ia;
	}

	/**
	 * 添加一个代理对象<br>
	 * 
	 * @param ia
	 *            代理对象
	 */
	public void addAgent(IAgent ia) {
		this.agentMap.put(ia.getClass(), ia);
	}

	// *******************************************
	// *************action相关
	// *******************************************
	/**
	 * 执行简单行为
	 * 
	 * @param action
	 * @param vars
	 * @return
	 */
	@Override
	public boolean doSimpleAction(SimpleGameAction action,
			Map<String, Object> vars) {
		return AgentActionDispatcher.dispatch(this, action, vars);
	}

	// *******************************************
	// *************游戏事件相关
	// *******************************************
	@Override
	public void registerEventHandler(GameEventChannel source,
			EventHandler handler, GameEventType... types) {
		if (types == null || types.length == 0) {
			return;
		}
		if (source == GameEventChannel.SELF || source == GameEventChannel.BOTH) {
			for (GameEventType type : types) {
				registerHandler(type, handler, eventHandlerMapS);
			}

		}
		if (source == GameEventChannel.NEIGHBOR
				|| source == GameEventChannel.BOTH) {
			for (GameEventType type : types) {
				registerHandler(type, handler, eventHandlerMapN);
			}
		}
	}

	// 注册事件监听器
	private void registerHandler(GameEventType type, EventHandler handler,
			Map<GameEventType, List<EventHandler>> eventHandlerMap) {
		List<EventHandler> hlist = eventHandlerMap.get(type);
		if (hlist != null) {
			if (!hlist.contains(handler))// 不重复添加
				hlist.add(handler);
		} else {
			hlist = new ArrayList<EventHandler>();
			hlist.add(handler);
			eventHandlerMap.put(type, hlist);
		}
	}

	@Override
	public void removeEventHandler(GameEventChannel source,
			EventHandler handler, GameEventType... types) {
		if (source == GameEventChannel.SELF || source == GameEventChannel.BOTH) {
			for (GameEventType type : types) {
				removeHandler(type, handler, eventHandlerMapS);
			}
		}
		if (source == GameEventChannel.NEIGHBOR
				|| source == GameEventChannel.BOTH) {
			for (GameEventType type : types) {
				removeHandler(type, handler, eventHandlerMapN);
			}
		}

	}

	// 注销事件监听器
	private void removeHandler(GameEventType type, EventHandler handler,
			Map<GameEventType, List<EventHandler>> eventHandlerMap) {
		List<EventHandler> hlist = eventHandlerMap.get(type);
		if (hlist != null) {
			hlist.remove(handler);
		}
	}
	// *******************************************
	// *************抽象方法
	// *******************************************
}
