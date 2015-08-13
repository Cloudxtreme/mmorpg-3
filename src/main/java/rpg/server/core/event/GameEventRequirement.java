package rpg.server.core.event;

import rpg.server.util.io.XmlUtils;

import org.w3c.dom.Element;

/**
 * 事件监听需求
 */
public class GameEventRequirement {

	/* 事件类型 */
	private GameEventType type;

	/* 事件源 */
	private GameEventChannel channel;

	/* 参数条件 */
	private GameEventParaCondition condition;

	public GameEventRequirement(GameEventType type, GameEventChannel channel,
			GameEventParaCondition condition) {
		this.type = type;
		this.channel = channel;
		this.condition = condition;
	}

	public GameEventRequirement(Element e) throws Exception {
		// 载入事件类型
		if (XmlUtils.getChildByName(e, "type") != null)
			try {
				type = GameEventType.valueOf(XmlUtils.getChildText(e, "type")
						.toUpperCase());
			} catch (IllegalArgumentException ex) {
				throw new Exception("事件因素不能识别：eventtype-"
						+ XmlUtils.getChildText(e, "type").toUpperCase());
			}
		else {
			throw new Exception("事件因素缺失");
		}
		// 载入事件渠道标识，默认为自身
		if (XmlUtils.getChildByName(e, "channel") != null)
			try {
				channel = GameEventChannel.valueOf(XmlUtils.getChildText(e,
						"channel").toUpperCase());
			} catch (IllegalArgumentException ex) {
				throw new Exception("事件源不能识别：eventchannel-"
						+ XmlUtils.getChildText(e, "channel").toUpperCase());
			}
		else {
			channel = GameEventChannel.SELF;
		}
		// 载入事件参数条件，默认文null
		if (XmlUtils.getChildByName(e, "condition") != null) {
			try {
				condition = new GameEventParaCondition(XmlUtils.getChildText(e,
						"condition"));
			} catch (Exception ex) {
				throw new Exception("事件参数条件不能识别：paracondition-"
						+ XmlUtils.getChildText(e, "condition").toUpperCase());
			}
		}
	}

	/**
	 * @return the type
	 */
	public GameEventType getType() {
		return type;
	}

	/**
	 * @return the channel
	 */
	public GameEventChannel getChannel() {
		return channel;
	}

	/**
	 * @return the condition
	 */
	public GameEventParaCondition getCondition() {
		return condition;
	}
}
