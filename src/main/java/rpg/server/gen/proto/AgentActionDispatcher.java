package rpg.server.gen.proto;

import java.util.Map;

import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.obj.GameObjectAdapter;
import rpg.server.module.family.FamilyAgent;
import rpg.server.module.quest.QuestAgent;
import rpg.server.module.stage.StageAgent;
import rpg.server.util.log.Log;

public class AgentActionDispatcher {
	public static boolean dispatch(GameObjectAdapter obj,
			SimpleGameAction action, Map<String, Object> vars) {
		switch (action.getType()) {
		case QUEST_GIVE: {
			QuestAgent agent = obj.getAgent(QuestAgent.class);
			if (agent != null) {
				return agent.questGive(action, vars);
			} else {
				Log.game.error("QuestAgent is null.id:{}", obj.getId());
				return false;
			}
		}
		default: {
			Log.game.error("not found action handler.id:{}.action:{}",
					obj.getId(), action.getType());
			return false;
		}
		}
	}
}
