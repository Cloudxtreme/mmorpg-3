package rpg.server.core.attribute;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.core.obj.GameObject;
import rpg.server.util.io.XmlReader;
import rpg.server.util.io.XmlReader.XmlElementParser;
import rpg.server.util.io.XmlUtils;
import rpg.server.util.log.Log;

/**
 * 属性配置<br />
 * 其完整列表是从XML配置文件里读出来的
 * 
 * @author lincy
 */
public abstract class AttrConfig {

	public static enum AttrType {

		FORMULA, // 公式
		// FALSE, // 写入程序公式
		MONSTER, // 怪物
		EQUIP;// 装备

		public static AttrType fromString(String s) {
			try {
				return AttrType.valueOf(s.trim().toUpperCase());
			} catch (IllegalArgumentException ex) {
				return null;
			}
		}
	}

	// 属性总数
	protected int count;
	// 属性id集合
	protected Attributes[] attributeId;
	// 属性id - 名称（中文）映射
	protected Map<Attributes, String> attributeName;
	// 属性id - 最小值映射
	protected Map<Attributes, Float> minValue;
	// 属性id - 最大值映射
	protected Map<Attributes, Float> maxValue;
	// 属性id - 相关属性id集映射
	// 相关属性是指本属性变化时，需要连带更新的其他属性
	protected Map<Attributes, Attributes[]> related;
	private static Attributes[] NO_RELATION = new Attributes[0];
	static {
		// 注册Xml解析器
		XmlReader.registerXmlElementParser(AttrConfig.class,
				new XmlElementParser<AttrConfig>() {

					public AttrConfig fromXml(Element e) {
						try {
							return AttrConfig.parseAttrConfig(e);
						} catch (Exception ex) {
							return null;
						}
					}
				});
	}

	public static AttrConfig parseAttrConfig(Element e) {
		if (e == null)
			return null;
		AttrType type = AttrType.fromString(XmlUtils.getAttribute(e, "custom"));
		AttrConfig config = null;
		switch (type) {
		case FORMULA:
			config = new FormulaAttrConfig();
			break;
		default:
			break;
		}
		if (config != null)
			config.load(e);
		return config;
	}

	public abstract Map<Attributes, Attribute> getAttrMap(GameObject obj);

	// **************************************************************************
	protected void load(Element e) {
		try {
			Element[] tempArray = XmlUtils.getChildrenByName(e, "attribute");
			count = tempArray.length;
			attributeId = new Attributes[count];
			attributeName = new HashMap<Attributes, String>(count);
			minValue = new HashMap<Attributes, Float>(count);
			maxValue = new HashMap<Attributes, Float>(count);

			related = new HashMap<Attributes, Attributes[]>(count);
			for (int i = 0; i < count; i++) {
				String sid = tempArray[i].getAttribute("id");
				Attributes id = Attributes.fromString(sid.trim());
				if (id == null) {
					Log.game.error(
							"load AttrConfig Error,attribute id {} not exist.",
							sid);
				}
				attributeId[i] = id;
				if (tempArray[i].getAttribute("name") != "") {
					attributeName.put(id, tempArray[i].getAttribute("name"));
				} else {
					attributeName.put(id, id.toString());
				}
				if (tempArray[i].getAttribute("min") != "") {
					minValue.put(id,
							Float.parseFloat(tempArray[i].getAttribute("min")));
				} else {
					minValue.put(id, 0.0f);
				}
				if (tempArray[i].getAttribute("max") != "") {
					maxValue.put(id,
							Float.parseFloat(tempArray[i].getAttribute("max")));
				} else {
					maxValue.put(id, Float.MAX_VALUE);
				}
				String rel = tempArray[i].getAttribute("related");
				String[] temprel = new String[0];
				if (!rel.equals("")) {
					temprel = rel.trim().split(" ");
				}
				Attributes[] relattr = new Attributes[temprel.length];
				for (int j = 0; j < temprel.length; j++) {
					relattr[j] = Attributes.fromString(temprel[j]);
				}
				related.put(id, relattr);
			}
		} catch (Exception ex) {
			Log.game.error("载入Attribute配置文件错误", ex);
		}
	}

	// **************************************************************************/
	/**
	 * 根据id返回属性名称
	 * 
	 * @param id
	 *            int
	 * @return String
	 */
	public String getAttributeName(Attributes id) {
		String name = attributeName.get(id);
		if (name == null || name.trim().equals(""))
			return id.getName();
		return attributeName.get(id);
	}

	/**
	 * 根据id取最大值
	 * 
	 * @param id
	 * @return
	 */
	public float getMaxValue(Attributes id) {
		return maxValue.get(id);
	}

	public void setMaxValue(Attributes id, float value) {
		Float f = this.getMaxValue(id);
		if (f != null) {
			this.maxValue.put(id, value);
		}
	}

	/**
	 * 根据id取最小值
	 * 
	 * @param id
	 * @return
	 */
	public float getMinValue(Attributes id) {
		return minValue.get(id);
	}

	/**
	 * 根据id取相关属性
	 * 
	 * @param id
	 * @return
	 */
	public Attributes[] getRelated(Attributes id) {
		return (related != null && related.get(id) != null) ? related.get(id)
				: NO_RELATION;
	}

	/**
	 * 获取属性总数
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 获取属性id集
	 * 
	 * @return
	 * @deprecated
	 */
	public Attributes[] getAttributeId() {
		return attributeId;
	}

	/**
	 * 是否存在某个属性key
	 * 
	 * @param attrId
	 * @return
	 */
	public boolean exist(String attrId) {
		for (Attributes attr : attributeId) {
			if (attr.name().equals(attrId.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
}
