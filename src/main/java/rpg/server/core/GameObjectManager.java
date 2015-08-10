package rpg.server.core;

public class GameObjectManager {
	private GameObjectManager instance = new GameObjectManager();

	private GameObjectManager() {
	}

	public GameObjectManager getInstance() {
		return instance;
	}

}
