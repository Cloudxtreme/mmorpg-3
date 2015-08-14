package rpg.server.core.obj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rpg.server.core.event.EventHandler;
import rpg.server.core.event.GameEventType;

public abstract class GameObjectAdapter implements GameObject {
	// 事件类别-事件处理器映射表
	private Map<GameEventType, List<EventHandler>> eventHandlerMapS = new HashMap<GameEventType, List<EventHandler>>();

	private Map<GameEventType, List<EventHandler>> eventHandlerMapN = new HashMap<GameEventType, List<EventHandler>>();

}
