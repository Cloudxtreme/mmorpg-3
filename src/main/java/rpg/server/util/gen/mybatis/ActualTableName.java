package rpg.server.util.gen.mybatis;

public class ActualTableName {
	private String tableName;
	private String catalog;
	private String schema;
	private String fullName;

	public ActualTableName(String catalog, String schema, String tableName) {
		this.catalog = catalog;
		this.schema = schema;
		this.tableName = tableName;
		fullName = DBGenConfig.composeFullyQualifiedTableName(catalog,
				schema, tableName, '.');
	}

	public String getCatalog() {
		return catalog;
	}

	public String getSchema() {
		return schema;
	}

	public String getTableName() {
		return tableName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ActualTableName)) {
			return false;
		}

		return obj.toString().equals(this.toString());
	}

	@Override
	public int hashCode() {
		return fullName.hashCode();
	}

	@Override
	public String toString() {
		return fullName;
	}
}
