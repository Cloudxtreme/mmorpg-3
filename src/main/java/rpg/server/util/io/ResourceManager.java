package rpg.server.util.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源加载/更新检测器
 */
public class ResourceManager implements Runnable {

	/** 默认忽略的文件或文件夹 */
	private static final String[] IGNORE_FILE = new String[] { ".svn" };
	/**
	 * 屏蔽更新的文件列表，在有local标识存在的分区，如果local里存在和通用资源相同的文件，需要将通用资源放入屏蔽列表，不检测更新
	 */
	List<File> ignoredFileList = new ArrayList<File>();

	/**
	 * The default delay between every file modification check, set to 60
	 * seconds.
	 */
	static final long DEFAULT_DELAY = 60 * 1000;

	/** 单利模式 */
	private static final ResourceManager instance = new ResourceManager();

	/**
	 * 监听对应关系<br>
	 * key:监听的目录或者文件,value:监听处理器
	 */
	private Map<File, ResourceListener> resources = new LinkedHashMap<File, ResourceListener>();

	/**
	 * 文件修改时间<br>
	 * 根据修改时间来判断文件是否有变动<br>
	 * key:文件 value:修改时间
	 * 
	 */
	private Map<File, Long> lastModif = new HashMap<File, Long>();

	private volatile boolean dirty;

	private Collection<Change> changes = Collections
			.synchronizedCollection(new ArrayList<Change>());

	public static class Change {
		ResourceListener l;

		File f;
	}

	/**
	 * @return the instance
	 */
	public static final ResourceManager getInstance() {
		return instance;
	}

	private ResourceManager() {
	}

	public void tick() {
		if (dirty) {
			synchronized (changes) {
				for (Change l : changes) {
					// TODO 记录LOG
					l.l.onResourceChange(l.f);
				}
				changes.clear();
			}
			dirty = false;
		}
	}

	/**
	 * 添加资源监听器
	 * 
	 * @param listener
	 */
	public void registerResourceListener(ResourceListener listener) {
		registerResourceListener(listener, false);
	}

	/**
	 * 添加资源监听器
	 * 
	 * @param listener
	 *            待添加的监听器
	 * @param loadOnAdded
	 *            添加监听器时，更新资源
	 */
	public void registerResourceListener(ResourceListener listener,
			boolean loadOnAdded) {
		File listened_file = listener.listenedFile();
		resources.put(listened_file, listener);
		if (loadOnAdded) {
			lastModif.put(listened_file, Long.MIN_VALUE);// 兼容不存在的文件夹
			checkChange(listened_file, listener, Long.MIN_VALUE, false);
		} else {
			lastModif
					.put(listened_file,
							getLastModified(listened_file,
									listened_file.lastModified()));
		}
	}

	/**
	 * 
	 * @param file
	 * @param lastModified
	 * @return
	 */
	private static final long getLastModified(File file, long lastModified) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if (getLastModified(f, lastModified) > lastModified) {
					lastModified = f.lastModified();
				}
			}
			return lastModified;
		} else {
			return Math.max(file.lastModified(), lastModified);
		}
	}

	/**
	 * 检测更新
	 * 
	 */
	public void checkChanges() {
		for (Map.Entry<File, ResourceListener> entry : resources.entrySet()) {
			File file = entry.getKey();
			ResourceListener value = entry.getValue();
			checkChange(file, value, lastModif.get(file), true);
		}
	}

	/**
	 * 检测资源变化
	 * 
	 * @param listener
	 *            资源监听器
	 */
	private void checkChange(ResourceListener listener) {
		File listened_file = listener.listenedFile();
		checkChange(listened_file, listener, lastModif.get(listened_file), true);
	}

	/**
	 * 加载资源
	 * 
	 * @param listener
	 *            资源监听器
	 */
	void loadResource(ResourceListener listener) {
		checkChange(listener.listenedFile(), listener, Long.MIN_VALUE, false);
	}

	/**
	 * 加载单个文件
	 * 
	 * @param reliveListener
	 * @param file
	 */
	public boolean updateFile(String listenerID, String file) {
		for (ResourceListener listener : resources.values()) {
			if (listener.toString().equals(listenerID)) {
				File listened_file = listener.listenedFile();
				String path = listened_file.getPath() + "/" + file;
				File f = new File(path);
				if (f.exists()) {
					listener.onResourceChange(f);
					return true;
				} else
					return false;
			}
		}
		return false;
	}

	/**
	 * 更新单个资源
	 * 
	 * @param listernerId
	 * @param force
	 * 
	 */
	public boolean checkChange(String listernerId, boolean force) {
		for (ResourceListener l : resources.values()) {
			if (l.toString().equals(listernerId)) {
				if (force)
					loadResource(l);
				else
					checkChange(l);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param file
	 * @param listener
	 * @param lastModified
	 * @param async
	 *            是否异步更新，是则加入更新列表等待主线程tick，否则直接更新
	 */
	private void checkChange(final File file, final ResourceListener listener,
			long lastModified, boolean async) {
		if (!file.exists()) {
			return;
		}
		if (isFileIgnored(file)) {
			return;
		}
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			if (list.length > 0) {
				Arrays.sort(list, (o1, o2) -> {
					if (o1.isDirectory()) {
						if (o2.isDirectory()) {
							return o1.getName().compareTo(o2.getName());
						} else {
							return 1;
						}
					} else if (o2.isDirectory()) {
						return -1;
					} else {
						return o1.getName().compareTo(o2.getName());
					}
				});
				for (File f : list) {
					checkChange(f, listener, lastModified, async);
				}
			}
		} else {
			long modif = file.lastModified();
			if (modif > lastModified) {

				if (async) {// 修改成从world主线程更新
					changes.add(new Change() {
						{
							this.l = listener;
							this.f = file;
						}
					});
					this.dirty = true;
				} else {
					// Logger.debug(LoggerFunction.LOADING, "Resource Load："
					// + file.getPath());
					listener.onResourceChange(file);
				}

				File listened_file = listener.listenedFile();
				Long last = lastModif.get(listened_file);
				if (last == null || modif > last) {
					lastModif.put(listened_file, modif);
				}
			}
		}
	}

	@Override
	public void run() {
		checkChanges();
	}

	/**
	 * 增加屏蔽加载的文件
	 * 
	 * @param file
	 *            忽略更新的文件
	 */
	public void addIgnoredFile(File file) {
		ignoredFileList.add(file);
	}

	/**
	 * 是否被屏蔽加载<br>
	 * 如果为屏蔽的文件,将不检测其变动
	 * 
	 * @param file
	 *            文件
	 * @return true:不监测文件,false:监测
	 */
	private boolean isFileIgnored(File file) {
		for (String s : IGNORE_FILE) {
			if (s.equals(file.getName())) {// skip igore file name
				return true;
			}
		}
		for (File f : ignoredFileList) {
			if (f.equals(file)) {// skip igore file name
				return true;
			}
		}
		return false;
	}
}
