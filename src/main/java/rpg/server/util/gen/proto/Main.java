package rpg.server.util.gen.proto;

public class Main {
	public static void main(String[] args) throws Exception {
		String protoc = "E:\\rpg\\server\\resource\\protocol\\protoc\\protoc.exe";
		String protoFilePath = "E:\\rpg\\server\\resource\\protocol\\protofile\\";
		String outputPath = "E:\\rpg\\server\\src\\main\\java\\";
		String outputPackage = "rpg.server.gen.proto";
		String templatePath = "E:\\rpg\\server\\resource\\genTemplate\\";
		ParseProto parse = new ParseProto(protoc, protoFilePath, outputPath,
				outputPackage, templatePath);
		parse.genProtoDesc();
		parse.processDesc();
		parse.genProtoClass();
	}
}
