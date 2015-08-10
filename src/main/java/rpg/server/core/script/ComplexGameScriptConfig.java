package rpg.server.core.script;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;



/**
 * 复合脚本
 * 至少由2个子脚本组成，否则，应退化为SimpleGameScript
 *
 */
public abstract class ComplexGameScriptConfig extends GameScriptConfig {

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		//载入子脚本
		Element[] list = XmlUtils.getChildrenByName(e, "script");
		scripts = new GameScriptConfig[list.length];
		for (int i = 0; i < list.length; i++) {
			scripts[i] = GameScriptConfig.parseScript(list[i]);
//			scripts[i].setRepeat(false);	//子脚本强行置为不重复执行
//			scripts[i].setChild(true);		//置位子脚本的child标记
		}
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
	
	//////////////getters & setter////////////////////////
	/**
	 * @return the scripts
	 */
	public GameScriptConfig[] getScripts() {
		return scripts;
	}

	/**
	 * @param scripts the scripts to set
	 */
	public void setScripts(GameScriptConfig[] scripts) {
		this.scripts = scripts;
	}

	//////////////////////////////////////////////////////
	
	protected GameScriptConfig[] scripts;	//子脚本组，长度至少为2

}
