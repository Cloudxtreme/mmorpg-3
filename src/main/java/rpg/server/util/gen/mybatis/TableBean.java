package rpg.server.util.gen.mybatis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rpg.server.util.StringUtil;

public class TableBean {
	private String catalog;
	private String schema;
	/** DB实际中的表名称 */
	private String tableName;
	/** 实例名称,驼峰 */
	private String objName;
	/** 别名,查询用 */
	private String alias;
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
	/** 主键列 */
	private List<ColumnBean> primaryKeyColumns = new ArrayList<ColumnBean>();
	/** 基础数据列 */
	private List<ColumnBean> baseColumns = new ArrayList<ColumnBean>();
	/** blob数据列 */
	private List<ColumnBean> blobColumns = new ArrayList<ColumnBean>();
	/** 所有的 */
	private List<ColumnBean> columnsList = new ArrayList<ColumnBean>();

	public TableBean(String catalog, String schema, String tableName,
			String objName, String alias, boolean insertStatementEnabled,
			boolean selectByPrimaryKeyStatementEnabled,
			boolean selectByExampleStatementEnabled,
			boolean updateByPrimaryKeyStatementEnabled,
			boolean deleteByPrimaryKeyStatementEnabled,
			boolean deleteByExampleStatementEnabled,
			boolean countByExampleStatementEnabled,
			boolean updateByExampleStatementEnabled) {
		this.catalog = catalog;
		this.schema = schema;
		this.tableName = tableName;
		this.objName = objName;
		if (StringUtil.isEmpty(objName)) {
			this.objName = StringUtil.getCamelCaseString(this.tableName, true);
		}
		this.alias = alias;
		if (this.alias == null) {
			this.alias = "";
		}
		this.insertStatementEnabled = insertStatementEnabled;
		this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
		this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
		this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
		this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
		this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
		this.countByExampleStatementEnabled = countByExampleStatementEnabled;
		this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
	}

	public void addColumn(ColumnBean cb) {
		if (cb.isBLOBColumn()) {
			blobColumns.add(cb);
		} else {
			baseColumns.add(cb);
		}
		this.columnsList.add(cb);
	}

	public void addPrimaryKeyColumn(String columnName) {
		boolean found = false;
		// first search base columns
		Iterator<ColumnBean> iter = baseColumns.iterator();
		while (iter.hasNext()) {
			ColumnBean cb = iter.next();
			if (cb.getName().equals(columnName)) {
				primaryKeyColumns.add(cb);
				iter.remove();
				found = true;
				break;
			}
		}
		if (!found) {
			iter = blobColumns.iterator();
			while (iter.hasNext()) {
				ColumnBean cb = iter.next();
				if (cb.getName().equals(columnName)) {
					primaryKeyColumns.add(cb);
					iter.remove();
					found = true;
					break;
				}
			}
		}
	}

	public boolean hasAnyColumns() {
		return primaryKeyColumns.size() > 0 || baseColumns.size() > 0
				|| blobColumns.size() > 0;
	}

	public boolean hasPrimaryKeyColumns() {
		return primaryKeyColumns.size() > 0;
	}

	public boolean hasBaseColumns() {
		return baseColumns.size() > 0;
	}

	public void setPropertyByGenModel(Map<String, Object> valueMap) {
		valueMap.put("alias", this.alias.length() > 0 ? this.alias + "." : "");
		valueMap.put("objName", this.objName);
		valueMap.put("columnList", this.columnsList);
	}

	public void setPropertyByGenDAO(Map<String, Object> valueMap) {
		valueMap.put("objName", this.objName);
		valueMap.put("enableCountByExample",
				this.countByExampleStatementEnabled);
		valueMap.put("enableDeleteByExample",
				this.deleteByExampleStatementEnabled);
		valueMap.put("enableDeleteByPrimaryKey",
				this.deleteByPrimaryKeyStatementEnabled);
		valueMap.put("enableInsert", this.insertStatementEnabled);
		valueMap.put("enableSelectByExample",
				this.selectByExampleStatementEnabled);
		valueMap.put("enableSelectByPrimaryKey",
				this.selectByPrimaryKeyStatementEnabled);
		valueMap.put("enableUpdateByExample",
				this.updateByExampleStatementEnabled);
		valueMap.put("enableUpdateByPrimaryKey",
				this.updateByPrimaryKeyStatementEnabled);
		valueMap.put("primaryKeyColumns", this.primaryKeyColumns);
	}

