package rpg.server.core.obj;

import java.util.HashMap;
import java.util.Map;

import rpg.server.module.player.PlayerCharacter;
import rpg.server.net.NetHandler;

/**
 * 管理所有游戏对象,包括玩家,怪,NPC等
 */
public class GameObjectManager {
	/** 单例模式 */
	private static final GameObjectManager instance = new GameObjectManager();
	private Map<Long, PlayerCharacter> playerMap = new HashMap<Long, PlayerCharacter>();

	private GameObjectManager() {
	}

	public static GameObjectManager getInstance() {
		return instance;
	}

	public void playerLogin(long playerId, NetHandler net) {
		PlayerCharacter player = playerMap.get(playerId);
		if (player != null) {// 如果玩家在线,则踢出在线的玩家
			this.kickPlayer(player);
		}
		player.login(playerId, net);
		this.playerMap.put(playerId, player);
	}

	public void kickPlayer(long playerId) {
		PlayerCharacter player = playerMap.get(playerId);
		if (player != null) {
			this.kickPlayer(player);
		}
	}

	private void kickPlayer(PlayerCharacter player) {
		// TODO
	}

}
