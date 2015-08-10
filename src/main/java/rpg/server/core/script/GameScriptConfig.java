package rpg.server.core.script;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rpg.server.core.SimulateObject;
import rpg.server.core.relation.AbstractRelation;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 脚本配置 <br/>
 * 配置文件中的脚本片断在内存中的镜像<br />
 * 脚本的静态、公用属性记录在GameScriptConfig中 <br/>
 * 脚本实例GameScript中保存对GameScriptConfig的引用<br />
 * 
 */
public abstract class GameScriptConfig {

	/**
	 * 从xml节点"script"中解析脚本细节
	 * 
	 * @param e
	 * @throws Exception
	 */
	void parse(Element e) throws Exception {
		// 载入重复性
		if (XmlUtils.getChildByName(e, "repeat") != null) {
			repeat = Boolean.parseBoolean(XmlUtils.getChildText(e, "repeat"));
		}
		// 载入检测次数
		if (XmlUtils.getChildByName(e, "count") != null) {
			count = Integer.parseInt(XmlUtils.getChildText(e, "count"));
		}
		// 载入移除设定
		if (XmlUtils.getChildByName(e, "removeWhenDone") != null) {
			removeWhenDone = Boolean.parseBoolean(XmlUtils.getChildText(e,
					"removeWhenDone"));
		}
		// 载入存在期限
		if (XmlUtils.getChildByName(e, "overdue") != null) {
			overdue = Long.parseLong(XmlUtils.getChildText(e, "overdue")) * 1000L; // 由秒换算为毫秒
		} // 载入实际添加对象
		Element sub = XmlUtils.getChildByName(e, "object");
		if (sub != null) {
			object_rel = AbstractRelation.parseRel(sub);
		}
		// 载入ui模板
		uitemplate = XmlUtils.getAttribute(e, "ui");
	}

	/**
	 * 工厂方法 从xml节点"script"解析脚本
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static final GameScriptConfig parseScript(Element e)
			throws Exception {
		GameScriptConfig script = null;
		// 提供一些默认的脚本配置解析
		String mode = XmlUtils.getAttribute(e, "mode");
		if (mode.equals("") || mode.equalsIgnoreCase("DEFAULT")) {
			script = new DefaultGameScriptConfig();
		}
		if (mode.equalsIgnoreCase("EVENT")) {
			script = new EventScriptConfig();
		} else if (mode.equalsIgnoreCase("TIME")) {
			script = new TimeScriptConfig();
		} else if (mode.equalsIgnoreCase("RAND")) {
			script = new RandScriptConfig();
		} else if (mode.equalsIgnoreCase("ALL")) {
			script = new AllScriptConfig();
		} else if (mode.equalsIgnoreCase("SEQ")) {
			script = new SeqScriptConfig();
		} else if (mode.equalsIgnoreCase("UNTIL")) {
			script = new UntilScriptConfig();
		} else if (mode.equalsIgnoreCase("TEMPLATE")) {
			script = new TemplateScriptConfig();
		} else if (customconfigs.containsKey(mode)) {
			script = (GameScriptConfig) customconfigs.get(mode).newInstance();
		} else {
			script = new DefaultGameScriptConfig();
		}
		script.parse(e);
		return script;
	}

	/**
	 * 根据模板获取脚本Config
	 * 
	 * @param id
	 * @return
	 */
	public final static GameScriptConfig parseScriptFromTemplate(
			String templateId, Map<String, ?> vars) {
		try {
			return GameScriptConfig.parseScript(XmlUtils.loadString(
					TemplateManager.getTemplate(templateId).parse(vars))
					.getDocumentElement());
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 为与仿真对象指定关系的对象添加该脚本 <br/>
	 * 这里仅负责解析出所有对象 <br/>
	 * 实际的加载调用{@link GameScript#addTo(SimulateObject)}执行
	 * 
	 * @param object
	 */
	public final Set<Long> addToObject(SimulateObject object,
			Map<String, Object> vars) {
		Set<Long> set = new HashSet<Long>(0);
		if (object_rel != null) {
			Set<SimulateObject> sobs = object.getRelatedSOBs(object_rel);
			if (sobs == null) {
				return set;
			}
			for (SimulateObject sob : sobs) {
				// //System.out.println("script actual adding to : " +
				// sob.getId());
				if (sob == null)
					return set;
				GameScript ins = getInstance(vars);
				GameScriptManager.getInstance().addScript(ins);
				ins.addTo(sob);
				set.add(ins.getId());
			}
		} else { // 关系项为空，则加载给对象自身
			GameScript ins = getInstance(vars);
			GameScriptManager.getInstance().addScript(ins);
			ins.addTo(object);
			set.add(ins.getId());
		}
		return set;
	}

	/**
	 * 获取实例
	 * 
	 * @param vars
	 * @return
	 */
	abstract GameScript getInstance(Map<String, Object> vars);

	/**
	 * 解析并获取UI字符串
	 * 
	 * @return
	 */
	public abstract String getUI();

	/**
	 * 注册自定义脚本解析器
	 * 
	 * @return
	 */
	public static final boolean registerParser(String mode, Class c) {
		if (customconfigs.containsKey(mode)) // 重名，会顶掉其它的，不允许
		{
			return false;
		}
		customconfigs.put(mode, c);
		return true;
	}

	// ////////getter & setter///////////////////////////////////////
	/**
	 * @return the repeat
	 */
	public boolean getRepeat() {
		return repeat;
	}

	/**
	 * @param repeat
	 *            the repeat to set
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	/**
	 * @return the object_rel
	 */
	public AbstractRelation getObject_rel() {
		return object_rel;
	}

	/**
	 * @param objectRel
	 *            the object_rel to set
	 */
	public void setObject_rel(AbstractRelation objectRel) {
		object_rel = objectRel;
	}

	boolean repeat;
	protected int count = -1; // 最大重复次数，仅对重复性脚本有效
	protected boolean removeWhenDone = true; // 完成时是否立即移除 // note lincy
												// 20110324，有些脚本执行完则移除会有问题，所以加上这一标识
	protected long overdue = -1; // 存在期限(ms)
	// protected boolean child; //是否是子节点
	/*
	 * 加载对象 <br/> 说明该脚本将被加载到与该对象什么关系的对象身上 <br/> 可缺省，默认为加载到对象自身
	 */
	protected AbstractRelation object_rel;
	/* ui模板文件 */
	protected String uitemplate = "";
	/* 自定义的脚本表 */
	private static Map<String, Class> customconfigs = new HashMap<String, Class>(
			0);
}
