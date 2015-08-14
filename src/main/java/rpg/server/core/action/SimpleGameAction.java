package rpg.server.core.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.MetaParamException;
import rpg.server.core.Metadata;
import rpg.server.core.obj.GameObject;
import rpg.server.core.relation.AbstractRelation;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 简单动作<br/>
 * 是真正的具体动作 <br/>
 * 所有的复合动作由简单动作组合而成 <br/>
 * 
 */
public class SimpleGameAction extends GameAction {
	/** 元数据 */
	private Metadata meta;

	/** 动作类型 */
	private ActionType type;

	/** 显示名称(用于NPC菜单等) */
	private String name;

	/** 动作目标(与动作执行者的关系) */
	private AbstractRelation object;

	/** 参数表(参数名-参数值的映射) */
	private Map<String, Object> args = new HashMap<String, Object>();

	/**
	 * 提供程序内直接构造简单动作的方法
	 * 
	 * @param type
	 *            动作名(需要在ActionType枚举类和actionMeta.xml文档中同时定义)
	 * @param paralist
	 *            参数列表(参照actionMeta.xml文档中定义的参数名，如果某参数不允许默认值，则必须在此map中传入)
	 * @param name
	 *            显示名(主要用于菜单显示等)
	 * @throws Exception
	 */
	public SimpleGameAction(ActionType type, Map<String, String> paralist,
			String name) throws Exception {
		this.type = type;
		this.name = name;
		meta = GameActionManager.getInstance().getMetadata(type);
		if (meta == null) {
			throw new Exception("action metadata error：type-" + type);
		}
		try {
			args = meta.parseParams(paralist);
		} catch (MetaParamException ex) {
			throw new Exception("action metadata error：type-" + type + "\n"
					+ ex.getMessage());
		}
	}

	/**
	 * 提供程序内直接构造简单动作的方法（默认显示名为空）
	 * 
	 * @param type
	 *            动作名（需要在ActionType枚举类和actionMeta.xml文档中同时定义）
	 * @param paralist
	 *            参数列表（参照actionMeta.xml文档中定义的参数名，如果某参数不允许默认值，则必须在此map中传入）
	 * @throws Exception
	 */
	public SimpleGameAction(ActionType type, Map<String, String> paralist)
			throws Exception {
		this(type, paralist, "");
	}

	SimpleGameAction(Metadata meta) {
		this.meta = meta;
		this.type = ActionType.valueOf(meta.getType().toUpperCase());
	}

	public SimpleGameAction() {
	}

	@Override
	public ActionMode getMode() {
		return ActionMode.SIMPLE;
	}

	@Override
	void load(Element e) throws Exception {
		// 载入类别
		try {
			type = ActionType.valueOf(XmlUtils.getAttribute(e, "type")
					.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new Exception("action type error.type-"
					+ XmlUtils.getAttribute(e, "type").toUpperCase());
		}
		name = XmlUtils.getAttribute(e, "showname");
		meta = GameActionManager.getInstance().getMetadata(type);
		if (meta == null) {
			throw new Exception("action metadata error.type-" + type);
		}
		// 载入目标，可缺省，默认为自身
		if (XmlUtils.getChildByName(e, "object") != null)
			object = AbstractRelation.parseRel(XmlUtils.getChildByName(e,
					"object"));
		// 载入参数
		Element[] list = XmlUtils.getChildrenByName(e, "para");
		try {
			args = meta.parseParams(list);
		} catch (MetaParamException ex) {
			throw new Exception("action metadata error.type-" + type + ","
					+ ex.getMessage());
		}
	}

	/**
	 * 根据参数名取参数
	 * 
	 * @param name
	 * @return
	 */
	public Object getArg(String name) {
		return args.get(name);
	}

	/**
	 * 获取int型参数
	 * 
	 * @param name
	 * @return
	 */
	public int getIntArg(String name) {
		return (Integer) args.get(name);
	}

	/**
	 * 获取short型参数
	 * 
	 * @param name
	 * @return
	 */
	public short getShortArg(String name) {
		return (Short) args.get(name);
	}

	/**
	 * 获取byte型参数
	 * 
	 * @param name
	 * @return
	 */
	public byte getByteArg(String name) {
		return (Byte) args.get(name);
	}

	/**
	 * 获取long型参数
	 * 
	 * @param name
	 * @return
	 */
	public long getLongArg(String name) {
		return (Long) args.get(name);
	}

	/**
	 * 获取float型参数
	 * 
	 * @param name
	 * @return
	 */
	public float getFloatArg(String name) {
		return (Float) args.get(name);
	}

	/**
	 * 获取bool型参数
	 * 
	 * @param name
	 * @return
	 */
	public boolean getBoolArg(String name) {
		return (Boolean) args.get(name);
	}

	/**
	 * 获取string型参数
	 * 
	 * @param name
	 * @return
	 */
	public String getStringArg(String name) {
		return (String) args.get(name);
	}

	/**
	 * 指定设置某一参数值
	 * 
	 * @param name
	 * @param arg
	 */
	public void setArg(String name, Object arg) {
		if (args.containsKey(name)) {
			args.put(name, arg);
		}
	}

	/**
	 * 获取分组
	 * 
	 * @return
	 */
	public String getClassify() {
		return meta.getClassify();
	}

	@Override
	public boolean action(GameObject sob, Map<String, Object> vars) {
		if (object != null) {
			boolean ret = true;
			Set<GameObject> sobs = sob.getRelatedSOBs(object);
			if (sobs == null)
				return false;
			if (vars == null)
				vars = new HashMap<String, Object>();
			for (GameObject s : sobs) {
				vars.put("object", s);
				ret &= sob.doSimpleAction(this, vars);
			}
			return ret;
		} else
			return sob.doSimpleAction(this, vars);
	}

	@Override
	public String getUI() {
		if (meta.getUitemplate() != "") {
			return TemplateManager.getTemplate(meta.getUitemplate())
					.parse(args);
		}
		return getName();
	}

	@Override
	public Document toXml() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("action");
		root.setAttribute("mode", "simple");
		root.setAttribute("type", type.toString());
		if (name != null && !name.trim().equals(""))
			root.setAttribute("showname", name);
		for (Iterator<Entry<String, Object>> itr = args.entrySet().iterator(); itr
				.hasNext();) {
			Element para = doc.createElement("para");
			Entry<String, Object> entry = itr.next();
			para.setAttribute("name", entry.getKey());
			para.setTextContent(entry.getValue().toString());
			root.appendChild(para);
		}
		doc.appendChild(root);
		return doc;
	}

	@Override
	public String getName() {
		if (name != null && !name.trim().equals(""))
			return name;
		return meta.getName();
	}

	// *********************************************
	// *******get set
	// *********************************************
	public ActionType getType() {
		return type;
	}

	public AbstractRelation getObject() {
		return object;
	}

	public Map<String, Object> getArgs() {
		return args;
	}
}
