package rpg.server.proto.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import rpg.server.util.io.FileExtFilter;

import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.DescriptorProtos.MessageOptions;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ParseProto {
	/** protoc.exe */
	private final String PROTOC_DIR;
	/** 协议文件位置 */
	private final String PROTO_FILE_DIR;

	private List<MessageInfo> messageInfos = new ArrayList<MessageInfo>();

	/**
	 * 构造器
	 * 
	 * @param protocPath
	 *            protoc.exe路径
	 * @param protoFilePath
	 *            .proto协议文件目录
	 */
	public ParseProto(String protocPath, String protoFilePath) {
		this.PROTOC_DIR = protocPath;
		this.PROTO_FILE_DIR = protoFilePath;
	}

	/**
	 * 生成desc描述文件<br>
	 * 
	 * @throws Exception
	 *             生成失败
	 */
	public void genProtoDesc() throws Exception {
		File dir = new File(this.PROTO_FILE_DIR);
		File[] protoFiles = dir.listFiles(new FileExtFilter(".proto"));
		for (File protoFile : protoFiles) {
			this.genProtoDesc(protoFile);
		}
	}

	/**
	 * 生成proto文件描述信息desc文件
	 * 
	 * @param protoFile
	 *            协议文件
	 * @throws Exception
	 *             生成失败
	 */
	private void genProtoDesc(File protoFile) throws Exception {
		// 目标文件名
		String descFileDir = this.PROTO_FILE_DIR
				+ protoFile.getName().replaceAll(".proto", ".desc");

		// 命令：protoc -I=$SRC_DIR descriptor_set_out=$DST_DIR/***.desc
		// $SRC_DIR/***.proto
		StringBuffer cmd = new StringBuffer();
		cmd.append(PROTOC_DIR);
		cmd.append(" -I=").append(this.PROTO_FILE_DIR);
		cmd.append(" --descriptor_set_out=").append(descFileDir);
		cmd.append(" ").append(protoFile.getAbsolutePath());
		String failMsg = "gen desc file error.";
		executeCommand(cmd.toString(), failMsg);
	}

	public void genProtoClass(String outputDir) throws Exception {
		File dir = new File(this.PROTO_FILE_DIR);
		File[] protoFiles = dir.listFiles(new FileExtFilter(".proto"));
		for (File protoFile : protoFiles) {
			this.genProtoClass(protoFile, outputDir);
		}
	}

	private void genProtoClass(File protoFile, String outputDir)
			throws Exception {
		// protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/addressbook.proto
		StringBuffer cmd = new StringBuffer();
		cmd.append(PROTOC_DIR);// protoc.exe目录
		cmd.append(" -I=").append(this.PROTO_FILE_DIR);// 协议文件目录
		cmd.append(" --java_out=").append(outputDir);// 生成的java文件存放目录
		cmd.append(" ").append(protoFile.getAbsolutePath());// 协议文件
		this.executeCommand(cmd.toString(), "gen class error.");
	}

	public void processDesc() throws Exception {
		File dir = new File(this.PROTO_FILE_DIR);
		File[] protoFiles = dir.listFiles(new FileExtFilter(".desc"));
		for (File protoFile : protoFiles) {
			this.getMsgInfo(protoFile);
		}

		Configuration cfg = new Configuration();
		TemplateLoader loader = new ClassTemplateLoader(this.getClass(),
				"\\com\\hu\\netty\\proto");
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("messageInfos", messageInfos);
		valueMap.put("packageName", "com.hu.netty.proto");
		Template template = cfg.getTemplate("MsgUtil.ftl");
		File targetFile = new File(
				"E:\\workspace\\netty-demo\\src\\main\\java\\com\\hu\\netty\\proto",
				"MsgUtil.java");
		if (targetFile.exists()) {
			targetFile.delete();
		}
		// 合并处理（模板 + 数据模型）
		template.process(valueMap, new OutputStreamWriter(new FileOutputStream(
				targetFile, false)));
	}

	/**
	 * 根据proto文件获取其中的message信息
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getMsgInfo(File descFile) throws Exception {
		// 根据得到的desc文件
		FileDescriptorSet fdSet = FileDescriptorSet
				.parseFrom(new FileInputStream(descFile));

		for (FileDescriptorProto fdp : fdSet.getFileList()) {
			String className = fdp.getName().replaceAll(".proto", "");
			// 遍历获取各message信息
			for (DescriptorProto dp : fdp.getMessageTypeList()) {
				MessageOptions options = dp.getOptions();
				if (options != null) {
					String name = dp.getName(); // message名称
					Integer id = null;
					UnknownFieldSet uf = options.getUnknownFields();
					for (Map.Entry<Integer, UnknownFieldSet.Field> entry : uf
							.asMap().entrySet()) {
						UnknownFieldSet.Field val = entry.getValue();
						id = val.getVarintList().get(0).intValue();
					}

					// 如果存在msgId则记录结果
					if (id != null) {
						this.messageInfos.add(new MessageInfo(id, name,
								className));
					}
				}
			}
		}
	}

	/**
	 * 执行cmd控制台命令
	 * 
	 * @param cmd
	 *            完整命令语句
	 * @param failMsg
	 *            失败时提示语句
	 * @throws Exception
	 *             执行失败
	 */
	private void executeCommand(String cmd, String failMsg) throws Exception {
		// 执行系统命令
		System.out.println("Execute Command:\n" + cmd);
		Runtime run = Runtime.getRuntime();
		Process p = run.exec(cmd);
		// 如果不正常终止，则生成desc文件失败
		if (p.waitFor() != 0) {
			if (p.exitValue() == 1) {
				System.err.println(failMsg);

				// 打印输出的错误信息
				BufferedReader in = new BufferedReader(new InputStreamReader(
						p.getErrorStream()));
				String line = "";
				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
				System.out.println();
			}
		}
	}
}
