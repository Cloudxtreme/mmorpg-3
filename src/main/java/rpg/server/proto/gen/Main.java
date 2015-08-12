package rpg.server.proto.gen;

public class Main {
	public static void main(String[] args) throws Exception {
		String protoc = "E:\\protocol\\protoc\\protoc.exe";
		String protoFilePath = "E:\\protocol\\protofile\\";
		ParseProto parse = new ParseProto(protoc, protoFilePath);
		// parse.genProtoDesc();
		// parse.genProtoClass("E:\\workspace\\netty-demo\\src\\main\\java\\");
		parse.processDesc();
		// 生成desc文件
		// String desc = parse.genProtoDesc("options.proto");
		// parse.genClassCommand("options.proto");
		// desc = parse.genProtoDesc("Account.proto");
		// parse.genClassCommand("Account.proto");
		// Map<String, Object> msgInfo = parse.getMsgInfo(desc);
		// System.out.println("\n协议信息：");
		// for (Entry<String, Object> e : msgInfo.entrySet()) {
		// System.out.println(e.getKey() + "->" + e.getValue());
		// }
		// desc = parse.genProtoDesc("Login.proto");
		// parse.genClassCommand("Login.proto");
		//
		// msgInfo = parse.getMsgInfo(desc);
		// System.out.println("\n协议信息：");
		// for (Entry<String, Object> e : msgInfo.entrySet()) {
		// System.out.println(e.getKey() + "->" + e.getValue());
		// }
	}
}
