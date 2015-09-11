package rpg.server.module.family;

import java.util.Map;

import rpg.server.core.condition.ConditionHandler;
import rpg.server.core.condition.ConditionType;
import rpg.server.core.condition.SimpleGameCondition;
import rpg.server.core.module.IAgent;
import rpg.server.module.player.PlayerCharacter;

public class FamilyAgent implements IAgent {

	@ConditionHandler(ConditionType.FAMILY_HAS)
	public boolean familyHas(SimpleGameCondition condition,
			Map<String, Object> vars) {
		return false;
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
