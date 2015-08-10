package rpg.server.util.io;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * xml资源监听器<br />
 * 服务器提供的另外一种资源监听方式<br />
 * 监听文件夹内所有的xml文件,对xml进行解析，<br />
 * 根据type标识，指派给对应的ResourceListener处理
 * 
 */
public class XmlResourceListener implements ResourceListener {
	/**
	 * 构造函数
	 * 
	 * @param file
	 *            监听的文件夹名
	 */
	public XmlResourceListener(File file) {
		this.file = file;
	}

	private final File file;

	@Override
	public String toString() {
		return file.toString();
	}

	public File listenedFile() {
		return file;
	}

	public void onResourceChange(File file) {
		if (XmlUtils.isXml(file.getName())) {
			try {
				Document d = XmlUtils.load(file);
				Element e = d.getDocumentElement();
				String type = e.getAttribute("type");
				ResourceListener l = ResourceManager.getInstance()
						.getResourceListener(type);
				if (l != null) {
					l.onResourceChange(file);
				}
			} catch (Exception e) {
				// TODO
			}
		}
	}

	public boolean isFileIgnored(File file) {
		return false;
	}

}
