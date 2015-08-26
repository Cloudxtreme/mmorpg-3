package rpg.server.module.stage;

public class StageManager {
	private static StageManager instance = new StageManager();

	private StageManager() {

	}

	public static StageManager getInstance() {
		return instance;
	}

	public void tick() {

	}
}
