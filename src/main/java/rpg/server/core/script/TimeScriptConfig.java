package rpg.server.core.script;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.template.TemplateManager;
import rpg.server.util.MathUtil;
import rpg.server.util.io.XmlUtils;

/**
 * 触发脚本条件检测的因素为时间的简单脚本<br/>
 * 即：定时检测<br/>
 * 可以是一次性的，也可以是按一定间隔定期的 <br/>
 * 如果firstTime和period均为0，则可退化为DefaultGameScriptConfig
 * 
 */
public class TimeScriptConfig extends SimpleGameScriptConfig {

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		// 载入初次检测时间，如果不含有此节点，则取默认值""
		if (XmlUtils.getChildByName(e, "first") != null)
			firstTime = XmlUtils.getChildText(e, "first");
		// 载入检测间隔，如果不含有此节点，则取默认值0
		if (XmlUtils.getChildByName(e, "period") != null)
			period = Long.parseLong(XmlUtils.getChildText(e, "period")) * 1000L; // 由秒换算为毫秒
	}

	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return new TimeScript(this, vars);
	}

	@Override
	public String getUI() {
		String ui = "";
		if (uitemplate != "") {
			Map<String, Object> vars = new HashMap<String, Object>(0);
			vars.put("period", period);
			vars.put("firstTime", firstTime);
			vars.put("script", script.getUI());
			ui = TemplateManager.getTemplate(uitemplate).parse(vars);
		}
		return ui;
	}

	/**
	 * 获取脚本初始延迟
	 * 
	 * @return
	 */
	long getFirstDelay() {
		if (firstTime.equals(""))
			return 0;
		else if (MathUtil.isNumeric(firstTime)) {
			return Integer.parseInt(firstTime) * 1000L;// 由秒换算为毫秒
		} else {
//			try {
//				return new CronExpression(firstTime).getNextValidTimeAfter(
//						Calendar.getInstance().getTime()).getTime()
//						- System.currentTimeMillis();
//			} catch (ParseException e) {
//				Logger.error(Logger.LoggerFunction.RUNTIME,e, "脚本执行时间解析出错：" + firstTime);
//				return -1;
//			}
			return -1;
		}
	}

	// ////////////////////////getter & setter///////////////////////
	/**
	 * @return the firstTime
	 */
	public String getFirstTime() {
		return firstTime;
	}

	/**
	 * @param firstTime
	 *            the firstTime to set
	 */
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	/**
	 * @return the period
	 */
	public long getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(long period) {
		this.period = period;
	}

	// /////////////////////////////////////////////////////////////////

	private String firstTime = ""; // 初次检测时间（CronExpression），需要立即检测的脚本firstTime=""

	private long period = 0L; // 检测间隔（ms），一次性脚本period=0

}
