package rpg.server.core.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.Element;

import rpg.server.util.io.XmlReader;
import rpg.server.util.io.XmlReader.XmlElementParser;
import rpg.server.util.task.TaskForSchedule;

/**
 * 脚本实例管理器 <br/>
 * 负责超时脚本实例的清理 <br/>
 * 
 */
public class GameScriptManager extends TaskForSchedule {
	static {
		// 注册Xml解析器
		XmlReader.registerXmlElementParser(GameScriptConfig.class,
				new XmlElementParser<GameScriptConfig>() {
					public GameScriptConfig fromXml(Element e) {
						try {
							return GameScriptConfig.parseScript(e);
						} catch (Exception ex) {
							// TODO 记录LOG
							return null;
						}
					}
				});
	}

	/**
	 * @return the instance
	 */
	public static GameScriptManager getInstance() {
		return instance;
	}

	/**
	 * 添加被管理的脚本实例
	 * 
	 * @param script
	 */
	void addScript(GameScript script) {
		scripts.put(script.getId(), script);
	}

	/**
	 * 从管理器中清除脚本，该脚本应该已经被remove
	 * 
	 * @param gs
	 */
	void removeScript(GameScript gs) {
		scripts.remove(gs.getId());
	}

	/**
	 * 移除指定id脚本实例 <br/>
	 * 包括脚本从对象身上的移除，和实例从管理器列表中的移除
	 * 
	 * @param id
	 */
	public void removeScript(long id) {
		if (scripts.containsKey(id)) { // 先在不自动清理的脚本里找，通常是这部分会手动清理
			scripts.get(id).remove();// 这个remove里已经移除了脚本
		}
	}

	/**
	 * 批量移除指定id脚本实例
	 * 
	 * @param ids
	 */
	public void removeAllScript(Collection<Long> ids) {
		for (Iterator<Long> itr = ids.iterator(); itr.hasNext();) {
			removeScript(itr.next());
		}
	}

	/**
	 * 移除指定owner的所有脚本 <br/>
	 * 用于owner被清除时<br />
	 */
	public void removeScriptByOwner(long owner) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (Iterator<Map.Entry<Long, GameScript>> itr = scripts.entrySet()
				.iterator(); itr.hasNext();) {
			Map.Entry<Long, GameScript> entry = itr.next();
			GameScript gs = entry.getValue();
			if (gs.getOwner() != null && owner == gs.getOwner().getId()) {
				ids.add(entry.getKey());
			}
		}
		removeAllScript(ids);
	}

	/**
	 * 清除指定id指定owner的脚本
	 * 
	 * @param id
	 * @param owner
	 */
	public void removeScriptByOwner(long id, long owner) {
		if (scripts.containsKey(id)) { // 先在不自动清理的脚本里找，通常是这部分会手动清理
			if (owner == scripts.get(id).getOwner().getId()) {
				scripts.get(id).remove();
				scripts.remove(id);
			}
		}
	}

	@Override
	public void run() {
		for (Iterator<Map.Entry<Long, GameScript>> itr = scripts.entrySet()
				.iterator(); itr.hasNext();) {
			GameScript gs = itr.next().getValue();
			if (gs.getOwner() == null
					|| (gs.getOverdue() > 0 && gs.getOverdue() <= System
							.currentTimeMillis())) {
				itr.remove();
				gs.remove();
			}
		}
	}

	private GameScriptManager() {
		scripts = new ConcurrentHashMap<Long, GameScript>(0);
		// TODO
		// TaskManager.getInstance().addScheduledTask(this, TaskType.WORLD,
		// CHECK_PERIOD, CHECK_PERIOD);
	}

	private static GameScriptManager instance = new GameScriptManager();

	private Map<Long, GameScript> scripts; // 脚本实例id映射表

	/**
	 * @return the scriptsNonCheck
	 */
	public Map<Long, GameScript> getScripts() {
		return scripts;
	}

	private static final long CHECK_PERIOD = 3600000; // 定时清理间隔，暂定一小时

}
