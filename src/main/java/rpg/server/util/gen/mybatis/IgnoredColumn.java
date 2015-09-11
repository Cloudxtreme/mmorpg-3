package rpg.server.util.gen.mybatis;

/**
 * 忽略的列<br>
 * 用来屏蔽不需要生成代码的列
 */
public class IgnoredColumn {

	/** 要忽略的列名 */
	private String columnName;

	/** 匹配列名的时候是否区分大小写 */
	private boolean isColumnNameDelimited;

	public IgnoredColumn(String columnName) {
		this.columnName = columnName;
		this.isColumnNameDelimited = false;
	}

	public IgnoredColumn(String columnName, boolean isColumnNameDelimited) {
		this.columnName = columnName;
		this.isColumnNameDelimited = isColumnNameDelimited;
	}

	/**
	 * 获取列名称
	 *
	 * @return 列名
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * 是否区分大小写
	 *
	 * @return true, 区分大小写
	 */
	public boolean isColumnNameDelimited() {
		return isColumnNameDelimited;
	}

	/**
	 * 设置是否区分大消息
	 *
	 * @param isColumnNameDelimited
	 *            是否区分大小写,true:区分
	 */
	public void setColumnNameDelimited(boolean isColumnNameDelimited) {
		this.isColumnNameDelimited = isColumnNameDelimited;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof IgnoredColumn)) {
			return false;
		}

		return columnName.equals(((IgnoredColumn) obj).getColumnName());
	}

	@Override
	public int hashCode() {
		return columnName.hashCode();
	}

}
