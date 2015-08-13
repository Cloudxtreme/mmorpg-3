package rpg.server.module.stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rpg.server.core.obj.GameObject;
import rpg.server.core.obj.GameObjectType;
import rpg.server.util.task.TickTask;

public class Stage implements TickTask {
	private long id;
	private String name;
	private Map<GameObjectType, List<GameObject>> objs = new HashMap<GameObjectType, List<GameObject>>();

	public void tick() {

	}

	public void enter(GameObject obj) {

	}

	public void leave(GameObject obj) {

	}
}
