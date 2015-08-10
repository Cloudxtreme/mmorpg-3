package rpg.server.core.event;

import rpg.server.core.event.GameEvent.Channel;
import rpg.server.core.event.GameEvent.Type;
import rpg.server.util.io.XmlUtils;

import org.w3c.dom.Element;


/**
 * 事件监听需求
 */
public class GameEventRequirement {

	/* 事件类型 */
	private GameEvent.Type type;

	/* 事件源 */
	private GameEvent.Channel channel;

	/* 参数条件 */
	private GameEventParaCondition condition;

	public GameEventRequirement(Type type, Channel channel,
			GameEventParaCondition condition) {
		this.type = type;
		this.channel = channel;
		this.condition = condition;
	}

	public GameEventRequirement(Element e) throws Exception {
		// 载入事件类型
		if (XmlUtils.getChildByName(e, "type") != null)
			try {
				type = GameEvent.Type.valueOf(XmlUtils.getChildText(e, "type")
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
				channel = GameEvent.Channel.valueOf(XmlUtils.getChildText(e,
						"channel").toUpperCase());
			} catch (IllegalArgumentException ex) {
				throw new Exception("事件源不能识别：eventchannel-"
						+ XmlUtils.getChildText(e, "channel").toUpperCase());
			}
		else {
			channel = GameEvent.Channel.SELF;
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
	public GameEvent.Type getType() {
		return type;
	}

	/**
	 * @return the channel
	 */
	public GameEvent.Channel getChannel() {
		return channel;
	}

	/**
	 * @return the condition
	 */
	public GameEventParaCondition getCondition() {
		return condition;
	}
}
