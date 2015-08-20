package rpg.server.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.w3c.dom.Element;

import rpg.server.util.io.XmlUtils;

/**
 * Condition、Action的一些元数据描述
 * 
 */
public class Metadata {

	/** 类标识 */
	private String classify;

	/** Condition或action类型 */
	private String type;

	/** 名称 */
	private String name = "";

	/** 对应的ui模版id */
	private String uitemplate = "";

	/** 参数列表,保证顺序 */
	private Map<String, MetadataParam> params = new LinkedHashMap<String, MetadataParam>(
			0);

	/**
	 * 载入元数据
	 * 
	 * @param e
	 *            xml节点
	 */
	public void load(Element e) {
		classify = e.getAttribute("classify");
		type = e.getAttribute("type");
		uitemplate = e.getAttribute("ui");
		name = e.getAttribute("name");
		Element[] list = XmlUtils.getChildrenByName(e, "para");
		for (Element sub : list) {
			MetadataParam p = new MetadataParam(sub);
			params.put(p.getName(), p);
		}
	}

	/**
	 * 根据传入的参数列表xml节点,解析参数列表
	 * 
	 * @param list
	 * @return
	 * @throws MetaParamException
	 */
	public Map<String, Object> parseParams(Element[] list)
			throws MetaParamException {
		Map<String, Object> args = new HashMap<String, Object>(0);
		Set<String> keys = new HashSet<String>(params.keySet());
		for (Element e : list) {
			String name = e.getAttribute("name");
			if (keys.remove(name)) {
				args.put(name, params.get(name).getValue(XmlUtils.getText(e)));
			} else {
				throw new MetaParamException("undefined parameter:" + name);
			}
		}
		for (String left : keys) {
			Object dft = params.get(left).getDefaultValue();
			if (dft == null)
				throw new MetaParamException("no default values:para-" + left);
			else
				args.put(left, dft);
		}
		return args;
	}

	/**
	 * 根据传入的参数列表map，解析参数列表
	 * 
	 * @param list
	 * @return
	 * @throws MetaParamException
	 */
	public Map<String, Object> parseParams(Map<String, String> list)
			throws MetaParamException {
		Map<String, Object> args = new HashMap<String, Object>(0);
		Set<String> keys = new HashSet<String>(params.keySet());
		for (Entry<String, String> e : list.entrySet()) {
			String name = e.getKey();
			if (keys.remove(name)) {
				args.put(name, params.get(name).getValue(e.getValue()));
			} else {
				throw new MetaParamException("undefined parameter:" + name);
			}
		}
		for (String left : keys) {
			Object dft = params.get(left).getDefaultValue();
			if (dft == null)
				throw new MetaParamException("no default values:para-" + left);
			else
				args.put(left, dft);
		}
		return args;
	}

	/**
	 * 根据参数名转化参数值
	 * 
	 * @param name
	 * @param str
	 * @return
	 */
	public Object getParamValue(String name, String str) {
		if (params.containsKey(name))
			return params.get(name).getValue(str);
		return str;
	}

	/**
	 * 根据参数名取默认值
	 * 
	 * @param name
	 * @return
	 */
	public Object getDefaultValue(String name) {
		if (params.containsKey(name))
			return params.get(name).getDefaultValue();
		return null;
	}

	// ********************************************
	// **********get set
	// ********************************************
	public String getClassify() {
		return classify;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getUitemplate() {
		return uitemplate;
	}

	public Map<String, MetadataParam> getParams() {
		return params;
	}
}
