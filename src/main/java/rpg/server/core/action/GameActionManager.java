package rpg.server.core.action;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.Metadata;
import rpg.server.core.World;
import rpg.server.util.StringUtil;
import rpg.server.util.io.ResourceListener;
import rpg.server.util.io.ResourceManager;
import rpg.server.util.io.XmlReader;
import rpg.server.util.io.XmlReader.XmlElementParser;
import rpg.server.util.io.XmlUtils;

/**
 * 动作管理器<br>
 * 负责加载动作的元数据
 */
public class GameActionManager {

	/** 元数据 */
	private Map<ActionType, Metadata> typemappings = new EnumMap<ActionType, Metadata>(
			ActionType.class);
	/**
	 * 协议ID与Action的对应关系<br>
	 * 如果定义action为协议类型,则收到msg时直接执行action
	 * 
	 */
	private Map<Integer, MsgActionMapping> msgActionMap = new HashMap<Integer, MsgActionMapping>();
	/** 资源文件名称 */
	private static final String RES = "actionConfig.xml";

	private static final GameActionManager instance = new GameActionManager();

	/**
	 * @return the instance
	 */
	public static final GameActionManager getInstance() {
		return instance;
	}

	private GameActionManager() {
		// 注册Xml解析器
		XmlReader.registerXmlElementParser(GameAction.class,
				new XmlElementParser<GameAction>() {
					public GameAction fromXml(Element e) {
						try {
							return GameAction.parseAction(e);
						} catch (Exception ex) {
							throw new RuntimeException(ex.getMessage(), ex);
						}
					}
				});
	}

	/**
	 * 加载元数据配置
	 * 
	 * @throws Exception
	 */
	public void load() throws Exception {
		final File file = new File(World.getInstance().getResPath(), RES);
		load(file);
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
				});
	}

	private void load(File file) throws Exception {
		try {
			Document d = XmlUtils.load(file);
			Element e = d.getDocumentElement();
			Element[] list = XmlUtils.getChildrenByName(e, "action");
			for (Element elem : list) {
				Metadata meta = new Metadata();
				meta.load(elem);
				ActionType type = ActionType.valueOf(meta.getType()
						.toUpperCase());
				typemappings.put(type, meta);
				MsgActionMapping msgAction = MsgActionMapping.parseFromElem(
						type, elem);
				if (msgAction != null) {
					msgActionMap.put(msgAction.msgId, msgAction);
				}
			}
		} catch (Exception e) {
			throw new Exception("load " + RES + ",error.", e);
		}
	}

	/**
	 * 获取Metadata，by type
	 * 
	 * @param type
	 * @return
	 */
	public Metadata getMetadata(ActionType type) {
		return typemappings.get(type);
	}

	/**
	 * Action和Cmd的Mapping，用于从cmd中解析Action<br />
	 * 通过在action中增加几个属性节点，来实现cmd和action的映射,例如：<br />
	 * 
	 * @author bluesky
	 * 
	 */
	static class MsgActionMapping {
		/** 动作类型 */
		ActionType action;
		/** 关联的协议ID */
		int msgId;

		/**
		 * 从节点中解析，如果没有映射，返回null
		 * 
		 * @param type
		 * @param elem
		 * @return
		 */
		static MsgActionMapping parseFromElem(ActionType type, Element elem) {
			String msgId = elem.getAttribute("msg");
			if (!StringUtil.isEmpty(msgId)) {
				MsgActionMapping msgAction = new MsgActionMapping();
				msgAction.action = type;
				msgAction.msgId = Integer.parseInt(msgId, 16);
				return msgAction;
			} else {
				return null;
			}
		}
	}

	/**
	 * 是否为Action类型的Cmd
	 * 
	 * @param cmdId
	 * @return
	 */
	public boolean isMsgAction(int msgId) {
		return msgActionMap.containsKey(msgId);
	}

	/**
	 * 获取指令号对应的CmdActionMapping
	 * 
	 * @param cmdId
	 * @return
	 */
	MsgActionMapping getCmdAction(int msgId) {
		return msgActionMap.get(msgId);
	}

}
