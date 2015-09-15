package rpg.server.util.gen.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.MsgHandler;
import rpg.server.core.action.ActionHandler;
import rpg.server.core.action.ActionType;
import rpg.server.core.condition.ConditionHandler;
import rpg.server.core.condition.ConditionType;
import rpg.server.core.module.IAgent;
import rpg.server.util.ClassUtil;
import rpg.server.util.StringUtil;
import rpg.server.util.io.XmlUtils;

import com.google.protobuf.GeneratedMessage;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class AgentGenConfig {
	private static final String FLODER = "genTemplate";
	private static final String PACKAGE = "rpg.server";
	private static final String UTIL_FTL = "AgentUtil.ftl";
	private static final String MSG_DISPATCHER_FTL = "AgentMsgDispatcher.ftl";
	private static final String ACTION_DISPATCHER_FTL = "AgentActionDispatcher.ftl";
	private static final String CONDITION_DISPATCHER_FTL = "AgentConditionDispatcher.ftl";
	private boolean gen = false;
	private String resourcePath;
	private String outputPackage;
	private String outputPath;

	private List<AgentInfo> agentList = new ArrayList<AgentInfo>();
	private List<AgentMsgInfo> msgHandlerList = new ArrayList<AgentMsgInfo>();
	private List<AgentActionInfo> actionHandlerList = new ArrayList<AgentActionInfo>();
	private List<AgentConditionInfo> conditionHandlerList = new ArrayList<AgentConditionInfo>();

	private AgentGenConfig() {

	}

	public static AgentGenConfig parseConfiguration(File configFile,
			String resourcePath, String outputPath) throws Exception {
		Document doc = XmlUtils.load(configFile);
		return parseConfiguration(doc.getDocumentElement(), resourcePath,
				outputPath);
	}

	public static AgentGenConfig parseConfiguration(Element ele,
			String resourcePath, String outputPath) throws Exception {
		AgentGenConfig agc = new AgentGenConfig();
		if (StringUtil.isTrue(XmlUtils.getAttribute(ele, "gen"))) {
			agc.gen = true;
			agc.resourcePath = resourcePath;
			agc.outputPackage = XmlUtils.getChildText(ele, "outputPackage");
			agc.outputPath = outputPath;
			agc.introspect();
		}
		return agc;
	}

	public void gen() throws Exception {
		if (!gen) {
			return;
		}
		this.genUtil();
		this.genMsgDispacher();
		this.genActionDispacher();
		this.genConditionDispacher();
	}

	private void genUtil() throws Exception {
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("agentpackage", outputPackage);
		valueMap.put("agentList", agentList);
		Template template = cfg.getTemplate(UTIL_FTL);
		File filePath = new File(this.outputPath, this.outputPackage.replace(
				".", File.separator));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File targetFile = new File(filePath, "AgentUtil.java");
		if (targetFile.exists()) {
			targetFile.delete();
		}
		// 合并处理（模板 + 数据模型）
		template.process(valueMap, new OutputStreamWriter(new FileOutputStream(
				targetFile, false)));
	}

	private void genMsgDispacher() throws Exception {
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("agentpackage", outputPackage);
		valueMap.put("agentList", agentList);
		valueMap.put("msgHandlerList", msgHandlerList);
		Template template = cfg.getTemplate(MSG_DISPATCHER_FTL);
		File filePath = new File(this.outputPath, this.outputPackage.replace(
				".", File.separator));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File targetFile = new File(filePath, "AgentMsgDispatcher.java");
		if (targetFile.exists()) {
			targetFile.delete();
		}
		// 合并处理（模板 + 数据模型）
		template.process(valueMap, new OutputStreamWriter(new FileOutputStream(
				targetFile, false)));
	}

	private void genActionDispacher() throws Exception {
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("agentpackage", outputPackage);
		valueMap.put("agentList", agentList);
		valueMap.put("actionHandlerList", actionHandlerList);
		Template template = cfg.getTemplate(ACTION_DISPATCHER_FTL);
		File filePath = new File(this.outputPath, this.outputPackage.replace(
				".", File.separator));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File targetFile = new File(filePath, "AgentActionDispatcher.java");
		if (targetFile.exists()) {
			targetFile.delete();
		}
		// 合并处理（模板 + 数据模型）
		template.process(valueMap, new OutputStreamWriter(new FileOutputStream(
				targetFile, false)));
	}

	private void genConditionDispacher() throws Exception {
		Configuration cfg = new Configuration();
		TemplateLoader loader = new FileTemplateLoader(new File(
				this.resourcePath, FLODER));
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("agentpackage", outputPackage);
		valueMap.put("agentList", agentList);
		valueMap.put("conditionHandlerList", conditionHandlerList);
		Template template = cfg.getTemplate(CONDITION_DISPATCHER_FTL);
		File filePath = new File(this.outputPath, this.outputPackage.replace(
				".", File.separator));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File targetFile = new File(filePath, "AgentConditionDispatcher.java");
		if (targetFile.exists()) {
			targetFile.delete();
		}
		// 合并处理（模板 + 数据模型）
		template.process(valueMap, new OutputStreamWriter(new FileOutputStream(
				targetFile, false)));
	}

	public void introspect() throws Exception {
		// 获取所有class
		List<Class<?>> classes = ClassUtil.getClasses(PACKAGE);
		for (Class<?> cla : classes) {
			// 如果未实现接口或者是接口本身则忽略掉
			if (!IAgent.class.isAssignableFrom(cla)) {
				continue;
			}
			if (IAgent.class.equals(cla)) {
				continue;
			}
			agentList.add(new AgentInfo(cla.getPackage().getName(), cla
					.getSimpleName()));
			Method[] methodArray = cla.getMethods();
			for (Method m : methodArray) {
				MsgHandler msgHandler = m.getAnnotation(MsgHandler.class);
				if (msgHandler != null) {
					introspectMsgHandler(cla, m, msgHandler);
				}

				ActionHandler actionHandler = m
						.getAnnotation(ActionHandler.class);
				if (actionHandler != null) {
					introspectActionHandler(cla, m, actionHandler);
				}
				ConditionHandler conditionHandler = m
						.getAnnotation(ConditionHandler.class);
				if (conditionHandler != null) {
					introspectConditionHandler(cla, m, conditionHandler);
				}
			}
		}
	}

	private void introspectMsgHandler(Class<?> cla, Method m,
			MsgHandler msgHandler) {
		Class<? extends GeneratedMessage> gmCla = msgHandler.value();
		msgHandlerList.add(new AgentMsgInfo(gmCla.getCanonicalName(), gmCla
				.getSimpleName(), cla.getSimpleName(), m.getName()));
	}

	private void introspectActionHandler(Class<?> cla, Method m,
			ActionHandler actionHandler) {
		ActionType at = actionHandler.value();
		actionHandlerList.add(new AgentActionInfo(at.name(), cla
				.getSimpleName(), m.getName()));
	}

	private void introspectConditionHandler(Class<?> cla, Method m,
			ConditionHandler conditionHandler) {
		ConditionType ct = conditionHandler.value();
		conditionHandlerList.add(new AgentConditionInfo(ct.name(), cla
				.getSimpleName(), m.getName()));
	}

}
