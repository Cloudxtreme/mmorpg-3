package rpg.server.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * 文件操作工具
 * 
 */
public class FileUtil {
	/**
	 * 读取文本数据
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static final String readFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] data = new byte[is.available()];
		is.read(data);
		is.close();
		return new String(data, "utf-8");
	}

	/**
	 * 将文件内容读成byte数组
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static final byte[] readFileAsStream(File file) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		if (file.exists()) {
			raf.seek(0);
		} else {
			//TODO 记录LOG 文件不存在
		}
		long len = raf.length();
		byte[] bytes = new byte[(int) len];
		raf.readFully(bytes);
		raf.close();
		return bytes;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param file
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws IOException
	 */
	public static final String readFile(File file, String charset)
			throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] data = new byte[is.available()];
		is.read(data);
		is.close();
		return new String(data, charset);
	}

	/**
	 * 
	 * @param file
	 * @param content
	 */
	public static final void writeFile(File file, byte[] content) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
