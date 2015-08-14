package rpg.server.core.condition;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.obj.GameObject;
import rpg.server.core.template.Template;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 模板类型的Condition<br />
 * 
 */
public class TemplateCondition extends GameCondition {
	/**
	 * 解析之后的GameCondition实例
	 */
	private GameCondition instance;

	TemplateCondition(ConditionMode mode) {
		super(mode);
	}

	@Override
	void load(Element e) throws Exception {
		String templateId = XmlUtils.getChildText(e, "template");
		Element[] list = XmlUtils.getChildrenByName(e, "para");
		Map<String, String> vars = new HashMap<String, String>(list.length);
		for (Element l : list) {
			vars.put(XmlUtils.getAttribute(l, "name"), XmlUtils.getText(l));
		}
		Template template = TemplateManager.getScriptTemplate(templateId);
		if (template != null) {
			instance = GameCondition.parseCondition(XmlUtils.loadString(
					template.parse(vars)).getDocumentElement());
		} else {
			throw new Exception("load condition error.template not found."
					+ templateId);
		}
	}

	@Override
	public boolean check(GameObject sob, Map<String, Object> vars) {
		return instance.check(sob, vars);
	}

	@Override
	public String getUI() {
		return instance.getUI();
	}

}
