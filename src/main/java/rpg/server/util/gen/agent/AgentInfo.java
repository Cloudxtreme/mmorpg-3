package rpg.server.util.gen.agent;

public class AgentInfo {
	private String packageName;
	private String name;

	public AgentInfo(String packageName, String name) {
		this.packageName = packageName;
		this.name = name;
	}

	public AgentInfo() {
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
