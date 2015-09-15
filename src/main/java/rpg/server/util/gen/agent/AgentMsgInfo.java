package rpg.server.util.gen.agent;

public class AgentMsgInfo {
	/** 包+名字 */
	private String canonicalName;
	/** 协议名称,只包含名字 */
	private String name;
	/** 代理名称,类名称 */
	private String agentName;
	/** 代理类中对应的方法名称 */
	private String methodName;

	public AgentMsgInfo(String canonicalName, String name, String agentName,
			String methodName) {
		super();
		this.canonicalName = canonicalName;
		this.name = name;
		this.agentName = agentName;
		this.methodName = methodName;
	}

	public AgentMsgInfo() {
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
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
