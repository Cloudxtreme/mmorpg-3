package rpg.server.util.io;

import java.io.File;

/**
 * 资源加载监听器
 * 
 */
public interface ResourceListener {
	/**
	 * 监听的资源文件路径，或者资源文件引用
	 * 
	 * @return
	 */
	File listenedFile();

	/**
	 * 文件改变时触发的加载操作
	 * 
	 * @param file
	 */
	void onResourceChange(File file);
}
