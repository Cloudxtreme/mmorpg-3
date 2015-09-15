package ${agentpackage};

import java.util.Map;

import rpg.server.core.condition.SimpleGameCondition;
import rpg.server.core.obj.GameObjectAdapter;
<#list agentList as agent>
import ${agent.packageName}.${agent.name};
</#list>
import rpg.server.util.log.Log;

public class AgentConditionDispatcher {
	public static boolean dispatch(GameObjectAdapter obj,
			SimpleGameCondition condition, Map<String, Object> vars) {
		switch (condition.getType()) {
		<#list conditionHandlerList as ch>
		case ${ch.name}: {
			${ch.agentName} agent = obj.getAgent(${ch.agentName}.class);
			if (agent != null) {
				return agent.${ch.methodName}(condition, vars);
			} else {
				// TODO throw exception
				Log.game.error("${ch.agentName} is null.id:{}", obj.getId());
				return false;
			}
		}
		</#list>
		default: {
			// TODO throw exception
			Log.game.error("not found condition handler.id:{}.action:{}",
					obj.getId(), condition.getType());
			return false;
		}
		}
	}
}
