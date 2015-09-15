package rpg.server.util.gen.proto;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.util.StringUtil;
import rpg.server.util.io.XmlUtils;

public class ProtoGenConfig {
	private boolean gen = false;
	private String protocPath;
	private String protoFilePath;
	private String outputPackage;
	private String resourcePath;
	private String outputPath;

	private ProtoGenConfig() {

	}

	public static ProtoGenConfig parseConfiguration(File configFile,
			String resourcePath, String outputPath) throws Exception {
		Document doc = XmlUtils.load(configFile);
		return parseConfiguration(doc.getDocumentElement(), resourcePath,
				outputPath);
	}

	public static ProtoGenConfig parseConfiguration(Element ele,
			String resourcePath, String outputPath) throws Exception {
		ProtoGenConfig pgc = new ProtoGenConfig();
		if (StringUtil.isTrue(XmlUtils.getAttribute(ele, "gen"))) {
			pgc.gen = true;
			pgc.protocPath = XmlUtils.getChildText(ele, "protocPath");
			pgc.protoFilePath = XmlUtils.getChildText(ele, "protoFilePath");
			pgc.outputPackage = XmlUtils.getChildText(ele, "outputPackage");
			pgc.resourcePath = resourcePath;
			pgc.outputPath = outputPath;
		}
		return pgc;
	}

	public void gen() throws Exception {
		if (!gen) {
			return;
		}
		String templatePath = this.resourcePath + File.separator
				+ "genTemplate";
		ParseProto parse = new ParseProto(protocPath, protoFilePath,
				outputPath, outputPackage, templatePath);
		parse.genProtoDesc();
		parse.processDesc();
		parse.genProtoClass();
	}
}
