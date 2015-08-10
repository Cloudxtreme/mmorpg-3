package rpg.server.core.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rpg.server.core.obj.SimulateObject;

/**
 * 全部加载执行的复合脚本实例 <br/>
 * 加载：每次加载时从配置中获取所有子脚本的实例，分别加载并监听其执行情况 <br/>
 * 移除：同时负责移除尚遗留的子脚本（如果是正常执行完毕，应该无遗留） <br/>
 * 进度监听：维护未完成列表，每当完成一个子脚本，移除之，当所有子脚本完成，则视为整个脚本完成 <br/>
 * 
 */
public class AllScript extends ComplexGameScript {

	AllScript(AllScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
		this.config = config;
	}

	@Override
	void addTo(SimulateObject object) {
		if (checkCount && count-- == 0 || // 如果有检测次数限制，且已达到，结束任务并从对象身上移除对应脚本
				checkOverdue && overdue < System.currentTimeMillis()) { // 如果有存在时间限制，且已过期，结束任务并从对象身上移除对应脚本
			remove();
			return;
		}
		setOwner(object);
		for (GameScriptConfig c : config.scripts) {
			GameScript ins = c.getInstance(vars);
			ins.setListner(this);
			scripts.add(ins);
			undone++;
			// ins.addTo(owner);
			// modified lincy 2010-09-07
			// note:
			// 子脚本是一次性脚本时，执行完毕立即删除了，undone将会变成空，导致后面的子脚本无法添加，故，首先全部加入undone，然后再addTo
			// owner
		}
		// for (Iterator<GameScript> itr = scripts.iterator(); itr.hasNext();) {
		// itr.next().addTo(owner);
		// }
		for (int i = 0; i < scripts.size(); i++) {
			scripts.get(i).addTo(getOwner());
		}
		// note:
		// 添加的过程与onScriptDone中的移出有可能出现ConcurrentModificationException，暂时采用计数移除的方式，可考虑更加可靠的方式
		// 2010-09-07
	}

	@Override
	GameScriptConfig getConfig() {
		return config;
	}

	@Override
	void remove() {
		for (GameScript gs : scripts) {
			gs.remove();
		}
		scripts.clear();
		super.remove();
	}

	public void onScriptDone(GameScript script) {
		int index = scripts.indexOf(script);
		if (index >= 0) { // 某个子脚本完成
			// script.remove(); //移除该子脚本
			// scripts.remove(index); //从未完成队列移出
			undone--;
		}
		// for (Iterator<GameScript> itr = undone.iterator(); itr.hasNext();) {
		// GameScript gs = itr.next();
		// if (gs.equals(script)) {
		// script.remove();
		// itr.remove();
		// break;
		// }
		// }
		if (undone == 0) {
			// if (scripts.size() == 0) { //所有子脚本执行完毕，则本脚本执行完毕
			// scripts.clear();
			if (getListner() != null) // 如果有监听者，则设置进度
				setProgress(Integer.MAX_VALUE);
			else { // 否则，自己处理自己的重复性问题
				if (config.repeat) { // 重复
					addTo(getOwner()); // 重新把自己加载给对象
				} else {
					if (config.removeWhenDone)
						remove();
				}
			}
		}
	}

	private AllScriptConfig config;

	private List<GameScript> scripts = new ArrayList<GameScript>(0); // 子脚本实例集
	// private List<GameScript> scripts = Collections.synchronizedList(new
	// ArrayList<GameScript>(0));

	private int undone = 0;

}
