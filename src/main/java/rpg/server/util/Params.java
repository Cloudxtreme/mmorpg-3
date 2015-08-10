package rpg.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 参数传递简单包装对象<br>
 * 当传递参数数量不确定时,可以使用本类.
 * 
 * 设置值：new Param("k1", v1, "k2", v2);<br>
 * new Param().put("k1", v1).put("k2", v2);
 * 
 * 获取值：X x = param.get("x");这里会自动转换类型,无需强转
 */
public class Params {
	private static final String DEFAULT_KEY = "DEFAULT_KEY"; // 当只传入一个数据项时，自动用此KEY值，简化操作
	private final Map<String, Object> datas = new HashMap<>();

	/**
	 * 默认构造函数
	 */
	public Params() {
		this(new Object[] {});
	}

	/**
	 * 有参构造函数,内容为map形式<br>
	 * 
	 * @param params
	 *            参数
	 */
	public Params(Map<String, Object> datas) {
		if (datas != null) {
			this.datas.putAll(datas);
		}
	}

	/**
	 * 有参构造函数,内容为Key:Val形式<br>
	 * 例如：new Param("user", user, "param", param)
	 * 
	 * @param params
	 *            参数
	 */
	public Params(Object... params) {
		// 无参 返回空即可
		if (params == null || params.length == 0) {
			return;
		}
		// 当数据仅有一项是 使用默认KEY作为值 简化操作
		if (params.length == 1) {
			put(DEFAULT_KEY, params[0]);
		} else { // 处理成对参数
			int len = params.length;
			for (int i = 0; i < len; i += 2) {
				String key = (String) params[i];
				Object val = (Object) params[i + 1];
				put(key, val);
			}
		}
	}

	/**
	 * 添加新键值
	 * 
	 * @param key
	 *            关键字
	 * @param value
	 *            值
	 * @return
	 */
	public Params put(String key, Object value) {
		datas.put(key, value);
		return this;
	}

	/**
	 * 返回相应键值对应的value<br>
	 * 会自动根据接收值进行类型转换，无需强制转换处理。
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <K> K get(String key) {
		Object obj = datas.get(key);
		return obj == null ? null : (K) obj;
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取
	 * 
	 * @return
	 */
	public <K> K get() {
		return get(DEFAULT_KEY);
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个布尔型数据
	 * 
	 * @return
	 */
	public boolean getBoolean(String key) {
		return get(key);
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个布尔型数据
	 * 
	 * @return
	 */
	public boolean getBoolean() {
		return get();
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个int型数据
	 * 
	 * @return
	 */
	public int getInt(String key) {
		return get(key);
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个int型数据
	 * 
	 * @return
	 */
	public int getInt() {
		return get();
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个long型数据
	 * 
	 * @return
	 */
	public long getLong(String key) {
		return get(key);
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个long型数据
	 * 
	 * @return
	 */
	public long getLong() {
		return get();
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个String型数据
	 * 
	 * @return
	 */
	public String getString(String key) {
		return get(key);
	}

	/**
	 * 当只有单一数据项时 可以用此方法获取 返回一个String型数据
	 * 
	 * @return
	 */
	public String getString() {
		return get();
	}

	/**
	 * 数据条数
	 * 
	 * @return
	 */
	public int size() {
		return datas.size();
	}

	/**
	 * 是否包含某个key键
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return datas.containsKey(key);
	}

	/**
	 * 转化为数组
	 * 
	 * @return
	 */
	public Object[] toArray() {
		Object[] arr;
		// 数据 返回空数组
		if (datas.isEmpty()) {
			arr = new Object[0];
		} else if (datas.size() == 1 && datas.containsKey(DEFAULT_KEY)) { // 只有一个数据的是默认索引
			arr = new Object[] { datas.get(DEFAULT_KEY) };
		} else { // 成对出现的常规参数
			// 返回数组长度是map的2倍
			arr = new Object[datas.size() * 2];
			// 转化为数组
			int index = 0;
			for (Entry<String, Object> e : datas.entrySet()) {
				arr[index++] = e.getKey();
				arr[index++] = e.getValue();
			}
		}
		return arr;
	}

	/**
	 * 获取全部Key值
	 * 
	 * @return
	 */
	public Set<String> keySet() {
		return datas.keySet();
	}

	@Override
	public String toString() {
		return datas.toString();
	}
}