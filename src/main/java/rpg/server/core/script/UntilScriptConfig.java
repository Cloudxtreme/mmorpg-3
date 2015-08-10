package rpg.server.core.script;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;



/**
 * 包含若干个DefaultGameScriptConfig
 * 依次检测直到某一个子脚本条件成立<br/>
 * 条件不成立时，不执行elseAction	<br/>
 * 
 */
public class UntilScriptConfig extends GameScriptConfig {

	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return new UntilScript(this, vars);
	}

	@Override
	public String getUI() {
		String ui = "";
		if (uitemplate != "") {
			StringBuilder sb = new StringBuilder();
			Map<String, Object> vars = new HashMap<String, Object>(0);
			for (int i = 0; i < scripts.length; i++) {
				sb.append(scripts[i].getUI());	//TODO 需要某种段落标记
			}
			vars.put("scripts", sb.toString());		//need to be translated
			ui = TemplateManager.getTemplate(uitemplate).parse(vars);
		}
		return ui;
	}

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		//载入子脚本
		Element[] list = XmlUtils.getChildrenByName(e, "script");
		scripts = new DefaultGameScriptConfig[list.length];
		for (int i = 0; i < list.length; i++) {
			scripts[i] = (DefaultGameScriptConfig) GameScriptConfig.parseScript(list[i]);
		}
	}

	DefaultGameScriptConfig[] scripts;
}
