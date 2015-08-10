/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.server.core.script;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.template.Template;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 脚本模板
 */
public class TemplateScriptConfig extends SimpleGameScriptConfig {

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		String templateId = XmlUtils.getChildText(e, "template");
		Element[] list = XmlUtils.getChildrenByName(e, "param");
		HashMap<String, String> vars = new HashMap<String, String>(list.length);
		for (Element l : list) {
			vars.put(XmlUtils.getAttribute(l, "id"), XmlUtils.getText(l));
		}
		Template t = TemplateManager.getScriptTemplate(templateId);
		if (t != null) {
			script = GameScriptConfig.parseScript(XmlUtils.loadString(
					t.parse(vars)).getDocumentElement());
		} else {
			// TODO 记录LOG
		}
	}

	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return script.getInstance(vars);
	}

	@Override
	public String getUI() {
		return script.getUI();
	}
}
