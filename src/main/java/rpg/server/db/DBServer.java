package rpg.server.db;


public class DBServer {
	private static DBServer instance = new DBServer();

	private DBServer() {
	}

	public static DBServer getInstance() {
		return instance;
	}

	public void startup() {
	}

	public void shutdown() {
	}
}
