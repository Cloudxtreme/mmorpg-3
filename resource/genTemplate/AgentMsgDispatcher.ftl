package ${agentpackage};

import rpg.server.gen.proto.MsgUtil;
<#list msgHandlerList as msg>
import ${msg.canonicalName};
</#list>
import rpg.server.module.player.PlayerCharacter;
<#list agentList as agent>
import ${agent.packageName}.${agent.name};
</#list>

import com.google.protobuf.GeneratedMessage;

public class AgentMsgDispatcher {
	public static void dispatch(PlayerCharacter player, int msgId,
			GeneratedMessage msg) {
		switch (msgId) {
		<#list msgHandlerList as msg>
		case MsgUtil.${msg.name}: {
			${msg.agentName} agent = player.getAgent(${msg.agentName}.class);
			agent.${msg.methodName}((${msg.name}) msg);
			break;
		}
		</#list>
		default: {
			break;
		}
		}
	}
}
