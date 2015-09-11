package rpg.server.gen.agent;

import java.util.Map;

import rpg.server.core.condition.SimpleGameCondition;
import rpg.server.core.obj.GameObjectAdapter;
import rpg.server.module.family.FamilyAgent;
import rpg.server.util.log.Log;

public class AgentConditionDispatcher {
	public static boolean dispatch(GameObjectAdapter obj,
			SimpleGameCondition condition, Map<String, Object> vars) {
		switch (condition.getType()) {
		case FAMILY_HAS: {
			FamilyAgent agent = obj.getAgent(FamilyAgent.class);
			if (agent != null) {
				return agent.familyHas(condition, vars);
			} else {
				// TODO throw exception
				Log.game.error("FamilyAgent is null.id:{}", obj.getId());
				return false;
			}
		}
		default: {
			// TODO throw exception
			Log.game.error("not found condition handler.id:{}.action:{}",
					obj.getId(), condition.getType());
			return false;
		}
		}
	}
}
