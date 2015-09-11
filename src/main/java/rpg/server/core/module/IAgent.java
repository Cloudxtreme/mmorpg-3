package rpg.server.core.module;

import rpg.server.module.player.PlayerCharacter;

/**
 * 代理类接口
 */
public interface IAgent {
	void loadData();

	boolean loadDataFinish();

	void setPlayer(PlayerCharacter player);
}