	public void setPropertyByGenXML(Map<String, Object> valueMap) {
		valueMap.put("objName", this.objName);
		valueMap.put("tableName", this.tableName);
		valueMap.put("enableCountByExample",
				this.countByExampleStatementEnabled);
		valueMap.put("enableDeleteByExample",
				this.deleteByExampleStatementEnabled);
		valueMap.put("enableDeleteByPrimaryKey",
				this.deleteByPrimaryKeyStatementEnabled);
		valueMap.put("enableInsert", this.insertStatementEnabled);
		valueMap.put("enableSelectByExample",
				this.selectByExampleStatementEnabled);
		valueMap.put("enableSelectByPrimaryKey",
				this.selectByPrimaryKeyStatementEnabled);
		valueMap.put("enableUpdateByExample",
				this.updateByExampleStatementEnabled);
		valueMap.put("enableUpdateByPrimaryKey",
				this.updateByPrimaryKeyStatementEnabled);
		valueMap.put("primaryKeyColumns", this.primaryKeyColumns);
		List<ColumnBean> ll = new ArrayList<ColumnBean>();
		ll.addAll(baseColumns);
		ll.addAll(blobColumns);
		valueMap.put("otherColumns", ll);
		valueMap.put("baseColumns", this.baseColumns);
		valueMap.put("blobColumns", this.blobColumns);
		valueMap.put("columnsList", this.columnsList);// 所有
	}
	
	public void setPropertyByGenService(Map<String, Object> valueMap) {
		valueMap.put("objName", this.objName);
		valueMap.put("tableName", this.tableName);
		valueMap.put("enableCountByExample",
				this.countByExampleStatementEnabled);
		valueMap.put("enableDeleteByExample",
				this.deleteByExampleStatementEnabled);
		valueMap.put("enableDeleteByPrimaryKey",
				this.deleteByPrimaryKeyStatementEnabled);
		valueMap.put("enableInsert", this.insertStatementEnabled);
		valueMap.put("enableSelectByExample",
				this.selectByExampleStatementEnabled);
		valueMap.put("enableSelectByPrimaryKey",
				this.selectByPrimaryKeyStatementEnabled);
		valueMap.put("enableUpdateByExample",
				this.updateByExampleStatementEnabled);
		valueMap.put("enableUpdateByPrimaryKey",
				this.updateByPrimaryKeyStatementEnabled);
		valueMap.put("primaryKeyColumns", this.primaryKeyColumns);
		List<ColumnBean> ll = new ArrayList<ColumnBean>();
		ll.addAll(baseColumns);
		ll.addAll(blobColumns);
		valueMap.put("otherColumns", ll);
		valueMap.put("baseColumns", this.baseColumns);
		valueMap.put("blobColumns", this.blobColumns);
		valueMap.put("columnsList", this.columnsList);// 所有
	}

	// *******************************************
	// *************get set
	// *******************************************
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isInsertStatementEnabled() {
		return insertStatementEnabled;
	}

	public void setInsertStatementEnabled(boolean insertStatementEnabled) {
		this.insertStatementEnabled = insertStatementEnabled;
	}

	public boolean isSelectByPrimaryKeyStatementEnabled() {
		return selectByPrimaryKeyStatementEnabled;
	}

	public void setSelectByPrimaryKeyStatementEnabled(
			boolean selectByPrimaryKeyStatementEnabled) {
		this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
	}

	public boolean isSelectByExampleStatementEnabled() {
		return selectByExampleStatementEnabled;
	}

	public void setSelectByExampleStatementEnabled(
			boolean selectByExampleStatementEnabled) {
		this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
	}

	public boolean isUpdateByPrimaryKeyStatementEnabled() {
		return updateByPrimaryKeyStatementEnabled;
	}

	public void setUpdateByPrimaryKeyStatementEnabled(
			boolean updateByPrimaryKeyStatementEnabled) {
		this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
	}

	public boolean isDeleteByPrimaryKeyStatementEnabled() {
		return deleteByPrimaryKeyStatementEnabled;
	}

	public void setDeleteByPrimaryKeyStatementEnabled(
			boolean deleteByPrimaryKeyStatementEnabled) {
		this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
	}

	public boolean isDeleteByExampleStatementEnabled() {
		return deleteByExampleStatementEnabled;
	}

	public void setDeleteByExampleStatementEnabled(
			boolean deleteByExampleStatementEnabled) {
		this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
	}

	public boolean isCountByExampleStatementEnabled() {
		return countByExampleStatementEnabled;
	}

	public void setCountByExampleStatementEnabled(
			boolean countByExampleStatementEnabled) {
		this.countByExampleStatementEnabled = countByExampleStatementEnabled;
	}

	public boolean isUpdateByExampleStatementEnabled() {
		return updateByExampleStatementEnabled;
	}

	public void setUpdateByExampleStatementEnabled(
			boolean updateByExampleStatementEnabled) {
		this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
	}

	public List<ColumnBean> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}

	public void setPrimaryKeyColumns(List<ColumnBean> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}

	public List<ColumnBean> getBaseColumns() {
		return baseColumns;
	}

	public void setBaseColumns(List<ColumnBean> baseColumns) {
		this.baseColumns = baseColumns;
	}

	public List<ColumnBean> getBlobColumns() {
		return blobColumns;
	}

	public void setBlobColumns(List<ColumnBean> blobColumns) {
		this.blobColumns = blobColumns;
	}

}
