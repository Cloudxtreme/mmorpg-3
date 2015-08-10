package rpg.server.core.obj;

public class GameObjectManager {
	private GameObjectManager instance = new GameObjectManager();

	private GameObjectManager() {
	}

	public GameObjectManager getInstance() {
		return instance;
	}

}
