package rpg.server.util.gen.mybatis;

public class MyBatisGenerator {
	public void generate(String resoucePath, String outputPath)
			throws Exception {

		// 读取配置文件
		DBGenConfiguration config = DBGenConfiguration.parseConfiguration(
				resoucePath, outputPath);
		config.introspect();
		config.gen();
	}

	public static void main(String[] args) throws Exception {
		String resoucePath = "E:\\rpg\\server\\resource\\";
		String outputPath = "E:\\rpg\\server\\src\\main\\java";
		new MyBatisGenerator().generate(resoucePath, outputPath);
	}
}
