package rpg.server.core.action;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.obj.GameObject;
import rpg.server.util.io.XmlUtils;

/**
 * 游戏动作行为<br/>
 * 简单动作格式说明:<br/>
 * &lt;action mode="指明模式(目前支持SIMPLE)" type="类型(应是元数据中登记过且ActionType定义过的)"
 * classify="处理模块,本属性可省略,缺省值取元数据中指定的模块"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;para>参数具体值&lt;/para&gt;<br/>
 * &lt;/action> <br/>
 * <br/>
 * 复合动作格式说明:<br/>
 * &lt;action mode="指明模式(目前支持ALL,RAND)"
 * probs="ALL型动作无需这一属性,RAND型动作用这一属性指明各个动作的权重(正整数，用空格隔开),也可缺省,默认值为等权重"&gt;<br/>
 * 以下依次列出子动作，子动作个数至少应为2，否则，应退化为简单动作<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;action
 * mode="子动作的模式，既可以是简单动作，也可以继续嵌套复合动作，但最内层必须是一个简单动作"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;子动作内容<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;/action&gt;<br/>
 * &lt;/action&gt;
 */
public abstract class GameAction {
	/**
	 * 解析action<br>
	 * 工厂方法,从XML节点解析,并返回正确的子类实例
	 * 
	 * @param e
	 *            节点
	 * @return action
	 * @throws Exception
	 */
	public static GameAction parseAction(Element e) throws Exception {
		GameAction action = null;
		ActionMode mode = null;
		try {
			mode = ActionMode.valueOf(XmlUtils.getAttribute(e, "mode")
					.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new Exception("action mode error:mode-"
					+ XmlUtils.getAttribute(e, "mode").toUpperCase(), ex);
		}
		switch (mode) {
		case TEMPLATE:
			action = new TemplateGameAction();
			break;
		case SCRIPT:
			action = new ScriptGameAction();
			break;
		case SIMPLE:
			action = new SimpleGameAction();
			break;
		case RAND:
			action = new RandGameAction();
			break;
		case ALL:
			action = new AllGameAction();
			break;
		case CONDI:
			action = new ConditionGameAction();
			break;
		}
		action.load(e);
		return action;
	}

	@Override
	public final String toString() {
		try {
			return XmlUtils
					.document2String(toXml())
					.replace(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
							"");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 从XNL字符串解析action
	 * 
	 * @param xml
	 *            XML字符串
	 * @return action
	 * @throws Exception
	 */
	public static final GameAction parse(String xml) throws Exception {
		if (xml == null || xml.trim().isEmpty())
			return null;
		return parseAction(XmlUtils.loadString(xml).getDocumentElement());
	}

	/**
	 * 执行本动作
	 * 
	 * @param sob
	 * @return 是否执行成功
	 */
	public final boolean action(GameObject sob) {
		return action(sob, new HashMap<String, Object>(0));
	}

	// *********************************************
	// *******抽象方法
	// *********************************************

	/**
	 * 从xml节点"action"载入动作细节
	 * 
	 * @param e
	 *            xml节点
	 * @throws Exception
	 */
	abstract void load(Element e) throws Exception;

	/**
	 * 由指定对象执行本动作
	 * 
	 * @param sob
	 * @param vars
	 *            参数
	 * @return 是否执行成功
	 */
	abstract public boolean action(GameObject sob, Map<String, Object> vars);

	/**
	 * 获取模式
	 * 
	 * @return
	 */
	abstract public ActionMode getMode();

	/**
	 * 解析并获取UI字符串
	 * 
	 * @return
	 */
	abstract public String getUI();

	/**
	 * 转换成xml字符串
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 */
	abstract public Document toXml() throws ParserConfigurationException;

	/**
	 * 获取action名称<br>
	 * 用于简单UI显示
	 * 
	 * @return 名称
	 */
	abstract public String getName();
}
