package rpg.server.module.stage;

import rpg.server.core.MsgHandler;

import rpg.server.core.module.Agent;
import rpg.server.core.module.IAgent;
import rpg.server.gen.proto.Stage.C_MOVE;

/**
 * 场景代理类<br>
 */
@Agent
public class StageAgent implements IAgent {

	@MsgHandler(C_MOVE.class)
	public void move(C_MOVE msg) {

	}
}
