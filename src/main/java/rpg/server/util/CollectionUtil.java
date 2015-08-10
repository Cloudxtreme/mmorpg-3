package rpg.server.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtil {
	// 专供ofList类使用 对于数组类型进行特殊处理
	private static final List<?> OFLIST_ARRAY_CLASS = CollectionUtil.ofList(
			int[].class, long[].class, boolean[].class, byte[].class,
			double[].class);

	/**
	 * 构造List对象
	 * 
	 * 如果传入的是参数仅仅为一个对象数组(Object[])或原生数组(int[], long[]等)
	 * 那么表现结果表现是不同的，Object[]为[obj[0], obj[1], obj[2]] 而原生数组则为[[int[0],
	 * int[1]，int[2]]] 多了一层嵌套，需要对原生数组进行特殊处理。
	 * 
	 * @param <T>
	 * @param ts
	 * @return
	 */
	@SafeVarargs
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> ofList(T... ts) {
		List result = new ArrayList();

		// 对Null进行特殊处理
		if (ts == null) {
			result.add(null);
			return result;
		}

		// 对单独的原始数组类型进行特殊处理
		if (ts.length == 1 && ts[0] != null
				&& OFLIST_ARRAY_CLASS.contains(ts[0].getClass())) {
			if (ts[0] instanceof int[]) {
				int[] val = (int[]) ts[0];
				for (int v : val) {
					result.add(v);
				}
			} else if (ts[0] instanceof long[]) {
				long[] val = (long[]) ts[0];
				for (long v : val) {
					result.add(v);
				}
			} else if (ts[0] instanceof boolean[]) {
				boolean[] val = (boolean[]) ts[0];
				for (boolean v : val) {
					result.add(v);
				}
			} else if (ts[0] instanceof byte[]) {
				byte[] val = (byte[]) ts[0];
				for (byte v : val) {
					result.add(v);
				}
			} else if (ts[0] instanceof double[]) {
				double[] val = (double[]) ts[0];
				for (double v : val) {
					result.add(v);
				}
			}
		} else { // 对象数组
			for (T t : ts) {
				result.add(t);
			}
		}

		return result;
	}

	/**
	 * 构造Map对象
	 * 
	 * @param <T>
	 * @param ts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> ofMap(Object... params) {
		LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();

		// 无参 返回空即可
		if (params == null || params.length == 0) {
			return result;
		}

		// 处理成对参数
		int len = params.length;
		for (int i = 0; i < len; i += 2) {
			K key = (K) params[i];
			V val = (V) params[i + 1];

			result.put(key, val);
		}

		return result;
	}
}
