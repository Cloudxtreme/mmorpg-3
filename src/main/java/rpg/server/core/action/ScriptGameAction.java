package rpg.server.core.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.obj.SimulateObject;
import rpg.server.core.script.GameScriptConfig;
import rpg.server.core.template.Template;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 添加脚本类型的Action
 * 
 */
public class ScriptGameAction extends GameAction {

	private String name;

	private String scriptId;

	private Map<String, String> vars;
	/**
	 * 解析之后的Action实例
	 */
	private GameScriptConfig script;

	@Override
	void load(Element e) throws Exception {
		name = XmlUtils.getAttribute(e, "showname");
		scriptId = XmlUtils.getAttribute(e, "script");
		Element[] list = XmlUtils.getChildrenByName(e, "para");
		vars = new HashMap<String, String>(list.length);
		for (Element l : list) {
			vars.put(XmlUtils.getAttribute(l, "name"), XmlUtils.getText(l));
		}
		Template template = TemplateManager.getTemplate(scriptId);
		if (template != null) {
			script = GameScriptConfig.parseScriptFromTemplate(scriptId, vars);
		} else {
			throw new Exception(
					"load GameScriptConfig error,script config not found:"
							+ scriptId);
		}
	}

	@Override
	public boolean action(SimulateObject sob, Map<String, Object> vars) {
		return sob.addScript(script, vars).size() > 0;
	}

	@Override
	public ActionMode getMode() {
		return ActionMode.SCRIPT;
	}

	@Override
	public String getUI() {
		return script.getUI();
	}

	@Override
	public Document toXml() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("action");
		root.setAttribute("mode", "script");
		if (name != null && !name.trim().equals(""))
			root.setAttribute("showname", name);
		for (Iterator<Entry<String, String>> itr = vars.entrySet().iterator(); itr
				.hasNext();) {
			Element para = doc.createElement("para");
			Entry<String, String> entry = itr.next();
			para.setAttribute("name", entry.getKey());
			para.setTextContent(entry.getValue());
			root.appendChild(para);
		}
		doc.appendChild(root);
		return doc;
	}

	@Override
	public String getName() {
		return name;
	}

}
