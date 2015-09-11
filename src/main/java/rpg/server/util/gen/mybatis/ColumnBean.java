package rpg.server.util.gen.mybatis;

import rpg.server.util.StringUtil;

public class ColumnBean {
	private String tableAlias;
	private String name;
	private int jdbcType;
	private int length;
	private boolean nullable;
	private int scale;
	private String remarks;
	private String defaultValue;
	// /////////////////////////////////////////////////////
	private String sname;
	private String gname;
	private String javaType;
	private String jdbcTypeName;
	private boolean identity;
	private boolean isSequenceColumn;
	private boolean BLOBColumn;
	private String camelCaseName;
	private boolean stringColumn;

	public ColumnBean(String tableAlias, String name, int jdbcType, int length,
			boolean nullable, int scale, String remarks, String defaultValue) {
		super();
		this.tableAlias = tableAlias;
		if (this.tableAlias == null) {
			this.tableAlias = "";
		}
		this.name = name;
		this.jdbcType = jdbcType;
		this.length = length;
		this.nullable = nullable;
		this.scale = scale;
		this.remarks = remarks;
		this.defaultValue = defaultValue;
		// 计算
		this.jdbcTypeName = JdbcTypeNameTranslator.getJdbcTypeName(jdbcType);
		this.javaType = JavaTypeTranslator.getJavaTypeName(jdbcType, scale,
				length);
		this.camelCaseName = StringUtil.getCamelCaseString(name, true);
		this.sname = "set" + camelCaseName;
		this.gname = "Boolean".equals(javaType) ? "is" + camelCaseName : "get"
				+ camelCaseName;
		this.BLOBColumn = "BINARY".equals(jdbcTypeName)
				|| "BLOB".equals(jdbcTypeName) || "CLOB".equals(jdbcTypeName)
				|| "LONGNVARCHAR".equals(jdbcTypeName)
				|| "LONGVARBINARY".equals(jdbcTypeName)
				|| "LONGVARCHAR".equals(jdbcTypeName)
				|| "NCLOB".equals(jdbcTypeName)
				|| "VARBINARY".equals(jdbcTypeName);
		this.stringColumn = "String".equals(jdbcTypeName);
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(int jdbcType) {
		this.jdbcType = jdbcType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJdbcTypeName() {
		return jdbcTypeName;
	}

	public void setJdbcTypeName(String jdbcTypeName) {
		this.jdbcTypeName = jdbcTypeName;
	}

	public boolean isIdentity() {
		return identity;
	}

	public void setIdentity(boolean identity) {
		this.identity = identity;
	}

	public boolean isSequenceColumn() {
		return isSequenceColumn;
	}

	public void setSequenceColumn(boolean isSequenceColumn) {
		this.isSequenceColumn = isSequenceColumn;
	}

	public boolean isBLOBColumn() {
		return BLOBColumn;
	}

	public void setBLOBColumn(boolean bLOBColumn) {
		BLOBColumn = bLOBColumn;
	}

	public String getCamelCaseName() {
		return camelCaseName;
	}

	public void setCamelCaseName(String camelCaseName) {
		this.camelCaseName = camelCaseName;
	}

	public boolean isStringColumn() {
		return stringColumn;
	}

	public void setStringColumn(boolean stringColumn) {
		this.stringColumn = stringColumn;
	}

}
