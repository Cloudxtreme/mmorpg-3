package rpg.server.core.condition;

import java.util.HashMap;
import java.util.Map;

import rpg.server.core.obj.SimulateObject;

import org.w3c.dom.Element;

import rpg.server.util.io.XmlUtils;

/**
 * 条件<br/>
 * 
 */
public abstract class GameCondition {
	
	/** 条件模式 */
	private ConditionMode mode;

	/** 检测失败时给出的提示信息 */
	private String msg = null;

	/** 条件名称,用于显示 */
	private String name;

	public GameCondition(ConditionMode mode) {
		this.mode = mode;
	}

	/**
	 * 工厂方法： 从xml节点解析，并返回正确的子类实例
	 * 
	 * @param e
	 *            xml节点
	 * @return
	 * @throws Exception
	 */
	public static final GameCondition parseCondition(Element e)
			throws Exception {
		GameCondition condition = null;
		ConditionMode mode = null;
		try {
			mode = ConditionMode.valueOf(XmlUtils.getAttribute(e, "mode")
					.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new Exception("load condition error：mode-"
					+ XmlUtils.getAttribute(e, "mode").toUpperCase());
		}
		switch (mode) {
		case TEMPLATE:
			condition = new TemplateCondition(mode);
			break;
		case SIMPLE:
		case NOT:
			condition = new SimpleGameCondition(mode);
			break;
		case OR:
		case AND:
			condition = new ComplexGameCondition(mode);
			break;
		}
		condition.load(e);
		// 载入条件名称，可缺省，默认为null
		if (XmlUtils.getChildByName(e, "name") != null)
			condition.name = XmlUtils.getChildText(e, "name");
		// 载入条件不满足的提示语，可缺省，默认为null
		if (XmlUtils.getChildByName(e, "msg") != null)
			condition.msg = XmlUtils.getChildText(e, "msg");
		return condition;
	}

	/**
	 * 对仿真对象检测本条件
	 * 
	 * @param sob
	 * @return
	 */
	public final boolean check(SimulateObject sob) {
		return check(sob, new HashMap<String, Object>(0));
	}

	// *********************************************
	// *******get
	// *********************************************
	/**
	 * @return the mode
	 */
	public ConditionMode getMode() {
		return mode;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	// *********************************************
	// *******抽象方法
	// *********************************************
	/**
	 * 从xml节点"condition"载入条件细节
	 * 
	 * @param e
	 * @throws Exception
	 */
	abstract void load(Element e) throws Exception;

	/**
	 * 对仿真对象检测本条件 <br/>
	 * 带临时参数
	 * 
	 * @param sob
	 * @param vars
	 * @return
	 */
	abstract public boolean check(SimulateObject sob, Map<String, Object> vars);

	/**
	 * 解析并获取UI字符串
	 * 
	 * @return
	 */
	abstract public String getUI();
}
