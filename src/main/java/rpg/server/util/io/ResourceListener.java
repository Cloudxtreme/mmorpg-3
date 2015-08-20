package rpg.server.util.io;

import java.io.File;

/**
 * 资源加载监听器
 * 
 */
public interface ResourceListener {
	File listenedFile();

	/**
	 * 文件改变时触发的加载操作
	 * 
	 * @param file
	 */
	void onResourceChange(File file);
}
