package rpg.server.util.io;

import java.io.File;

/**
 * 一个静态的工具类，用于处理local资源加载时与通用资源冲突的情况<br />
 * 出现冲突时，将通用资源对应文件加入忽略列表，不进行加载
 * 
 */
public class LocalResourceProcessor {
	/**
	 * 处理local资源差异
	 * 
	 * @param resPath
	 *            通用资源路径
	 * @param localPath
	 *            local资源路径
	 */
	public static final void diffResource(String resPath, String localPath) {
		File file = new File(localPath);
		doDiffResource(resPath, file.getAbsolutePath(), file);
	}

	/**
	 * 
	 * @param resPath
	 * @param localPath
	 */
	private static final void doDiffResource(String resPath, String localPath,
			File localFile) {
		if (ResourceManager.getInstance().isFileIgnored(localFile)) {
			return;
		}
		if (localFile.isDirectory()) {
			for (File file : localFile.listFiles()) {
				doDiffResource(resPath, localPath, file);
			}
		} else {
			int index = localFile.getAbsolutePath().lastIndexOf(localPath);
			if (index < 0) {
				return;// sth. wrong happened;
			}
			String res = localFile.getAbsolutePath().substring(
					index + localPath.length());
			File f = new File(resPath + "/" + res);
			if (f.exists()) {
				ResourceManager.getInstance().addIgnoredFile(f);
			}
		}
	}
}
