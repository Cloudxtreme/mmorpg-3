package rpg.server.util.gen.mybatis;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class JavaTypeTranslator {
	private static Map<Integer, String> typeMap;
	static {
		typeMap = new HashMap<Integer, String>();
		typeMap.put(Types.ARRAY, "Object");
		typeMap.put(Types.BIGINT, "Long");
		typeMap.put(Types.BINARY, "byte[]");
		typeMap.put(Types.BIT, "Boolean");
		typeMap.put(Types.BLOB, "byte[]");
		typeMap.put(Types.BOOLEAN, "Boolean");
		typeMap.put(Types.CHAR, "String");
		typeMap.put(Types.CLOB, "String");
		typeMap.put(Types.DATALINK, "Object");
		typeMap.put(Types.DATE, "java.util.Date");
		typeMap.put(Types.DISTINCT, "Object");
		typeMap.put(Types.DOUBLE, "Double");
		typeMap.put(Types.FLOAT, "Double");
		typeMap.put(Types.INTEGER, "Integer");
		typeMap.put(Types.JAVA_OBJECT, "Object");
		typeMap.put(Types.LONGNVARCHAR, "String");
		typeMap.put(Types.LONGVARBINARY, "byte[]");
		typeMap.put(Types.LONGVARCHAR, "String");
		typeMap.put(Types.NCHAR, "String");
		typeMap.put(Types.NCLOB, "String");
		typeMap.put(Types.NVARCHAR, "String");
		typeMap.put(Types.NULL, "Object");
		typeMap.put(Types.OTHER, "Object");
		typeMap.put(Types.REAL, "Float");
		typeMap.put(Types.REF, "Object");
		typeMap.put(Types.SMALLINT, "Short");
		typeMap.put(Types.STRUCT, "Object");
		typeMap.put(Types.TIME, "java.util.Date");
		typeMap.put(Types.TIMESTAMP, "java.util.Date");
		typeMap.put(Types.TINYINT, "Byte");
		typeMap.put(Types.VARBINARY, "byte[]");
		typeMap.put(Types.VARCHAR, "String");
	}

	public static String getJavaTypeName(int jdbcType, int scale, int length) {
		String type = typeMap.get(jdbcType);
		if (type == null) {
			switch (jdbcType) {
			case Types.DECIMAL:
			case Types.NUMERIC:
				if (scale > 0 || length > 18) {
					return "java.math.BigDecimal";
				} else if (length > 9) {
					return "Long";
				} else if (length > 4) {
					return "Integer";
				} else {
					return "Short";
				}
			default:
				return null;
			}
		} else {
			return type;
		}
	}
}
