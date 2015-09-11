package rpg.server.module.stage;

import rpg.server.core.MsgHandler;
import rpg.server.core.module.Agent;
import rpg.server.core.module.IAgent;
import rpg.server.gen.proto.Stage.C_MOVE;
import rpg.server.module.player.PlayerCharacter;

/**
 * 场景代理类<br>
 */
@Agent
public class StageAgent implements IAgent {

	@MsgHandler(C_MOVE.class)
	public void move(C_MOVE msg) {

	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean loadDataFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPlayer(PlayerCharacter player) {
		// TODO Auto-generated method stub

	}
}
