package rpg.server.util.gen.mybatis;

import rpg.server.util.StringUtil;

/**
 * 自定生成列.
 */
public class GeneratedKey {

	/** 列名 */
	private String column;

	/** 是否为id */
	private boolean isIdentity;

	private String runtimeSqlStatement;

	/** 如果指定,则此值将被添加为生成selectKey元素的类型.此属性的值应该是"pre"或"post". */
	private String type;

	/**
	 * Instantiates a new generated key.
	 *
	 * @param column
	 *            the column
	 * @param configuredSqlStatement
	 *            the configured sql statement
	 * @param isIdentity
	 *            the is identity
	 * @param type
	 *            the type
	 */
	public GeneratedKey(String column, boolean isIdentity, String type) {
		this.column = column;
		this.type = type;
		this.isIdentity = isIdentity;
		// for mysql
		this.runtimeSqlStatement = "SELECT LAST_INSERT_ID()";
	}

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * Checks if is identity.
	 *
	 * @return true, if is identity
	 */
	public boolean isIdentity() {
		return isIdentity;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * This method is used by the iBATIS2 generators to know if the XML
	 * &lt;selectKey&gt; element should be placed before the insert SQL
	 * statement.
	 *
	 * @return true, if is placed before insert in ibatis2
	 */
	public boolean isPlacedBeforeInsertInIbatis2() {
		boolean rc;

		if (StringUtil.stringHasValue(type)) {
			rc = true;
		} else {
			rc = !isIdentity;
		}

		return rc;
	}

	public boolean isJdbcStandard() {
		return "JDBC".equals(runtimeSqlStatement); //$NON-NLS-1$
	}
}
