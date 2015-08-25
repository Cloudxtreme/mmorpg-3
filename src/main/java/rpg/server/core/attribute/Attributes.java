package rpg.server.core.attribute;


/**
 * 属性名称枚举 <br/>
 * 方便程序中访问，避免字符串访问方式下的输入错误等 <br/>
 * 如果新定义属性，应在资源文件和本枚举类中同时添加
 * 
 */
public enum Attributes {
	/** 等级 */
	LV(""), ;

	private String name; // 属性默认名称（可以在AttrConfig里定义属性的个性名称）

	/** 是否以百分比形式显示 */
	private boolean isPercentage;

	private Attributes(String name) {
		this.name = name;
	}

	private Attributes(String name, boolean isPercentage) {
		this(name);
		this.isPercentage = isPercentage;
	}

	public String getName() {
		return name;
	}

	/**
	 * 是否以百分比形式显示
	 * 
	 * @return
	 */
	public boolean isPercentage() {
		return this.isPercentage;
	}

	public static Attributes fromString(String s) {
		try {
			return Attributes.valueOf(s.trim().toUpperCase());
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	/**
	 * 转化为显示字符串 <br/>
	 * 默认为整数
	 * 
	 * @param value
	 * @return
	 */
	public static String toShowString(Attributes id, float value) {
		return toShowString(id, value, 0);
	}

	/**
	 * 转化为显示字符串并指定小数点后位数
	 * 
	 * @param id
	 * @param value
	 * @param dot
	 * @return
	 */
	public static String toShowString(Attributes id, float value, int dot) {
		if (dot > 0) {
			String format = "%1$." + dot + "f";
			if (id.isPercentage) {
				return String.format(format, value * 100) + "%";
			} else {
				return String.format(format, value);
			}
		} else {
			if (id.isPercentage) {
				return String.valueOf(Math.round(value * 100)) + "%";
			} else {
				return String.valueOf(Math.round(value));
			}
		}
	}

	public static Attributes getAttributesByName(String name) {
		Attributes[] array = Attributes.values();
		for (Attributes attr : array) {
			if (attr.getName().equals(name)) {
				return attr;
			}
		}
		return null;
	}
}
