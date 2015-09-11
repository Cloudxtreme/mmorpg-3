package rpg.server.util.gen.mybatis;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import rpg.server.util.StringUtil;

public class DatabaseIntrospector {

	/** 数据库的meta data */
	private DatabaseMetaData databaseMetaData;

	/**
	 * Instantiates a new database introspector.
	 *
	 * @param databaseMetaData
	 *            the database meta data
	 * @param javaTypeResolver
	 *            the java type resolver
	 * @param warnings
	 *            the warnings
	 */
	public DatabaseIntrospector(DBGenConfiguration configuration,
			DatabaseMetaData databaseMetaData) {
		this.databaseMetaData = databaseMetaData;
	}

	/**
	 * Calculate primary key.
	 *
	 * @param table
	 *            the table
	 * @param introspectedTable
	 *            the introspected table
	 */
	private void calculatePrimaryKey(TableBean tb) {
		ResultSet rs = null;

		try {
			rs = databaseMetaData.getPrimaryKeys(tb.getCatalog(),
					tb.getSchema(), tb.getTableName());
		} catch (SQLException e) {
			ConnectionFactory.getInstance().closeResultSet(rs);
			return;
		}

		try {
			// keep primary columns in key sequence order
			Map<Short, String> keyColumns = new TreeMap<Short, String>();
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				short keySeq = rs.getShort("KEY_SEQ");
				keyColumns.put(keySeq, columnName);
			}

			for (String columnName : keyColumns.values()) {
				tb.addPrimaryKeyColumn(columnName);
			}
		} catch (SQLException e) {
			// ignore the primary key if there's any error
		} finally {
			ConnectionFactory.getInstance().closeResultSet(rs);
		}
	}

	/**
	 * Returns a List of IntrospectedTable elements that matches the specified
	 * table configuration.
	 *
	 * @param tc
	 *            the tc
	 * @return a list of introspected tables
	 * @throws SQLException
	 *             the SQL exception
	 */
	public List<TableBean> introspectTables(TableConfiguration tc)
			throws SQLException {

		// 从db获取所有列
		Map<ActualTableName, List<ColumnBean>> columns = getColumns(tc);

		if (columns.isEmpty()) {
			return null;
		}
		// 移除忽略生成的列
		removeIgnoredColumns(tc, columns);
		calculateIdentityColumns(tc, columns);

		List<TableBean> tbList = calculateIntrospectedTables(tc, columns);

		// now introspectedTables has all the columns from all the
		// tables in the configuration. Do some validation...

		Iterator<TableBean> iter = tbList.iterator();
		while (iter.hasNext()) {
			TableBean tb = iter.next();
			// 如果没有任何数据列,则移除
			if (!tb.hasAnyColumns()) {
				iter.remove();
			} else if (!tb.hasPrimaryKeyColumns() && !tb.hasBaseColumns()) {// 没有主键或者基本数据列,则移除
				iter.remove();
			}
		}

		return tbList;
	}

	/**
	 * 移除忽略生成的列
	 *
	 * @param tc
	 *            the tc
	 * @param columns
	 *            the columns
	 */
	private void removeIgnoredColumns(TableConfiguration tc,
			Map<ActualTableName, List<ColumnBean>> columns) {
		for (Map.Entry<ActualTableName, List<ColumnBean>> entry : columns
				.entrySet()) {
			Iterator<ColumnBean> tableColumns = (entry.getValue()).iterator();
			while (tableColumns.hasNext()) {
				ColumnBean introspectedColumn = tableColumns.next();
				if (tc.isColumnIgnored(introspectedColumn.getName())) {
					tableColumns.remove();
				}
			}
		}
	}

	/**
	 * Calculate identity columns.
	 *
	 * @param tc
	 *            the tc
	 * @param columns
	 *            the columns
	 */
	private void calculateIdentityColumns(TableConfiguration tc,
			Map<ActualTableName, List<ColumnBean>> columns) {
		GeneratedKey gk = tc.getGeneratedKey();
		if (gk == null) {
			// no generated key, then no identity or sequence columns
			return;
		}

		for (Map.Entry<ActualTableName, List<ColumnBean>> entry : columns
				.entrySet()) {
			for (ColumnBean cb : entry.getValue()) {
				if (isMatchedColumn(cb, gk)) {
					if (gk.isIdentity() || gk.isJdbcStandard()) {
						cb.setIdentity(true);
						cb.setSequenceColumn(false);
					} else {
						cb.setIdentity(false);
						cb.setSequenceColumn(true);
					}
				}
			}
		}
	}

	/**
	 * Checks if is matched column.
	 *
	 * @param introspectedColumn
	 *            the introspected column
	 * @param gk
	 *            the gk
	 * @return true, if is matched column
	 */
	private boolean isMatchedColumn(ColumnBean cb, GeneratedKey gk) {
		return cb.getName().equalsIgnoreCase(gk.getColumn());
	}

	/**
	 * This method returns a Map<ActualTableName, List<ColumnDefinitions>> of
	 * columns returned from the database introspection.
	 *
	 * @param tc
	 *            the tc
	 * @return introspected columns
	 * @throws SQLException
	 *             the SQL exception
	 */
	private Map<ActualTableName, List<ColumnBean>> getColumns(
			TableConfiguration tc) throws SQLException {
		String localCatalog;
		String localSchema;
		String localTableName;

		boolean delimitIdentifiers = tc.isDelimitIdentifiers()
				|| StringUtil.stringContainsSpace(tc.getCatalog())
				|| StringUtil.stringContainsSpace(tc.getSchema())
				|| StringUtil.stringContainsSpace(tc.getTableName());

		if (delimitIdentifiers) {
			localCatalog = tc.getCatalog();
			localSchema = tc.getSchema();
			localTableName = tc.getTableName();
		} else if (databaseMetaData.storesLowerCaseIdentifiers()) {
			localCatalog = tc.getCatalog() == null ? null : tc.getCatalog()
					.toLowerCase();
			localSchema = tc.getSchema() == null ? null : tc.getSchema()
					.toLowerCase();
			localTableName = tc.getTableName() == null ? null : tc
					.getTableName().toLowerCase();
		} else if (databaseMetaData.storesUpperCaseIdentifiers()) {
			localCatalog = tc.getCatalog() == null ? null : tc.getCatalog()
					.toUpperCase();
			localSchema = tc.getSchema() == null ? null : tc.getSchema()
					.toUpperCase();
			localTableName = tc.getTableName() == null ? null : tc
					.getTableName().toUpperCase();
		} else {
			localCatalog = tc.getCatalog();
			localSchema = tc.getSchema();
			localTableName = tc.getTableName();
		}

		Map<ActualTableName, List<ColumnBean>> answer = new HashMap<ActualTableName, List<ColumnBean>>();

		ResultSet rs = databaseMetaData.getColumns(localCatalog, localSchema,
				localTableName, null);
		while (rs.next()) {
			ColumnBean columnBean = new ColumnBean(tc.getAlias(),
					rs.getString("COLUMN_NAME"), rs.getInt("DATA_TYPE"),
					rs.getInt("COLUMN_SIZE"),
					rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable,
					rs.getInt("DECIMAL_DIGITS"), rs.getString("REMARKS"),
					rs.getString("COLUMN_DEF"));
			ActualTableName atn = new ActualTableName(
					rs.getString("TABLE_CAT"), rs.getString("TABLE_SCHEM"),
					rs.getString("TABLE_NAME"));

			List<ColumnBean> columns = answer.get(atn);
			if (columns == null) {
				columns = new ArrayList<ColumnBean>();
				answer.put(atn, columns);
			}

			columns.add(columnBean);
		}
		ConnectionFactory.getInstance().closeResultSet(rs);
		return answer;
	}

	/**
	 * Calculate introspected tables.
	 *
	 * @param tc
	 *            the tc
	 * @param columns
	 *            the columns
	 * @return the list
	 */
	private List<TableBean> calculateIntrospectedTables(TableConfiguration tc,
			Map<ActualTableName, List<ColumnBean>> columns) {
		List<TableBean> answer = new ArrayList<TableBean>();
		for (Map.Entry<ActualTableName, List<ColumnBean>> entry : columns
				.entrySet()) {
			ActualTableName atn = entry.getKey();
			TableBean tb = new TableBean(tc.getCatalog(), tc.getSchema(),
					atn.getTableName(), tc.getDomainObjectName(),
					tc.getAlias(), tc.isInsertStatementEnabled(),
					tc.isSelectByPrimaryKeyStatementEnabled(),
					tc.isSelectByExampleStatementEnabled(),
					tc.isUpdateByPrimaryKeyStatementEnabled(),
					tc.isDeleteByPrimaryKeyStatementEnabled(),
					tc.isDeleteByExampleStatementEnabled(),
					tc.isCountByExampleStatementEnabled(),
					tc.isUpdateByExampleStatementEnabled());
			for (ColumnBean cb : entry.getValue()) {
				tb.addColumn(cb);
			}
			calculatePrimaryKey(tb);
			answer.add(tb);
		}

		return answer;
	}
}
