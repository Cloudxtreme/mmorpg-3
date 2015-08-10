package rpg.server.core.action;

import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.obj.SimulateObject;
import rpg.server.util.io.XmlUtils;

/**
 * 全部动作组 <br/>
 * 至少应有两个动作,否则应采用SimpleGameCondition
 * 
 */
public class AllGameAction extends GameAction {
	/** 子动作集合,至少2个 */
	private GameAction[] actions;

	@Override
	public ActionMode getMode() {
		return ActionMode.ALL;
	}

	@Override
	void load(Element e) throws Exception {
		Element[] list = XmlUtils.getChildrenByName(e, "action");
		actions = new GameAction[list.length];
		for (int i = 0; i < list.length; i++) {
			actions[i] = GameAction.parseAction(list[i]);
		}
	}

	@Override
	public boolean action(SimulateObject sob, Map<String, Object> vars) {
		boolean ret = true;
		for (GameAction a : actions) {
			ret &= a.action(sob, vars);
		}
		return ret;
	}

	@Override
	public String getUI() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < actions.length - 1; i++) {
			sb.append(actions[i].getUI()).append(";");
		}
		sb.append(actions[actions.length - 1].getUI());
		return sb.toString();
	}

	@Override
	public Document toXml() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("action");
		root.setAttribute("mode", "all");
		for (GameAction a : actions) {
			root.appendChild(a.toXml().getDocumentElement());
		}
		doc.appendChild(root);
		return doc;
	}

	@Override
	public String getName() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < actions.length - 1; i++) {
			sb.append(actions[i].getName()).append(";");
		}
		sb.append(actions[actions.length - 1].getName());
		return sb.toString();
	}

}
