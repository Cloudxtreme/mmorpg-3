package rpg.server.core.attribute;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.obj.GameObject;
import rpg.server.util.io.XmlUtils;

/**
 * 自定义公式属性配置
 * 
 */
public class FormulaAttrConfig extends AttrConfig {

	@Override
	public Map<Attributes, Attribute> getAttrMap(GameObject obj) {
		Map<Attributes, Attribute> attrMap = new HashMap<Attributes, Attribute>(
				count);
		for (Attributes id : attributeId) {
			attrMap.put(id, new FormulaAttribute(id, obj));
		}
		return attrMap;
	}

	@Override
	protected void load(Element e) {
		super.load(e);
		Element[] tempArray = XmlUtils.getChildrenByName(e, "attribute");
		formula = new HashMap<Attributes, String>(count);
		for (int i = 0; i < count; i++) {
			String sid = tempArray[i].getAttribute("id");
			Attributes id = Attributes.fromString(sid.trim());
			formula.put(id, tempArray[i].getAttribute("formula"));
		}
	}

	/**
	 * 根据id取计算公式
	 * 
	 * @param id
	 * @return
	 */
	String getFormula(Attributes id) {
		if (formula.containsKey(id))
			return formula.get(id);
		return null;
	}

	// 属性id - 自定义公式映射（仅custom = true时有效）
	private Map<Attributes, String> formula;

}
