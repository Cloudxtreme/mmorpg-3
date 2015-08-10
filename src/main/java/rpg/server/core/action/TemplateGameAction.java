package rpg.server.core.action;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.obj.SimulateObject;
import rpg.server.core.template.Template;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 模板类型的action
 */
public class TemplateGameAction extends GameAction {
	/** 解析之后的Action实例 */
	private GameAction instance;

	@Override
	void load(Element e) throws Exception {
		String templateId = XmlUtils.getAttribute(e, "template");
		Element[] list = XmlUtils.getChildrenByName(e, "para");
		Map<String, String> vars = new HashMap<String, String>(list.length);
		for (Element l : list) {
			vars.put(XmlUtils.getAttribute(l, "name"), XmlUtils.getText(l));
		}
		Template template = TemplateManager.getScriptTemplate(templateId);
		if (template != null) {
			instance = GameAction.parseAction(XmlUtils.loadString(
					template.parse(vars)).getDocumentElement());
		} else {
			throw new Exception("action template not found." + templateId);
		}
	}

	@Override
	public boolean action(SimulateObject sob, Map<String, Object> vars) {
		return instance.action(sob, vars);
	}

	@Override
	public ActionMode getMode() {
		return instance.getMode();
	}

	@Override
	public String getUI() {
		return instance.getUI();
	}

	@Override
	public Document toXml() throws ParserConfigurationException {
		return instance.toXml();
	}

	@Override
	public String getName() {
		return instance.getName();
	}

}
