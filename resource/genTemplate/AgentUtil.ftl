package ${agentpackage};

import rpg.server.core.obj.GameObjectAdapter;
<#list agentList as agent>
import ${agent.packageName}.${agent.name};
</#list>

public class AgentUtil {
	public static void init(GameObjectAdapter obj) {
		<#list agentList as agent>
		obj.addAgent(new ${agent.name}());
		</#list>
	}
}