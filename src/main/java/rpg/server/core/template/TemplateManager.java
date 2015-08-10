package rpg.server.core.template;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rpg.server.core.Const;
import rpg.server.util.io.FileUtil;
import rpg.server.util.io.ResourceListener;
import rpg.server.util.io.ResourceManager;

/**
 * 
 */
public class TemplateManager {

	final static Map<String, Template> templates_ui = new HashMap<String, Template>();

	final static Map<String, Template> templates_mail = new HashMap<String, Template>();

	final static Map<String, Template> templates_script = new HashMap<String, Template>();

	private static final String RES = "/template/";
	private static final String RES_MAIL = "/mail/";
	private static final String RES_SCRIPT = "/script/";
	private static final String RES_UI = "/ui/";

	/**
	 * 载入模板数据
	 * 
	 * @param path
	 */
	public final static void load(String path) {
		loadAllMailTemplate(path);
		loadAllScriptTemplate(path);
		loadUITemplate(path);
	}

	/**
	 * 获取UI模板
	 * 
	 * @param id
	 * @return
	 */
	public final static Template getTemplate(String id) {
		return templates_ui.get(id);
	}

	/**
	 * 获取邮件模板
	 * 
	 * @param id
	 * @return
	 */
	public final static Template getMailTemplate(String id) {
		return templates_mail.get(id);
	}

	/**
	 * 获取脚本模板
	 * 
	 * @param id
	 * @return
	 */
	public final static Template getScriptTemplate(String id) {
		return templates_script.get(id);
	}

	/**
	 * 保證輸出為合法的xml字符串
	 * 
	 * @param xml
	 *            String
	 * @return String
	 */
	public static String trim(String xml) {
		char[] data = xml.toCharArray();
		StringBuffer sb = new StringBuffer(data.length);
		for (int i = 0; i < data.length; i++) {
			// TODO
			// if (XMLChar.isContent(data[i])) {
			// if (data[i] == '$') {
			// sb.append("$$");
			// } else {
			// sb.append(data[i]);
			// }
			// } // 特殊字符的處理
			if (data[i] == '&') {
				sb.append("&amp;");
			} else if (data[i] == '<') {
				sb.append("&lt;");
			} else if (data[i] == '\n') {
				sb.append("<br />");
			}
		}
		return sb.toString();
	}

	// 载入和监听邮件模板
	private static void loadAllMailTemplate(String path) {
		final File dir = new File(path + RES + RES_MAIL);
		ResourceManager.getInstance().registerResourceListener(
				new ResourceListener() {

					public File listenedFile() {
						return dir;
					}

					public void onResourceChange(File file) {
						try {
							loadTemplateMail(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public String toString() {
						return "Template-mail";
					}
				}, true);

		if (Const.hasCharset()) {
			final File dir1 = new File(path + Const.LOCAL + Const.CHARSET + "/"
					+ RES + RES_MAIL);
			ResourceListener rl = new ResourceListener() {

				public File listenedFile() {
					return dir1;
				}

				public void onResourceChange(File file) {
					try {
						loadTemplateMail(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public String toString() {
					return "Template-mail-local";
				}
			};
			ResourceManager.getInstance().registerResourceListener(rl, true);
		}
	}

	// 从单个文件载入邮件模板
	private static void loadTemplateMail(File file) throws IOException {
		String name = file.getName();
		Template t = templates_mail.get(name);
		if (t == null) {
			t = new Template(FileUtil.readFile(file));
			templates_mail.put(file.getName(), t);
		} else {
			t.load(FileUtil.readFile(file));
		}
	}

	// 载入和监听脚本模板
	private static void loadAllScriptTemplate(String path) {
		final File dir = new File(path + RES + RES_SCRIPT);
		ResourceManager.getInstance().registerResourceListener(
				new ResourceListener() {

					public File listenedFile() {
						return dir;
					}

					public void onResourceChange(File file) {
						try {
							loadTemplateScript(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public String toString() {
						return "Template-script";
					}
				}, true);

		if (Const.hasCharset()) {
			final File dir1 = new File(path + Const.LOCAL + Const.CHARSET + "/"
					+ RES + RES_SCRIPT);
			ResourceListener rl = new ResourceListener() {

				public File listenedFile() {
					return dir1;
				}

				public void onResourceChange(File file) {
					try {
						loadTemplateScript(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public String toString() {
					return "Template-script-local";
				}
			};
			ResourceManager.getInstance().registerResourceListener(rl, true);
		}
	}

	// 从单个文件载入脚本模板
	private static void loadTemplateScript(File file) throws IOException {
		String name = file.getName();
		Template t = templates_script.get(name);
		if (t == null) {
			t = new Template(FileUtil.readFile(file));
			templates_script.put(file.getName(), t);
		} else {
			t.load(FileUtil.readFile(file));
		}
	}

	// 载入和监听phone版ui模板
	private static void loadUITemplate(String path) {
		final File dir = new File(path + RES + RES_UI);
		ResourceManager.getInstance().registerResourceListener(
				new ResourceListener() {

					public File listenedFile() {
						return dir;
					}

					public void onResourceChange(File file) {
						try {
							loadTemplateUI(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public String toString() {
						return "Template-ui-phone";
					}
				}, true);

		if (Const.hasCharset()) {
			final File dir1 = new File(path + Const.LOCAL + Const.CHARSET + "/"
					+ RES + RES_UI);
			ResourceListener rl = new ResourceListener() {

				public File listenedFile() {
					return dir1;
				}

				public void onResourceChange(File file) {
					try {
						loadTemplateUI(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public String toString() {
					return "Template-ui-phone-local";
				}
			};
			ResourceManager.getInstance().registerResourceListener(rl, true);
		}
	}

	// 从单个文件载入phone版ui模板
	private static void loadTemplateUI(File file) throws IOException {
		String name = file.getName();
		Template t = templates_ui.get(name);
		if (t == null) {
			t = new Template(FileUtil.readFile(file));
			templates_ui.put(file.getName(), t);
		} else {
			t.load(FileUtil.readFile(file));
		}
	}

}
