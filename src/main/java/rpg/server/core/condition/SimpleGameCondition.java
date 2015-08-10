package rpg.server.core.condition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rpg.server.core.MetaParamException;
import rpg.server.core.Metadata;
import rpg.server.core.SimulateObject;
import rpg.server.core.relation.AbstractRelation;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 简单条件（一元） <br/>
 * 是真正的具体条件 <br/>
 * 所有的复合条件由简单条件组合而成 <br/>
 * 资源文件格式:<br/>
 * &lt;condition mode="指明模式（目前支持SIMPLE，NOT）"
 * type="类型（应是元数据中登记过且GameCondition.Type定义过的"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;参数的个数和格式应与元数据中定义的一致<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;para&gt;参数具体值&lt;/para&gt;<br/>
 * &lt;/condition&lt;
 */
public class SimpleGameCondition extends GameCondition {
	/** 元数据 */
	private Metadata meta;

	/** 条件类型 */
	private ConditionType type;

	/** 检测目标(与条件检测者的关系) */
	private AbstractRelation object;

	/** 参数表(参数名-参数值的映射) */
	private Map<String, Object> args = new HashMap<String, Object>();

	/*
	 * ----资源文件格式----------------------------------------------------------------
	 * ------ <condition mode="指明模式（目前支持SIMPLE，NOT）"
	 * type="类型（应是元数据中登记过且GameCondition.Type定义过的"> <!--参数的个数和格式应与元数据中定义的一致-->
	 * <para>参数具体值</para> </condition>
	 * ------------------------------------------
	 * -----------------------------------------
	 */

	/**
	 * 提供程序内直接构造简单动作的方法
	 * 
	 * @param type
	 *            动作名（需要在ConditionType枚举类和ConditionMeta.xml文档中同时定义）
	 * @param paralist
	 *            参数列表（参照ConditionMeta.xml文档中定义的参数名，如果某参数不允许默认值，则必须在此map中传入）
	 * @param mode
	 *            模式
	 * @throws Exception
	 */
	public SimpleGameCondition(ConditionType type,
			Map<String, String> paralist, ConditionMode mode) throws Exception {
		super(mode);
		this.type = type;
		meta = GameConditionManager.getInstance().getMetadata(type);
		if (meta == null) {
			throw new Exception("载入动作出错，类型元数据未登记：type-" + type);
		}
		try {
			args = meta.parseParams(paralist);
		} catch (MetaParamException ex) {
			throw new Exception("载入动作出错，参数列表解析有误：type-" + type + "\n"
					+ ex.getMessage());
		}
	}

	SimpleGameCondition(ConditionMode mode) {
		super(mode);
	}

	@Override
	void load(Element e) throws Exception {
		// 载入类别
		try {
			type = ConditionType.valueOf(XmlUtils.getAttribute(e, "type")
					.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new Exception("载入条件出错，类型不能识别：type-"
					+ XmlUtils.getAttribute(e, "type").toUpperCase());
		}
		meta = GameConditionManager.getInstance().getMetadata(type);
		if (meta == null) {
			throw new Exception("载入条件出错，类型元数据：type-" + type);
		}
		// 载入条件目标，可缺省，默认为自身
		if (XmlUtils.getChildByName(e, "object") != null)
			object = AbstractRelation.parseRel(XmlUtils.getChildByName(e,
					"object"));
		// 载入参数
		Element[] list = XmlUtils.getChildrenByName(e, "para");
		// int index = 0;
		// for (Element elem : list) {
		// String name = meta.getParamName(index);
		// if (name == null) {
		// throw new Exception("载入条件出错，参数个数不匹配：参数index-" + index);
		// }
		// Object value = meta.getParamValue(index, XmlUtils.getText(elem));
		// args.put(name, value);
		// index++;
		// }
		try {
			args = meta.parseParams(list);
		} catch (MetaParamException ex) {
			throw new Exception("载入条件出错，参数列表解析有误：type-" + type + "\n"
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
	 * 获取byte型参数
	 * 
	 * @param name
	 * @return
	 */
	public byte getByteArg(String name) {
		return (Byte) args.get(name);
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
		} else {
			// TODO 记录LOG
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
	public boolean check(SimulateObject sob, Map<String, Object> vars) {
		switch (getMode()) {
		case SIMPLE:
			if (object == null)
				return sob.checkSimpleCondition(this, vars);
			else {
				Set<SimulateObject> sobs = sob.getRelatedSOBs(object);
				if (sobs == null)
					return false;
				for (SimulateObject s : sobs)
					if (s == null || !s.checkSimpleCondition(this, vars))
						return false;
				return true;
			}
		case NOT:
			if (object == null)
				return !sob.checkSimpleCondition(this, vars);
			else {
				Set<SimulateObject> sobs = sob.getRelatedSOBs(object);
				if (sobs == null)
					return false;
				for (SimulateObject s : sobs)
					if (s == null || s.checkSimpleCondition(this, vars))
						return false;
				return true;
			}
		default:
			return false;
		}
	}

	@Override
	public String getUI() {
		String ui = "";
		if (meta.getUitemplate() != "") {
			Map<String, Object> vars = new HashMap<String, Object>(args);
			if (getMode() == ConditionMode.NOT) {
				vars.put("not", "不满足");
			} // 所有条件的UI模板以"{not}xxxxx"的形式，这样，遇到NOT型条件只需填充"不满足“即可
			ui = TemplateManager.getTemplate(meta.getUitemplate()).parse(vars);
		} else {
			ui = getName() == null ? "" : getName();
		}
		return ui;
	}

	// *********************************************
	// *******get
	// *********************************************

	/**
	 * @return the type
	 */
	public ConditionType getType() {
		return type;
	}

	/**
	 * @return the args
	 */
	public Map<String, Object> getArgs() {
		return args;
	}

	/**
	 * @return the object
	 */
	public AbstractRelation getObject() {
		return object;
	}

}
