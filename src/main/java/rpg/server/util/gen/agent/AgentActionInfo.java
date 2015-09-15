package rpg.server.util.gen.agent;

public class AgentActionInfo {
	/** action type 名称 */
	private String name;
	/** 代理类名称,只是类名 */
	private String agentName;

	/** 代理类中对应的方法名称 */
	private String methodName;

	public AgentActionInfo() {
	}

	public AgentActionInfo(String name, String agentName, String methodName) {
		this.name = name;
		this.agentName = agentName;
		this.methodName = methodName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
