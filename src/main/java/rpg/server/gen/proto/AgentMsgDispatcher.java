package rpg.server.gen.proto;

import rpg.server.gen.proto.MsgUtil;
import rpg.server.gen.proto.Stage.C_MOVE;
import rpg.server.module.player.PlayerCharacter;
import rpg.server.module.family.FamilyAgent;
import rpg.server.module.quest.QuestAgent;
import rpg.server.module.stage.StageAgent;

import com.google.protobuf.GeneratedMessage;

public class AgentMsgDispatcher {
	public static void dispatch(PlayerCharacter player, int msgId,
			GeneratedMessage msg) {
		switch (msgId) {
		case MsgUtil.C_MOVE: {
			StageAgent agent = player.getAgent(StageAgent.class);
			agent.move((C_MOVE) msg);
			break;
		}
		default: {
			break;
		}
		}
	}
}
