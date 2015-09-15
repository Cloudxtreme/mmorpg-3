package rpg.server.util.gen.mybatis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.util.StringUtil;
import rpg.server.util.io.XmlUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class DBGenConfig {

	private static final String FLODER = "genTemplate";
	// model
	private static final String MODEL_FTL = "DBModel.ftl";
	private static final String EXAMPLE_MODEL_FTL = "DBExample.ftl";
	// DAO
	private static final String DAO_FTL = "DBDAO.ftl";
	// XML
	private static final String MAPPER_XML_FTL = "DBMapperXML.ftl";

	private static final String SERVICE_FTL = "DBServic.ftl";

	private boolean gen = false;
	/** 输出位置 */
	private String outputPath;

	/** 资源路径 */
	private String resourcePath;
	/** JDBC 配置信息 */
	private JDBCConnectionConfiguration jdbcConfig;
	/** model生成位置 */
	private String modelPackage;
	/** mapping生成位置 */
	private String mappingPackage;
	/** dao生成位置 */
	private String daoPackage;
	/** service生成位置 */
	private String servicePackage;
	/** 需要生成的表配置 */
	private List<TableConfiguration> tableConfigurations = new ArrayList<TableConfiguration>();

	private DBGenConfig() {
	}

	// *******************************************
	// *************parse config
	// *******************************************

	public static DBGenConfig parseConfiguration(File configFile,
			String resourcePath, String outputPath) throws Exception {
		Document doc = XmlUtils.load(configFile);
		return parseConfiguration(doc.getDocumentElement(), resourcePath,
				outputPath);
	}

	public static DBGenConfig parseConfiguration(Element ele,
			String resourcePath, String outputPath) throws Exception {
		DBGenConfig dbgc = new DBGenConfig();
		if (StringUtil.isTrue(XmlUtils.getAttribute(ele, "gen"))) {
			dbgc.gen = true;
			dbgc.resourcePath = resourcePath;
			dbgc.outputPath = outputPath;
			dbgc.parseJdbcConnection(XmlUtils.getChildByName(ele,
					"jdbcConnection"));
			dbgc.parsePackage(ele);
			dbgc.parseTable(ele, dbgc);
			dbgc.introspect();
		}
		return dbgc;
	}

	// /**
	// * 配置文件解析
	// *
	// * @param inputFile
	// * 配置文件
	// * @throws Exception
	// * 解析异常
	// *
	// */
	// public static DBGenConfig parseConfiguration(String resourcePath,
	// String outputPath) throws Exception {
	// DBGenConfig config = new DBGenConfig();
	// config.resourcePath = resourcePath;
	// config.outputPath = outputPath;
	// File f = new File(resourcePath, GEN_CONFIG);
	// Document doc = XmlUtils.load(f);
	// Element rootNode = doc.getDocumentElement();
	// config.parseJdbcConnection(
	// XmlUtils.getChildByName(rootNode, "jdbcConnection"), config);
	// config.parsePackage(rootNode, config);
	// config.parseTable(rootNode, config);
	// return config;
	// }

	/**
	 * 解析JDBC连接节点<br>
	 * 
	 * @param ele
	 *            节点
	 * @param config
	 *            配置
	 */
	private void parseJdbcConnection(Element ele) {
		JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
		jdbcConfig.setConnectionURL(XmlUtils.getChildText(ele, "url"));
		jdbcConfig.setUser(XmlUtils.getChildText(ele, "user"));
		jdbcConfig.setDriverClass(XmlUtils.getChildText(ele, "driverClass"));
		jdbcConfig.setPassword(XmlUtils.getChildText(ele, "password"));
		this.jdbcConfig = jdbcConfig;
	}

	/**
	 * 解析各个文件生成的包路径<br>
	 * 
	 * @param root
	 *            根节点
	 * @param config
	 *            配置文件
	 */
	private void parsePackage(Element root) {
		this.modelPackage = XmlUtils.getChildText(root, "modelPackage");
		this.mappingPackage = XmlUtils.getChildText(root, "mappingPackage");
		this.daoPackage = XmlUtils.getChildText(root, "daoPackage");
		this.servicePackage = XmlUtils.getChildText(root, "servicePackage");
	}

	private void parseTable(Element root, DBGenConfig config) {
		Element[] eles = XmlUtils.getChildrenByName(root, "table");
		for (int i = 0; i < eles.length; i++) {
			config.tableConfigurations.add(TableConfiguration
					.parseTableConfiguration(eles[i]));
		}
	}

	// *******************************************
	// *************introspect
	// *******************************************

	private void introspect() throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection(
				this.jdbcConfig);
		DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(
				this, conn.getMetaData());
		for (TableConfiguration tc : tableConfigurations) {
			List<TableBean> tables = databaseIntrospector.introspectTables(tc);
			if (tables != null) {
				tbList.addAll(tables);
			}
		}
		ConnectionFactory.getInstance().closeConnection(conn);
	}

	private List<TableBean> tbList = new ArrayList<TableBean>();

	// *******************************************
	// *************gen
	// *******************************************

	public void gen() throws Exception {
		if (!gen) {
			return;
		}
		this.genModel();
		this.genDAO();
		this.genMapping();
		this.genService();
	}

	private void genModel() throws Exception {
		// table循环
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		for (TableBean tb : this.tbList) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("modelPackage", modelPackage);
			valueMap.put("mappingPackage", mappingPackage);
			valueMap.put("daoPackage", daoPackage);
			valueMap.put("servicePackage", servicePackage);
			tb.setPropertyByGenModel(valueMap);
			// model
			Template template = cfg.getTemplate(MODEL_FTL);
			File filePath = new File(this.outputPath,
					this.modelPackage.replace(".", File.separator));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File targetFile = new File(filePath, tb.getObjName() + ".java");
			if (targetFile.exists()) {
				targetFile.delete();
			}
			// 合并处理（模板 + 数据模型）
			template.process(valueMap, new OutputStreamWriter(
					new FileOutputStream(targetFile, false)));
			// example
			template = cfg.getTemplate(EXAMPLE_MODEL_FTL);
			targetFile = new File(filePath, tb.getObjName() + "Example.java");
			if (targetFile.exists()) {
				targetFile.delete();
			}
			// 合并处理（模板 + 数据模型）
			template.process(valueMap, new OutputStreamWriter(
					new FileOutputStream(targetFile, false)));
		}
	}

	private void genDAO() throws Exception {
		// table循环
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		for (TableBean tb : this.tbList) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("modelPackage", modelPackage);
			valueMap.put("mappingPackage", mappingPackage);
			valueMap.put("daoPackage", daoPackage);
			valueMap.put("servicePackage", servicePackage);
			tb.setPropertyByGenDAO(valueMap);
			// dao
			Template template = cfg.getTemplate(DAO_FTL);
			File filePath = new File(this.outputPath, this.daoPackage.replace(
					".", File.separator));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File targetFile = new File(filePath, tb.getObjName()
					+ "Mapper.java");
			if (targetFile.exists()) {
				targetFile.delete();
			}
			// 合并处理（模板 + 数据模型）
			template.process(valueMap, new OutputStreamWriter(
					new FileOutputStream(targetFile, false)));
		}
	}

	private void genMapping() throws Exception {
		// table循环
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		for (TableBean tb : this.tbList) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("modelPackage", modelPackage);
			valueMap.put("mappingPackage", mappingPackage);
			valueMap.put("daoPackage", daoPackage);
			valueMap.put("servicePackage", servicePackage);
			tb.setPropertyByGenXML(valueMap);
			// dao
			Template template = cfg.getTemplate(MAPPER_XML_FTL);
			File filePath = new File(this.outputPath,
					this.mappingPackage.replace(".", File.separator));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File targetFile = new File(filePath, tb.getObjName() + "Mapper.xml");
			if (targetFile.exists()) {
				targetFile.delete();
			}
			// 合并处理（模板 + 数据模型）
			template.process(valueMap, new OutputStreamWriter(
					new FileOutputStream(targetFile, false)));
		}
	}

	private void genService() throws Exception {
		// table循环
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		for (TableBean tb : this.tbList) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("modelPackage", modelPackage);
			valueMap.put("mappingPackage", mappingPackage);
			valueMap.put("daoPackage", daoPackage);
			valueMap.put("servicePackage", servicePackage);
			tb.setPropertyByGenService(valueMap);
			// dao
			Template template = cfg.getTemplate(SERVICE_FTL);
			File filePath = new File(this.outputPath,
					this.servicePackage.replace(".", File.separator));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File targetFile = new File(filePath, tb.getObjName()
					+ "Service.java");
			if (targetFile.exists()) {
				targetFile.delete();
			}
			// 合并处理（模板 + 数据模型）
			template.process(valueMap, new OutputStreamWriter(
					new FileOutputStream(targetFile, false)));
		}
	}

	/**
	 * 获取完整表名
	 * 
	 * @param catalog
	 * @param schema
	 * @param ableName
	 * @param separator
	 *            分隔符
	 * 
	 */
	public static String composeFullyQualifiedTableName(String catalog,
			String schema, String tableName, char separator) {
		StringBuilder sb = new StringBuilder();

		if (StringUtil.stringHasValue(catalog)) {
			sb.append(catalog);
			sb.append(separator);
		}

		if (StringUtil.stringHasValue(schema)) {
			sb.append(schema);
			sb.append(separator);
		} else {
			if (sb.length() > 0) {
				sb.append(separator);
			}
		}

		sb.append(tableName);

		return sb.toString();
	}

	// *******************************************
	// *************get set
	// *******************************************
	public JDBCConnectionConfiguration getJdbcConfig() {
		return jdbcConfig;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public String getMappingPackage() {
		return mappingPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public String getServicePackage() {
		return servicePackage;
	}

}
