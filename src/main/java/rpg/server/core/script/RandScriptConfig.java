package rpg.server.core.script;

import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.util.MathUtil;
import rpg.server.util.io.XmlUtils;



/**
 * 随机加载执行的复合脚本	<br/>
 * 加载时随机选择一个子脚本加载并执行	<br/>
 * 当该子脚本执行成功，则算整个脚本执行成功	<br/>
 *
 */
public class RandScriptConfig extends ComplexGameScriptConfig {

	@Override
	void parse(Element e) throws Exception {
		super.parse(e);
		//载入权重，如果没有这个属性，则取默认等权重
		probs = new int[scripts.length];
		String ps = XmlUtils.getAttribute(e, "probs");
		if (ps == null || ps.equals("")) {
			for (int i = 0; i < probs.length; i++)
				probs[i] = 1;
		} else {
			String[] probString = ps.split(" ");
			int len = Math.min(probs.length, probString.length);
			//如果设置的权重数大于实际子脚本数，则多余的无效；
			//如果设置的权重数小于实际子脚本数，则不足的用0补齐，即不会执行那些动作
			for (int i = 0; i < len; i++)
				probs[i] = Integer.parseInt(probString[i]);
		}
	}
	
	GameScriptConfig rand() {
		int index = MathUtil.randCategory(probs);
		if (index < 0)	//理论上不会发生
			return null;
		return scripts[index];
	}
	
	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return new RandScript(this, vars);
	}

	/////////////getters & setters////////////////
	/**
	 * @return the probs
	 */
	public int[] getProbs() {
		return probs;
	}

	/**
	 * @param probs the probs to set
	 */
	public void setProbs(int[] probs) {
		this.probs = probs;
	}
	
	////////////////////////////////////////////////
	
	private int[] probs;	//各子脚本权重

}
