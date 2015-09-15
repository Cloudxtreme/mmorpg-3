package ${agentpackage};

import java.util.Map;

import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.obj.GameObjectAdapter;
<#list agentList as agent>
import ${agent.packageName}.${agent.name};
</#list>
import rpg.server.util.log.Log;

public class AgentActionDispatcher {
	public static boolean dispatch(GameObjectAdapter obj,
			SimpleGameAction action, Map<String, Object> vars) {
		switch (action.getType()) {
		<#list actionHandlerList as ah>
		case ${ah.name}: {
			${ah.agentName} agent = obj.getAgent(${ah.agentName}.class);
			if (agent != null) {
				return agent.${ah.methodName}(action, vars);
			} else {
				Log.game.error("${ah.agentName} is null.id:{}", obj.getId());
				return false;
			}
		}
		</#list>
		default: {
			Log.game.error("not found action handler.id:{}.action:{}",
					obj.getId(), action.getType());
			return false;
		}
		}
	}
}
