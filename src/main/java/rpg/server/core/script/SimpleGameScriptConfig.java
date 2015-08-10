package rpg.server.core.script;

import org.w3c.dom.Element;

import rpg.server.util.io.XmlUtils;



/**
 * 简单脚本	<br/>
 * 由一个内部脚本和一些其它自定义控制因素组成	<br/>
 * 控制因素决定内部的添加或移除
 */
public abstract class SimpleGameScriptConfig extends GameScriptConfig {

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		script = GameScriptConfig.parseScript(XmlUtils.getChildByName(e, "script"));
//		script.repeat = false;
		//载入等待标示
		if (XmlUtils.getChildByName(e, "wait") != null)
			wait = Boolean.parseBoolean(XmlUtils.getChildText(e, "wait"));
	}
	
	/**
	 * @param wait the wait to set
	 */
	public void setWait(boolean wait) {
		this.wait = wait;
	}

	/**
	 * @return the wait
	 */
	public boolean isWait() {
		return wait;
	}
	
	protected GameScriptConfig script;	// 内嵌脚本
	
	private boolean wait = true;	//如果事件频繁触发，是否要等待上一次执行完毕
	
}
