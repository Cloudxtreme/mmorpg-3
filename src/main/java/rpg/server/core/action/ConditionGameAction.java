package rpg.server.core.action;

import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.SimulateObject;
import rpg.server.core.condition.GameCondition;
import rpg.server.util.io.XmlUtils;

/**
 * 条件动作<br>
 * 当条件检测为true时执行动作行为
 * 
 * @see GameCondition
 */
public class ConditionGameAction extends GameAction {
	/** 执行的动作 */
	private GameAction action;
	/** 检测条件 */
	private GameCondition condition;

	@Override
	void load(Element e) throws Exception {
		// 解析条件
		Element elemCondition = XmlUtils.getChildByName(e, "condition");
		if (elemCondition != null) {
			condition = GameCondition.parseCondition(elemCondition);
		}
		// 解析动作
		Element elemAction = XmlUtils.getChildByName(e, "action");
		if (elemCondition != null) {
			action = GameAction.parseAction(elemAction);
		}
	}

	@Override
	public boolean action(SimulateObject sob, Map<String, Object> vars) {
		// 先判断条件,然后执行动作
		if (condition.check(sob, vars)) {
			return action.action(sob, vars);
		}
		return false;
	}

	@Override
	public ActionMode getMode() {
		return ActionMode.CONDI;
	}

	@Override
	public String getUI() {
		return this.action.getUI();
	}

	@Override
	public Document toXml() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("action");
		root.setAttribute("mode", "CONDI");
		// 没有添加条件，不知道有没有问题
		// Element elemCondition = doc.createElement("condition");

		root.appendChild(action.toXml().getDocumentElement());
		doc.appendChild(root);
		return doc;
	}

	@Override
	public String getName() {
		return this.action.getName();
	}

}
