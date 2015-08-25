package rpg.server.core.obj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rpg.server.core.event.EventHandler;
import rpg.server.core.event.GameEventType;
import rpg.server.core.module.IAgent;

public abstract class GameObjectAdapter implements GameObject {
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
	private Map<Class, IAgent> agentMap = new HashMap<Class, IAgent>();

	@Override
	public <T extends IAgent> T getAgent(Class<T> cla) {
		IAgent ia = this.agentMap.get(cla);
		return ia == null ? null : (T) ia;
	}

	public void addAgent(IAgent ia) {
		this.agentMap.put(ia.getClass(), ia);
	}
}
