package rpg.server.core.event;

import rpg.server.core.obj.GameObject;

/**
 * 事件
 * 
 */
public class GameEvent {
	/** 事件类型 */
	private final GameEventType type;
	/** 事件发起者 */
	private final GameObject source;
	/** 事件目标,可能为null */
	private final GameObject target;
	/** 事件参数 */
	private final Object[] params;
	/** 无参数 */
	private final static Object[] NO_PARAM = new Object[0];

	/**
	 * @param type
	 * @param source
	 */
	public GameEvent(GameEventType type, GameObject source) {
		this(type, source, null, NO_PARAM);
	}

	/**
	 * 
	 * @param type
	 * @param source
	 * @param target
	 */
	public GameEvent(GameEventType type, GameObject source, GameObject target) {
		this(type, source, target, NO_PARAM);
	}

	/**
	 * 
	 * @param type
	 * @param source
	 * @param params
	 */
	public GameEvent(GameEventType type, GameObject source, Object... params) {
		this(type, source, null, params);
	}

	/**
	 * 
	 * @param type
	 * @param source
	 * @param target
	 * @param callback
	 * @param params
	 */
	public GameEvent(GameEventType type, GameObject source, GameObject target,
			Object... params) {
		this.source = source;
		this.target = target;
		this.type = type;
		this.params = params;
	}

	/**
	 * 获取第i各参数
	 * 
	 * @param i
	 * @return
	 */
	public final Object getParameter(int i) {
		return params[i];
	}

	/**
	 * 获取String型参数
	 * 
	 * @param i
	 * @return
	 */
	public final String getStringParameter(int i) {
		return (String) params[i];
	}

	/**
	 * 获取bool型参数
	 * 
	 * @param i
	 * @return
	 */
	public final boolean getBoolParameter(int i) {
		return ((Boolean) params[i]).booleanValue();
	}

	/**
	 * 获取byte型参数
	 * 
	 * @param i
	 * @return
	 */
	public final byte getByteParameter(int i) {
		return ((Byte) params[i]).byteValue();
	}

	/**
	 * 获取short型参数
	 * 
	 * @param i
	 * @return
	 */
	public final short getShortParameter(int i) {
		return ((Short) params[i]).shortValue();
	}

	/**
	 * 获取int型参数
	 * 
	 * @param i
	 * @return
	 */
	public final int getIntParameter(int i) {
		return ((Integer) params[i]).intValue();
	}

	/**
	 * 获取long型参数
	 * 
	 * @param i
	 * @return
	 */
	public final long getLongParameter(int i) {
		return ((Long) params[i]).longValue();
	}

	public final float getFloatParameter(int i) {
		return ((Float) params[i]).floatValue();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(type).append("|")
				.append(source == null ? "" : source.getId()).append("|")
				.append(target == null ? "" : target.getId()).append("|");
		for (Object para : params) {
			sb.append(para).append("|");
		}
		return sb.toString();
	}

	// ///////////////////////getter & setter//////////////////////////////
	/**
	 * @return the type
	 */
	public GameEventType getType() {
		return type;
	}

	/**
	 * @return the source
	 */
	public GameObject getSource() {
		return source;
	}

	/**
	 * @return the target
	 */
	public GameObject getTarget() {
		return target;
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}

}
