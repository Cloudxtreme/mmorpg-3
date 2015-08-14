package rpg.server.core.script;

import java.util.Map;

import rpg.server.core.obj.GameObject;

/**
 * 默认脚本的实例 <br/>
 * 在被添加时：一次性立即检测条件，并根据检测结果执行相应的动作 <br/>
 * 在被移除时：如果有反动作，则执行 <br/>
 * 
 */
public class DefaultGameScript extends GameScript {

	DefaultGameScript(DefaultGameScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
		this.config = config;
	}

	/*
	 * 默认的，根据检测和执行结果设置ret临时变量 -1 条件检测失败 0 条件检测成功，但未能执行或执行失败 1 执行成功
	 * 对于那些需要特殊返回值的，可以自己在checkCondition或doAction方法中设置
	 */
	@Override
	void addTo(GameObject object) {
		setOwner(object);
		if (check()) {
			// //System.out.println("script check true at owner:" +
			// owner.getId());
			if (config.action == null || config.action.action(getOwner(), vars)) {
				vars.put("ret", 1);
			} else {
				vars.put("ret", 0);
			}
		} else {
			if (config.elseAction != null)
				config.elseAction.action(getOwner(), vars);
			vars.put("ret", -1);
		}
		if (getListner() != null) { // 如果有其它实例在监听本脚本实例的执行情况，设置执行进度和结果
			setProgress(Integer.MAX_VALUE);
		} else { // 不重复！
			if (config.removeWhenDone)
				remove();
		}
		// modified 2010-09-07 lincy
		// note: 一次性脚本，执行结果通过ret来区分，但无论如何，只要执行过了一次，都应通知上层执行完毕
	}

	@Override
	GameScriptConfig getConfig() {
		return config;
	}

	@Override
	void remove() {
		if (config.antiAction != null) {
			// 有antiAction的脚本，需要保证Action执行成功，才执行antiAction ;
			Object o = vars.get("ret");
			if (o != null && (Integer) o == 1)
				config.antiAction.action(getOwner(), vars);
		}
		super.remove();
	}

	/**
	 * 检测脚本条件
	 * 
	 * @return
	 */
	boolean check() {
		if (config.condition == null)
			return true;
		return config.condition.check(getOwner(), vars);
	}

	private DefaultGameScriptConfig config;
}
