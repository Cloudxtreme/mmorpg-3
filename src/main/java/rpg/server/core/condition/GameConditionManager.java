package rpg.server.core.condition;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.Metadata;
import rpg.server.util.io.ResourceListener;
import rpg.server.util.io.ResourceManager;
import rpg.server.util.io.XmlReader;
import rpg.server.util.io.XmlReader.XmlElementParser;
import rpg.server.util.io.XmlUtils;

/**
 * 条件管理器，负责加载条件的元数据
 * 
 */
public class GameConditionManager {

	private Map<ConditionType, Metadata> typemappings = new EnumMap<ConditionType, Metadata>(
			ConditionType.class);

	private static final String RES = "conditionMeta.xml";

	private static final GameConditionManager instance = new GameConditionManager();

	/**
	 * @return the instance
	 */
	public static final GameConditionManager getInstance() {
		return instance;
	}

	private GameConditionManager() {
		// 注册Xml解析器
		XmlReader.registerXmlElementParser(GameCondition.class,
				new XmlElementParser<GameCondition>() {
					public GameCondition fromXml(Element e) {
						try {
							return GameCondition.parseCondition(e);
						} catch (Exception ex) {
							throw new RuntimeException(ex.getMessage(), ex);
						}
					}
				});
	}

	/**
	 * 加载元数据配置
	 * 
	 * @param path
	 */
	public void load(String path) {
		final File file = new File(path, RES);
		try {
			load(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResourceManager.getInstance().registerResourceListener(
				new ResourceListener() {

					public void onResourceChange(File file) {
						try {
							load(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					public File listenedFile() {
						return file;
					}

					@Override
					public String toString() {
						return "conditionMeta";
					}
				});
	}

	public void load(File file) throws Exception {
		Document d = XmlUtils.load(file);
		Element e = d.getDocumentElement();
		Element[] list = XmlUtils.getChildrenByName(e, "condition");
		for (Element elem : list) {
			Metadata meta = new Metadata();
			meta.load(elem);
			typemappings.put(
					ConditionType.valueOf(meta.getType().toUpperCase()), meta);
		}
	}

	/**
	 * 获取Metadata，by type
	 * 
	 * @param type
	 * @return
	 */
	public Metadata getMetadata(ConditionType type) {
		return typemappings.get(type);
	}
}
