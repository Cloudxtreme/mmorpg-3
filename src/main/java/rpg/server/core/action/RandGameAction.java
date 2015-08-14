package rpg.server.core.action;

import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.obj.GameObject;
import rpg.server.util.MathUtil;
import rpg.server.util.io.XmlUtils;

/**
 * 随机动作组 <br/>
 * 至少应有两个动作,否则应采用SimpleGameCondition
 * 
 */
public class RandGameAction extends GameAction {
	/** 子动作集合,至少2个 */
	private GameAction[] actions;
	/** 各动作权重 */
	private int[] probs;

	@Override
	public ActionMode getMode() {
		return ActionMode.RAND;
	}

	@Override
	void load(Element e) throws Exception {
		// 载入子动作
		Element[] list = XmlUtils.getChildrenByName(e, "action");
		actions = new GameAction[list.length];
		for (int i = 0; i < list.length; i++) {
			actions[i] = GameAction.parseAction(list[i]);
		}
		// 载入权重，如果没有这个属性，则取默认等权重
		probs = new int[list.length];
		String ps = XmlUtils.getAttribute(e, "probs");
		if (ps == null || ps.equals("")) {
			for (int i = 0; i < probs.length; i++)
				probs[i] = 1;
		} else {
			String[] probString = ps.split(" ");
			int len = Math.min(probs.length, probString.length);
			// 如果设置的权重数大于实际动作数，则多余的无效；
			// 如果设置的权重数小于实际动作数，则不足的用0补齐，即不会执行那些动作
			for (int i = 0; i < len; i++)
				probs[i] = Integer.parseInt(probString[i]);
		}
	}

	@Override
	public boolean action(GameObject sob, Map<String, Object> vars) {
		return getAction().action(sob, vars);
	}

	@Override
	public String getUI() {
		// 可能需要一些换行、缩进的tag
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < actions.length - 1; i++) {
			sb.append(actions[i].getUI()).append(";");
		}
		sb.append(actions[actions.length - 1].getUI());
		// 是否需要将概率显示出来？
		return sb.toString();
	}

	@Override
	public Document toXml() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("action");
		root.setAttribute("mode", "rand");
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
		return sb.toString();
	}

	/*
	 * 从子动作组中随机选择一个动作
	 */
	private GameAction getAction() {
		int chosen = MathUtil.randCategory(probs);
		if (chosen < 0)
			return null;
		else
			return actions[chosen];
	}

}
