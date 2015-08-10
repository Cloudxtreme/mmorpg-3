package rpg.server.core.script;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.event.GameEvent;
import rpg.server.core.event.GameEventParaCondition;
import rpg.server.core.template.TemplateManager;
import rpg.server.util.io.XmlUtils;


/**
 * 触发脚本条件检测的因素为事件的简单脚本<br/>
 * 理论上可以减少定时检查带来的工作量<br/>
 * 但是，由于事件参数复杂，目前只能做到按类型和事件源匹配触发检测<br/>
 * 这势必导致理解上的混淆许多不必要的检测<br/>
 * 因此，除非事件本身比较明确（如：不带任何参数），否则不建议使用这种脚本<br/>
 * 如果能够完善事件的元数据格式，则可以在此类中做确切的事件匹配，方能达到按事件检测的初衷<br/>
 * modified by lincy 2010-06-09
 * 	增加了事件参数的简单条件判断
 *  @see GameEventParaCondition
 * 
 * 
 */
public class EventScriptConfig extends SimpleGameScriptConfig {

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		// 载入事件类型
		if (XmlUtils.getChildByName(e, "eventtype") != null)
			try {
				eventType = GameEvent.Type.valueOf(XmlUtils.getChildText(e,
						"eventtype").toUpperCase());
			} catch (IllegalArgumentException ex) {
				throw new Exception("载入脚本出错，事件因素不能识别：eventtype-"
						+ XmlUtils.getChildText(e, "eventtype").toUpperCase());
			}
		else {
			throw new Exception("载入脚本出错，事件因素缺失");
		}
		// 载入事件渠道标识，默认为自身
		if (XmlUtils.getChildByName(e, "eventsource") != null)
			try {
				eventChannel = GameEvent.Channel.valueOf(XmlUtils.getChildText(
						e, "eventsource").toUpperCase());
			} catch (IllegalArgumentException ex) {
				throw new Exception("载入脚本出错，事件源不能识别：eventsource-"
						+ XmlUtils.getChildText(e, "eventsource").toUpperCase());
			}
		else {
			eventChannel = GameEvent.Channel.SELF;
		}
		// 载入事件参数条件，默认为null
		Element[] list = XmlUtils.getChildrenByName(e, "paracondition");
		eventParaCond = new GameEventParaCondition[list.length];
		for (int i = 0; i < list.length; i++) {
			try {
				eventParaCond[i] = new GameEventParaCondition(XmlUtils.getText(list[i]));
			} catch (Exception ex) {
				throw new Exception("载入脚本出错，事件参数条件不能识别：paracondition-"
						+ XmlUtils.getText(list[i])
								.toUpperCase());
			}
		}
		// 载入子脚本加载对象，默认为原owner
		if (XmlUtils.getChildByName(e, "who") != null) {
			who = Byte.parseByte(XmlUtils.getChildText(e, "who"));
		}
	}

	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return new EventScript(this, vars);
	}

	@Override
	public String getUI() {
		String ui = "";
		if (uitemplate != "") {
			Map<String, Object> vars = new HashMap<String, Object>(0);
			vars.put("eventType", eventType); // need to be translated
			vars.put("eventChannel", eventChannel); // need to be translated
			vars.put("eventParaCond", eventParaCond.toString());
			vars.put("script", script.getUI());
			ui = TemplateManager.getTemplate(uitemplate).parse(vars);
		}
		return ui;
	}

	// ///////////////////getter & setter////////////////////////////////////
	/**
	 * @return the eventType
	 */
	public GameEvent.Type getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(GameEvent.Type eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the eventChannel
	 */
	public GameEvent.Channel getEventChannel() {
		return eventChannel;
	}

	/**
	 * @param eventChannel
	 *            the eventChannel to set
	 */
	public void setEventChannel(GameEvent.Channel eventChannel) {
		this.eventChannel = eventChannel;
	}

	/**
	 * @return the eventParaCond
	 */
	public GameEventParaCondition[] getEventParaCond() {
		return eventParaCond;
	}

	/**
	 * @param eventParaCond the eventParaCond to set
	 */
	public void setEventParaCond(GameEventParaCondition[] eventParaCond) {
		this.eventParaCond = eventParaCond;
	}

	public byte getWho() {
		return who;
	}

	public void setWho(byte who) {
		this.who = who;
	}
	// /////////////////////////////////////////////////////////////////////
	
	private GameEvent.Type eventType; // 触发该脚本的事件类型

	private GameEvent.Channel eventChannel; // 触发该脚本的事件渠道

	private GameEventParaCondition[] eventParaCond = null; // 事件参数条件
	
	private byte who;	// 内嵌脚本加载对象（0-owner默认，1-事件source，2-事件target）
}
