package rpg.server.core.script;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.action.GameAction;
import rpg.server.core.condition.GameCondition;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;

/**
 * 默认脚本 <br/>
 * 最基本的脚本设定 <br/>
 * 由条件-条件满足时的动作-（条件不满足时的动作）-（移除时的动作）组成 <br/>
 * 
 */
public class DefaultGameScriptConfig extends GameScriptConfig {

	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return new DefaultGameScript(this, vars);
	}

	@Override
	public String getUI() {
		String ui = "";
		if (uitemplate != "") {
			Map<String, Object> vars = new HashMap<String, Object>(0);
			vars.put("action", action.getUI());
			vars.put("elseAction", elseAction.getUI());
			vars.put("antiAction", antiAction.getUI());
			vars.put("condition", condition.getUI());
			ui = TemplateManager.getTemplate(uitemplate).parse(vars);
		}
		return ui;
	}

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		Element sub = null;
		// 载入条件，可以为空
		// 例如，一些反复执行的怪物AI，并不必考虑任何条件，只是做动作即可
		condition = ((sub = XmlUtils.getChildByName(e, "condition")) == null) ? null
				: GameCondition.parseCondition(sub);
		// 载入动作，理论上可以为空
		action = ((sub = XmlUtils.getChildByName(e, "action")) == null) ? null
				: GameAction.parseAction(sub);
		elseAction = ((sub = XmlUtils.getChildByName(e, "elseAction")) == null) ? null
				: GameAction.parseAction(sub);
		antiAction = ((sub = XmlUtils.getChildByName(e, "antiAction")) == null) ? null
				: GameAction.parseAction(sub);
		// repeat = 0;
		removeWhenDone = (antiAction == null);
	}

	protected GameCondition condition; // 条件

	protected GameAction action; // 动作

	protected GameAction elseAction; // 当条件检测不满足时的动作，可以为空

	protected GameAction antiAction; // 反动作（移除脚本时要做的动作，可以为空）
}
