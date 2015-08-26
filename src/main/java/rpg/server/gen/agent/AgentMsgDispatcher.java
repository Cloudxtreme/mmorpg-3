package rpg.server.gen.agent;

import rpg.server.gen.proto.Stage.C_MOVE;
import rpg.server.module.player.PlayerCharacter;
import rpg.server.module.stage.StageAgent;

import com.google.protobuf.GeneratedMessage;

public class AgentMsgDispatcher {
	public static void moveMsg(PlayerCharacter player, GeneratedMessage msg) {
		StageAgent a = player.getAgent(StageAgent.class);
		a.move((C_MOVE) msg);
	}
}
