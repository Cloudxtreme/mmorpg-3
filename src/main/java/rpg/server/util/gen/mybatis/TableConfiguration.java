package rpg.server.util.gen.mybatis;

import static rpg.server.util.EqualsUtil.areEqual;
import static rpg.server.util.HashCodeUtil.SEED;
import static rpg.server.util.HashCodeUtil.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import rpg.server.util.StringUtil;
import rpg.server.util.io.XmlUtils;

public class TableConfiguration  {

	/** 是否生成insert和insertSelective方法,对应xml属性enableInsert */
	private boolean insertStatementEnabled = true;

	/** 是否生成selectByPrimaryKey方法,对应xml属性enableSelectByPrimaryKey */
	private boolean selectByPrimaryKeyStatementEnabled = true;

	/** 是否生成selectByExample方法,对应xml属性enableSelectByExample */
	private boolean selectByExampleStatementEnabled = true;

	/**
	 * 是否生成updateByPrimaryKey和updateByPrimaryKeySelective方法,
	 * 对应xml属性enableUpdateByPrimaryKey
	 */
	private boolean updateByPrimaryKeyStatementEnabled = true;

	/** 是否生成deleteByPrimaryKey方法,对应xml属性enableDeleteByPrimaryKey */
	private boolean deleteByPrimaryKeyStatementEnabled = true;

	/** 是否生成deleteByExample方法 ,对应xml属性enableDeleteByExample */
	private boolean deleteByExampleStatementEnabled = true;

	/** 是否生成countByExample方法,对应xml属性enableCountByExample */
	private boolean countByExampleStatementEnabled = true;

	/**
	 * 是否生成updateByExample和updateByExampleSelective方法,
	 * 对应xml属性enableUpdateByExample
	 */
	private boolean updateByExampleStatementEnabled = true;

	/** 忽略生成的类 */
	private Map<IgnoredColumn, Boolean> ignoredColumns = new HashMap<IgnoredColumn, Boolean>();

	/** The generated key. */
	private GeneratedKey generatedKey;
	/** The catalog. */
	private String catalog;

	/** The schema. */
	private String schema;
	/** 表名 */
	private String tableName;

	/** 生成对象的基本名称,如果没有指定则根据表名来生成 */
	private String domainObjectName;

	/** 数据库查询用别名 */
	private String alias;
	/** 是否保留大小写格式 */
	private boolean delimitIdentifiers;

	private TableConfiguration() {
	}

	public static TableConfiguration parseTableConfiguration(Element ele) {
		TableConfiguration tc = new TableConfiguration();
		tc.catalog = XmlUtils.getAttribute(ele, "catalog"); //$NON-NLS-1$
		tc.schema = XmlUtils.getAttribute(ele, "schema"); //$NON-NLS-1$
		tc.tableName = XmlUtils.getAttribute(ele, "tableName");

		tc.domainObjectName = XmlUtils.getAttribute(ele, "domainObjectName");
		tc.alias = XmlUtils.getAttribute(ele, "alias");
		// 方法生成开关
		String enableInsert = XmlUtils.getAttribute(ele, "enableInsert");
		if (StringUtil.stringHasValue(enableInsert)) {
			tc.insertStatementEnabled = Boolean.parseBoolean(enableInsert);
		}
		String enableSelectByPrimaryKey = XmlUtils.getAttribute(ele,
				"enableSelectByPrimaryKey");
		if (StringUtil.stringHasValue(enableSelectByPrimaryKey)) {
			tc.selectByPrimaryKeyStatementEnabled = Boolean
					.parseBoolean(enableSelectByPrimaryKey);
		}
		String enableSelectByExample = XmlUtils.getAttribute(ele,
				"enableSelectByExample");
		if (StringUtil.stringHasValue(enableSelectByExample)) {
			tc.selectByExampleStatementEnabled = Boolean
					.parseBoolean(enableSelectByExample);
		}
		String enableUpdateByPrimaryKey = XmlUtils.getAttribute(ele,
				"enableUpdateByPrimaryKey");
		if (StringUtil.stringHasValue(enableUpdateByPrimaryKey)) {
			tc.updateByPrimaryKeyStatementEnabled = Boolean
					.parseBoolean(enableUpdateByPrimaryKey);
		}
		String enableDeleteByPrimaryKey = XmlUtils.getAttribute(ele,
				"enableDeleteByPrimaryKey");
		if (StringUtil.stringHasValue(enableDeleteByPrimaryKey)) {
			tc.deleteByPrimaryKeyStatementEnabled = Boolean
					.parseBoolean(enableDeleteByPrimaryKey);
		}
		String enableDeleteByExample = XmlUtils.getAttribute(ele,
				"enableDeleteByExample");
		if (StringUtil.stringHasValue(enableDeleteByExample)) {
			tc.deleteByExampleStatementEnabled = Boolean
					.parseBoolean(enableDeleteByExample);
		}
		String enableCountByExample = XmlUtils.getAttribute(ele,
				"enableCountByExample");
		if (StringUtil.stringHasValue(enableCountByExample)) {
			tc.countByExampleStatementEnabled = Boolean
					.parseBoolean(enableCountByExample);
		}
		String enableUpdateByExample = XmlUtils.getAttribute(ele,
				"enableUpdateByExample");
		if (StringUtil.stringHasValue(enableUpdateByExample)) {
			tc.updateByExampleStatementEnabled = Boolean
					.parseBoolean(enableUpdateByExample);
		}
		String delimitIdentifiers = XmlUtils.getAttribute(ele,
				"delimitIdentifiers");
		if (StringUtil.stringHasValue(delimitIdentifiers)) {
			tc.delimitIdentifiers = Boolean.parseBoolean(delimitIdentifiers);
		}

		tc.parseIgnoreColumn(ele);
		tc.parseGeneratedKey(ele);
		return tc;
	}

