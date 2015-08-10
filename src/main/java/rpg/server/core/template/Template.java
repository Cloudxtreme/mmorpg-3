package rpg.server.core.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UI模板类
 * 
 */
public class Template {

	private static ThreadLocal<HashMap<String, Object>> vars = new ThreadLocal<HashMap<String, Object>>() {
		@Override
		protected HashMap<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	/**
	 * 分割字符串
	 */
	String[] parts;

	/**
	 * 变量集合
	 */
	private String[] variable;
	/**
	 * 变量的类型
	 */
	private String[] vartype;
	/**
	 * 翻译器ID，默认为null,直接将变量显示，无需翻译
	 */
	private String[] translatorid;

	static final Map<String, TemplateVarTranslator> translatorMap = new HashMap<String, TemplateVarTranslator>();

	/**
	 * 增加一个UI翻译器
	 * 
	 * @param id
	 *            翻译器ID
	 * @param translater
	 *            翻译器实例
	 */
	public static final void registerTranslator(String id,
			TemplateVarTranslator translater) {
		translatorMap.put(id, translater);
	}

	/**
	 * 
	 * @param data
	 */
	public Template(String data) {
		load(data);
	}

	/**
	 * 载入模板数据
	 * 
	 * @param data
	 */
	void load(String data) {
		Pattern p = Pattern.compile("\\{[^\\{^\\}]+\\}");
		Matcher m = p.matcher(data);
		ArrayList<String> p_array = new ArrayList<String>();
		ArrayList<String> v_array = new ArrayList<String>();
		ArrayList<String> t_array = new ArrayList<String>();
		ArrayList<String> i_array = new ArrayList<String>();
		int index = 0;
		// 使用循环将句子里所有的匹配字符串找出
		while (m.find()) {
			p_array.add(data.substring(index, m.start()));
			String str = data.substring(m.start() + 1, m.end() - 1);
			String[] t = str.split(":");// 变量类型和变量名之间用":"分割
			String vvalue = null, vtype = null, vid = null;
			if (t.length == 1) {// 默认变量类型为字符串
				vvalue = t[0];
			} else {
				vid = t[0];
				vtype = t[1];
				vvalue = t[2];
			}
			t_array.add(vtype);
			i_array.add(vid);
			v_array.add(vvalue);
			index = m.end();
		}
		p_array.add(data.substring(index, data.length()));
		parts = new String[p_array.size()];
		variable = new String[v_array.size()];
		vartype = new String[t_array.size()];
		translatorid = new String[i_array.size()];
		p_array.toArray(parts);
		v_array.toArray(variable);
		t_array.toArray(vartype);
		i_array.toArray(translatorid);
	}

	/**
	 * 解析模板，生成xml string
	 * 
	 * @param object
	 * @return
	 */
	public String parse(TemplateObject object) {
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		for (int i = 0; i < getVariable().length; i++) {
			if (object != null) {
				String s = object.getAttributeString(getVariable()[i]);
				if (translatorid[i] != null
						&& translatorMap.get(translatorid[i]) != null) {
					sb.append(translatorMap.get(translatorid[i]).translate(
							vartype[i], s.toString()));
				} else if (s != null)
					sb.append(s);
			}
			sb.append(parts[i + 1]);
		}
		return sb.toString();
	}

	/**
	 * 解析模板，生成xml string
	 * 
	 * @param vars
	 * @param object
	 * @return
	 */
	public String parse(Map<String, ?> vars, TemplateObject object) {
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		for (int i = 0; i < getVariable().length; i++) {
			Object s = vars.get(getVariable()[i]);
			if (s == null) {
				s = object.getAttributeString(getVariable()[i]);
			}
			if (translatorid[i] != null
					&& translatorMap.get(translatorid[i]) != null) {
				sb.append(translatorMap.get(translatorid[i]).translate(
						vartype[i], s.toString()));
			} else if (s != null)
				sb.append(s);
			sb.append(parts[i + 1]);
		}
		return sb.toString();
	}

	/**
	 * 解析模板，生成xml string
	 * 
	 * @param object
	 * @param vars
	 * @return
	 */
	public String parse(TemplateObject object, Map<String, ?> vars) {
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		for (int i = 0; i < getVariable().length; i++) {
			Object s = object.getAttributeString(getVariable()[i]);
			if (s == null || s.toString().trim().isEmpty()) {
				s = vars.get(getVariable()[i]);
			}
			if (translatorid[i] != null
					&& translatorMap.get(translatorid[i]) != null) {
				sb.append(translatorMap.get(translatorid[i]).translate(
						vartype[i], s.toString()));
			} else if (s != null)
				sb.append(s);
			sb.append(parts[i + 1]);
		}
		return sb.toString();
	}

	/**
	 * 解析模板，生成xml string
	 * 
	 * @param vars
	 * @return
	 */
	public String parse(Map<String, ?> vars) {
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		for (int i = 0; i < getVariable().length; i++) {
			Object s = vars.get(getVariable()[i]);
			if (translatorid[i] != null
					&& translatorMap.get(translatorid[i]) != null) {
				sb.append(translatorMap.get(translatorid[i]).translate(
						vartype[i], s.toString()));
			} else if (s != null)
				sb.append(s);
			sb.append(parts[i + 1]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	public String parse() {
		HashMap<String, Object> map = vars.get();
		String ui = parse(map);
		map.clear();
		return ui;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		for (int i = 0; i < getVariable().length; i++) {
			sb.append("{").append(getVariable()[i]).append("}");
			sb.append(parts[i + 1]);
		}
		return sb.toString();
	}

	/**
	 * 变量集合
	 */
	public String[] getVariable() {
		return variable;
	}

	public static void setVar(String key, Object value) {
		vars.get().put(key, value);
	}

	static Map<String, ?> getVarMap() {
		return vars.get();
	}

	/**
	 * 获取模板字节大小
	 * 
	 */
	public long getTemplateSize() {

		long temp = 0;
		for (String p : parts) {
			temp += p.getBytes().length;
		}
		return temp;
	}

	/**
	 * 还原UI
	 * 
	 * @return
	 */
	public String revertUI() {

		String temp = parts[0];
		for (int i = 0; i < getVariable().length; i++) {
			temp += "{" + getVariable()[i] + "}";
			temp += parts[i + 1];
		}
		return temp;
	}
}
