package rpg.server.module.quest;

import java.util.Map;

import rpg.server.core.action.ActionHandler;
import rpg.server.core.action.ActionType;
import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.module.IAgent;
import rpg.server.module.player.PlayerCharacter;

public class QuestAgent implements IAgent {
	private PlayerCharacter player;

	@Override
	public void loadData() {

	}

	@Override
	public boolean loadDataFinish() {
		return false;
	}

	@Override
	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	@ActionHandler(ActionType.QUEST_GIVE)
	public boolean questGive(SimpleGameAction action, Map<String, Object> vars) {
		return true;
	}
}
