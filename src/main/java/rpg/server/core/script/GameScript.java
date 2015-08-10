package rpg.server.core.script;

import java.util.HashMap;
import java.util.Map;

import rpg.server.core.GameObject;
import rpg.server.core.SimulateObject;

/**
 * 脚本 <br/>
 * 是脚本配置在实际被加载时的实例 <br/>
 * 脚本的动态、个性化信息记录在GameScript中
 * 
 */
public abstract class GameScript {

	GameScript(GameScriptConfig config, Map<String, Object> vars) {
		setId(IdGen());// 设置脚本运行时ID,此ID可以用于在map中快速的检索到script实例,add by bluesky
		if (vars != null)
			this.vars.putAll(vars);

		if (config.overdue > 0) {
			overdue = System.currentTimeMillis() + config.overdue;
			// GameScriptManager.getInstance().addScript(this);
			checkOverdue = true;
		}
		checkCount = config.count < 0 ? false : true;
		count = config.count;
	}

	/**
	 * 添加到对象
	 * 
	 * @param object
	 * @return
	 */
	abstract void addTo(SimulateObject object);

	/**
	 * 从对象移除
	 */
	void remove() {
		vars.clear();
		GameScriptManager.getInstance().removeScript(this);
	}

	/**
	 * 获取脚本配置
	 * 
	 * @return
	 */
	abstract GameScriptConfig getConfig();

	/**
	 * 设置临时参数
	 * 
	 * @param tag
	 * @param value
	 */
	final void setVar(String tag, Object value) {
		vars.put(tag, value);
	}

	/**
	 * 获取临时参数
	 * 
	 * @param tag
	 * @return
	 */
	final Object getVar(String tag) {
		return vars.get(tag);
	}

	// ////////////////////getter & setter/////////////////////
	/**
	 * @return the vars
	 */
	public Map<String, Object> getVars() {
		return vars;
	}

	/**
	 * @param vars
	 *            the vars to set
	 */
	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}

	/**
	 * @return the listner
	 */
	public ScriptListner getListner() {
		return listner;
	}

	/**
	 * @param listner
	 *            the listner to set
	 */
	public void setListner(ScriptListner listner) {
		this.listner = listner;
	}

	/**
	 * @return the progress
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public final void setProgress(int progress) {
		this.progress = progress;
		if (progress == Integer.MAX_VALUE && listner != null)
			listner.onScriptDone(this);
	}

	/**
	 * @return the owner
	 */
	public final GameObject getOwner() {
		// TODO
		return null;
		// return GameObjectManager.getInstance().getObj(ownerId);
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public final void setOwner(SimulateObject owner) {
		this.ownerId = owner.getId();
	}

	/**
	 * @return the overdue
	 */
	public long getOverdue() {
		return overdue;
	}

	/**
	 * @param overdue
	 *            the overdue to set
	 */
	public void setOverdue(long overdue) {
		this.overdue = overdue;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	private static long IdGen() {
		return idCounter++;
	}

	// /////////////////////////////////////////////////////////////

	/*
	 * 临时参数表 <br/> 允许程序运行过程中传入约定好的参数，而不必在资源文件中写出
	 */
	protected Map<String, Object> vars = new HashMap<String, Object>(0);

	private ScriptListner listner; // 进度监听器，主要用于作为子脚本的时候，其父脚本监听进度

	private int progress = -1; // 脚本执行进度，Integer.MAX_VALUE表示执行完毕

	protected long ownerId; // 脚本实例的持有者

	protected boolean checkOverdue; // 确定是否有过期时间

	protected long overdue = -1; // 脚本实例过期时间

	protected boolean checkCount; // 确定是否有重复次数限制

	protected int count; // 检测次数计数器（递减至0则结束）

	private long id; // 脚本运行时ID

	private static long idCounter = 0;
}
