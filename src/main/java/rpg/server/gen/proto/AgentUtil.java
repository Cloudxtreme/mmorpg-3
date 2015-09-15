package rpg.server.gen.proto;

import rpg.server.core.obj.GameObjectAdapter;
import rpg.server.module.family.FamilyAgent;
import rpg.server.module.quest.QuestAgent;
import rpg.server.module.stage.StageAgent;

public class AgentUtil {
	public static void init(GameObjectAdapter obj) {
		obj.addAgent(new FamilyAgent());
		obj.addAgent(new QuestAgent());
		obj.addAgent(new StageAgent());
	}
}