package rpg.server.util.gen;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.util.StringUtil;
import rpg.server.util.gen.agent.AgentGenConfig;
import rpg.server.util.gen.mybatis.DBGenConfig;
import rpg.server.util.gen.proto.ProtoGenConfig;
import rpg.server.util.io.XmlUtils;

public class GenMain {
	/** 资源路径 */
	private String resourcePath;
	/** 输出目录 */
	private String outputPath;
	/** 协议生成配置文件 */
	private ProtoGenConfig protoGenConfig;
	/** DB生成配置文件 */
	private DBGenConfig dbGenConfig;
	/** 代理生成配置文件 */
	private AgentGenConfig agentGenConfig;
	/** 整体配置文件 */
	private static final String GEN_CONFIG = "genConfig.xml";

	public static void main(String[] args) throws Exception {
		String resourcePath = "";
		if (args != null && args.length > 0) {
			resourcePath = args[0];
		}
		resourcePath = System.getProperty("user.dir") + File.separator
				+ "resource";
		GenMain genMain = new GenMain(resourcePath);
		genMain.load();
		genMain.gen();

	}

	public GenMain(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public void load() throws Exception {
		Document doc = XmlUtils.load(new File(this.resourcePath, GEN_CONFIG));
		Element rootElement = doc.getDocumentElement();
		this.outputPath = XmlUtils.getChildText(rootElement, "outputPath");
		if (StringUtil.isEmpty(this.outputPath)) {
			this.outputPath = System.getProperty("user.dir") + File.separator
					+ "src" + File.separator + "main" + File.separator + "java";
		}
		this.loadProtoGenConfig(rootElement);
		this.loadDBGenConfig(rootElement);
		this.loadAgentGenConfig(rootElement);
	}

	/**
	 * 载入协议生成的配置文件<br>
	 * 
	 * @throws Exception
	 */
	private void loadProtoGenConfig(Element rootElement) throws Exception {
		Element protoGenElement = XmlUtils.getChildByName(rootElement,
				"protoGen");
		if (protoGenElement != null) {
			this.protoGenConfig = ProtoGenConfig.parseConfiguration(
					protoGenElement, this.resourcePath, this.outputPath);
		}
	}

	private void loadDBGenConfig(Element rootElement) throws Exception {
		Element dbGenElement = XmlUtils.getChildByName(rootElement, "dbgen");
		if (dbGenElement != null) {
			// 读取配置文件
			this.dbGenConfig = DBGenConfig.parseConfiguration(dbGenElement,
					this.resourcePath, outputPath);
		}
	}

	private void loadAgentGenConfig(Element rootElement) throws Exception {
		Element agentGenElement = XmlUtils.getChildByName(rootElement,
				"agentGen");
		if (agentGenElement != null) {
			this.agentGenConfig = AgentGenConfig.parseConfiguration(
					agentGenElement, this.resourcePath, outputPath);
		}
	}

	public void gen() throws Exception {
		if (protoGenConfig != null) {
			protoGenConfig.gen();
		}
		if (dbGenConfig != null) {
			dbGenConfig.gen();
		}
		if (agentGenConfig != null) {
			agentGenConfig.gen();
		}
	}

}
