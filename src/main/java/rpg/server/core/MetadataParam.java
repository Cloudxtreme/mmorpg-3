package rpg.server.core;

import org.w3c.dom.Element;

public class MetadataParam {
	/** 参数名 */
	private String name;
	/**
	 * 参数类型<br>
	 * 目前支持int,short,byte,boolean,long,string,float
	 */
	private String dataType;
	/**
	 * 参数默认值<br>
	 * 如果没有默认值，取null
	 */
	private Object defaultValue = null;

	MetadataParam(Element e) {
		name = e.getAttribute("name");
		dataType = e.getAttribute("class");
		if (e.getAttributeNode("default") != null)
			defaultValue = getValue(e.getAttribute("default"));
	}

	Object getValue(String str) {
		if ("byte".equalsIgnoreCase(dataType)) {
			return Byte.parseByte(str);
		}
		if ("short".equalsIgnoreCase(dataType)) {
			return Short.parseShort(str);
		}
		if ("int".equalsIgnoreCase(dataType)
				|| "integer".equalsIgnoreCase(dataType)) {
			return Integer.parseInt(str);
		}
		if ("long".equalsIgnoreCase(dataType)) {
			return Long.parseLong(str);
		}
		if ("float".equalsIgnoreCase(dataType)) {
			return Float.parseFloat(str);
		}
		if ("double".equalsIgnoreCase(dataType)) {
			return Double.parseDouble(str);
		}
		if ("char".equalsIgnoreCase(dataType)) {
			return str.toCharArray()[0];
		}
		if ("boolean".equalsIgnoreCase(dataType)
				|| "bool".equalsIgnoreCase(dataType)) {
			return Boolean.parseBoolean(str);
		}
		if ("string".equalsIgnoreCase(dataType)
				|| "char".equalsIgnoreCase(dataType)) {
			return str;
		}
		return str;
	}

	/**
	 * 获取参数名
	 * 
	 * @return 参数名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取参数类型
	 * 
	 * @return 参数类型
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 获取参数默认值
	 * 
	 * @return 参数默认值
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

}
