package rpg.server.gen.agent;

import rpg.server.core.obj.GameObjectAdapter;
import rpg.server.module.family.FamilyAgent;

public class AgentUtil {
	public static void init(GameObjectAdapter obj) {
		obj.addAgent(new FamilyAgent());
	}
}