	/**
	 * 解析忽略生成的列<br>
	 * 
	 * @param ele
	 *            根节点
	 * 
	 */
	private void parseIgnoreColumn(Element ele) {
		Element[] eles = XmlUtils.getChildrenByName(ele, "ignoreColumn");
		for (int i = 0; i < eles.length; i++) {
			Element e = eles[i];
			String columnName = XmlUtils.getAttribute(e, "column");
			IgnoredColumn ic = new IgnoredColumn(columnName);
			String delimitedColumnName = XmlUtils.getAttribute(e,
					"delimitedColumnName");
			if (StringUtil.stringHasValue(delimitedColumnName)) {
				ic.setColumnNameDelimited(Boolean
						.parseBoolean(delimitedColumnName));
			}
			this.addIgnoredColumn(ic);
		}

	}

	private void parseGeneratedKey(Element ele) {
		Element e = XmlUtils.getChildByName(ele, "generatedKey");
		if (e == null)
			return;
		String column = XmlUtils.getAttribute(e, "column");
		boolean identity = StringUtil.isTrue(XmlUtils.getAttribute(e,
				"identity"));
		String type = XmlUtils.getAttribute(e, "type");
		GeneratedKey gk = new GeneratedKey(column, identity, type);
		this.generatedKey = gk;
	}

	/**
	 * Adds the ignored column.
	 *
	 * @param ignoredColumn
	 *            the ignored column
	 */
	public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
		ignoredColumns.put(ignoredColumn, Boolean.FALSE);
	}

	/**
	 * This method returns a List of Strings. The values are the columns that
	 * were specified to be ignored in the table, but do not exist in the table.
	 * 
	 * @return a List of Strings - the columns that were improperly configured
	 *         as ignored columns
	 */
	public List<String> getIgnoredColumnsInError() {
		List<String> answer = new ArrayList<String>();

		for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns
				.entrySet()) {
			if (Boolean.FALSE.equals(entry.getValue())) {
				answer.add(entry.getKey().getColumnName());
			}
		}

		return answer;
	}

	/**
	 * Checks if is column ignored.
	 *
	 * @param columnName
	 *            the column name
	 * @return true, if is column ignored
	 */
	public boolean isColumnIgnored(String columnName) {
		for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns
				.entrySet()) {
			IgnoredColumn ic = entry.getKey();
			if (ic.isColumnNameDelimited()) {
				if (columnName.equals(ic.getColumnName())) {
					entry.setValue(Boolean.TRUE);
					return true;
				}
			} else {
				if (columnName.equalsIgnoreCase(ic.getColumnName())) {
					entry.setValue(Boolean.TRUE);
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TableConfiguration)) {
			return false;
		}

		TableConfiguration other = (TableConfiguration) obj;

		return areEqual(this.tableName, other.tableName);
	}

	@Override
	public int hashCode() {
		int result = SEED;
		result = hash(result, tableName);

		return result;
	}

	@Override
	public String toString() {
		return DBGenConfig.composeFullyQualifiedTableName("", "",
				tableName, '.');
	}

	public boolean isInsertStatementEnabled() {
		return insertStatementEnabled;
	}

	public boolean isSelectByPrimaryKeyStatementEnabled() {
		return selectByPrimaryKeyStatementEnabled;
	}

	public boolean isSelectByExampleStatementEnabled() {
		return selectByExampleStatementEnabled;
	}

	public boolean isUpdateByPrimaryKeyStatementEnabled() {
		return updateByPrimaryKeyStatementEnabled;
	}

	public boolean isDeleteByPrimaryKeyStatementEnabled() {
		return deleteByPrimaryKeyStatementEnabled;
	}

	public boolean isDeleteByExampleStatementEnabled() {
		return deleteByExampleStatementEnabled;
	}

	public boolean isCountByExampleStatementEnabled() {
		return countByExampleStatementEnabled;
	}

	public boolean isUpdateByExampleStatementEnabled() {
		return updateByExampleStatementEnabled;
	}

	public GeneratedKey getGeneratedKey() {
		return generatedKey;
	}

	public String getTableName() {
		return tableName;
	}

	public String getDomainObjectName() {
		return domainObjectName;
	}

	public String getAlias() {
		return alias;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getSchema() {
		return schema;
	}

	public boolean isDelimitIdentifiers() {
		return delimitIdentifiers;
	}

}
