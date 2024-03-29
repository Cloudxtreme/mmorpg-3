package rpg.server.util;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具
 */
public class StringUtil extends StringUtils {
	public static boolean isTrue(String s) {
		return "true".equalsIgnoreCase(s);
	}

	public static boolean stringHasValue(String s) {
		return !isEmpty(s);
	}

	public static boolean stringContainsSpace(String s) {
		return s != null && s.indexOf(' ') != -1;
	}

	/**
	 * Gets the camel case string.
	 *
	 * @param inputString
	 *            the input string
	 * @param firstCharacterUppercase
	 *            the first character uppercase
	 * @return the camel case string
	 */
	public static String getCamelCaseString(String inputString,
			boolean firstCharacterUppercase) {
		StringBuilder sb = new StringBuilder();

		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
			case '/':
			case '&':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;

			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}

		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		return sb.toString();
	}

	/**
	 * Converts a line of text into an array of lower case words. Words are
	 * delimited by the following characters: , .\r\n:/\+
	 * <p>
	 * In the future, this method should be changed to use a
	 * BreakIterator.wordInstance(). That class offers much more fexibility.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken().toLowerCase();
		}
		return words;
	}

	/**
	 * Converts a line of text into an array of lower case words. Words are
	 * delimited by the following characters: , .\r\n:/\+
	 * <p>
	 * In the future, this method should be changed to use a
	 * BreakIterator.wordInstance(). That class offers much more fexibility.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toStringArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, ",\r\n");
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		return words;
	}

	/**
	 * 
	 * @param text
	 * @param delimiter
	 * @return
	 */
	public static final String[] split(String text, String delimiter) {
		StringTokenizer tokens = new StringTokenizer(text, delimiter);
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		return words;
	}

	public static final long[] splitLong(String text, String delimiter) {
		StringTokenizer tokens = new StringTokenizer(text, delimiter);
		long[] words = new long[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			try {
				words[i] = Long.parseLong(tokens.nextToken());
			} catch (Exception e) {
				words[i] = 0;
			}
		}
		return words;
	}

	public static final int[] splitInt(String text, String delimiter) {
		StringTokenizer tokens = new StringTokenizer(text, delimiter);
		int[] words = new int[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			try {
				words[i] = Integer.parseInt(tokens.nextToken());
			} catch (Exception e) {
				words[i] = 0;
			}
		}
		return words;
	}

	public static final byte[] splitByte(String text, String delimiter) {
		StringTokenizer tokens = new StringTokenizer(text, delimiter);
		byte[] words = new byte[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			try {
				words[i] = Byte.parseByte(tokens.nextToken());
			} catch (Exception e) {
				words[i] = 0;
			}
		}
		return words;
	}

	public static final float[] splitFloat(String text, String delimiter) {
		StringTokenizer tokens = new StringTokenizer(text, delimiter);
		float[] words = new float[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			try {
				words[i] = Float.parseFloat(tokens.nextToken());
			} catch (Exception e) {
				words[i] = 0;
			}
		}
		return words;
	}

	/**
	 * * Converts a line of text into an array of lower case words. Words are
	 * delimited by the following characters: , .\r\n:/\+
	 * <p>
	 * In the future, this method should be changed to use a
	 * BreakIterator.wordInstance(). That class offers much more fexibility.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @param token
	 *            String
	 * @return String[]broken up into an array of words.
	 */
	public static final String[] toStringArray(String text, String token) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, token);
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		return words;
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String[] splitOnWhitespace(String source) {
		int pos = -1;
		LinkedList<String> list = new LinkedList<String>();
		int max = source.length();
		for (int i = 0; i < max; i++) {
			char c = source.charAt(i);
			if (Character.isWhitespace(c)) {
				if (i - pos > 1) {
					list.add(source.substring(pos + 1, i));
				}
				pos = i;
			}
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Replayer str
	 * 
	 * @param str
	 * @param key
	 * @param replacement
	 * @return
	 */
	public static final String replaceAll(String str, String key,
			String replacement) {
		if (str != null && key != null && replacement != null
				&& !str.equals("") && !key.equals("")) {
			StringBuilder strbuf = new StringBuilder();
			int begin = 0;
			int slen = str.length();
			int npos = 0;
			int klen = key.length();
			for (; begin < slen && (npos = str.indexOf(key, begin)) >= begin; begin = npos
					+ klen) {
				strbuf.append(str.substring(begin, npos)).append(replacement);
			}

			if (begin == 0) {
				return str;
			}
			if (begin < slen) {
				strbuf.append(str.substring(begin));
			}
			return strbuf.toString();
		} else {
			return str;
		}
	}

	/**
	 * 切割字符串
	 * 
	 * @param str
	 * @param count
	 * @return
	 */
	public static final String subString(String str, int count) {
		if (str == null) {
			return "";
		}
		if (str.length() <= count) {
			return str;
		}
		return new StringBuilder(str.substring(0, count)).append("...")
				.toString();
	}

	/**
	 * 把几个字符串连接起来
	 * 
	 * @param delimiter
	 * @param first
	 * @param others
	 * @return
	 */
	public static final String join(String delimiter, String first,
			String... strings) {
		StringBuilder sb = new StringBuilder(first);
		for (String s : strings) {
			sb.append(delimiter).append(s);
		}
		return sb.toString();
	}

	/**
	 * 测试指定名称中是否包含非法字符。
	 * 
	 * @param name
	 * @return
	 */
	public static boolean containsForbidChars(String name) {
		String forbidChars = "/#@\\+|";
		for (int i = 0; i < forbidChars.length(); i++) {
			if (name.indexOf(forbidChars.charAt(i)) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取以|分割的字符串列表
	 * 
	 * @param str
	 *            原字符串，A|B
	 * @return 列表[A,B]
	 */
	public static List<String> getParams(String str) {
		return getParams(str, "\\|");
	}

	public static List<String> getParams(String str, String split) {
		if (str == null || str.equals("")) {
			return null;
		}
		String[] tmpStrs = str.split(split);// 拆分命令及参数
		List<String> strs = new ArrayList<String>();
		for (String s : tmpStrs) {
			if (s.length() > 0) {
				strs.add(s);
			}
		}
		return strs;
	}

	/**
	 * 将数组转为字符串
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> String toString(T[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}
		return sb.toString();
	}

	public static String toString(int[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}
		return sb.toString();
	}

	public static String toString(int[] array, String delimiter) {
		StringBuffer sb = new StringBuffer();
		if (array.length > 1) {
			for (int i = 0; i < array.length - 1; i++) {
				sb.append(array[i]).append(delimiter);
			}
			sb.append(array[array.length - 1]);
		} else if (array.length == 1) {
			sb.append(array[0]);
		}
		return sb.toString();
	}

	public static String toString(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}
		return sb.toString();
	}

	public static String toString(float[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}
		return sb.toString();
	}

	public static String toString(boolean[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}
		return sb.toString();
	}

	/**
	 * 字符串拼接
	 * 
	 * @param object
	 * @return
	 */
	public static String assemble(Object... object) {
		return assemble0('|', object);
	}

	/**
	 * 字符串拼接
	 * 
	 * @param object
	 * @return
	 */
	public static String assembleString(String sep, Object... object) {
		if (object == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Object obj : object) {
			sb.append(obj == null ? null : obj.toString());
			sb.append(sep);
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 字符串拼接
	 * 
	 * @param object
	 * @return
	 */
	public static String assemble0(char sep, Object... object) {
		if (object == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Object obj : object) {
			if (obj == null)
				continue;
			sb.append(obj.toString());
			sb.append(sep);
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String assemble1(char sep, Object object) {
		if (object == null)
			return null;
		if (object.getClass().isArray()) {
			StringBuilder sb = new StringBuilder();
			int len = Array.getLength(object);
			for (int i = 0; i < len; i++) {
				Object obj = Array.get(object, i);
				if (obj == null)
					continue;
				sb.append(obj.toString());
				sb.append(sep);
			}
			if (sb.length() > 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();
		} else {
			return object.toString();
		}

	}

	/**
	 * 是否含有指定字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean contains(String[] strs, String str) {
		boolean result = false;
		for (String s : strs) {
			if (str.equals(s)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * 检查指定的字符串中是有效的Mysql支持的UTF-8编码,该检测直接比较每个code point是否在0X0000~0XFFFF中(Mysql
	 * utf8能够有效支持的UNICODE编码范围0X000~0XFFFF)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidMySqlUTF8Fast(String str) {
		final int length = str.length();
		for (int i = 0; i < length; i++) {
			int c = str.codePointAt(i);
			if (c < 0X0000 || c > 0Xffff) {
				return false;
			}
		}
		return true;
	}

	public static boolean hasExcludeChar(String str) {
		if (str != null) {
			char[] chs = str.toCharArray();
			for (int i = 0; i < chs.length; i++) {

				if (Character.getType(chs[i]) == Character.PRIVATE_USE) {

					return true;
				}

			}
		}
		return false;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 比较两个版本号
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int VersionCompare(String s1, String s2) {
		if (s1 == null && s2 == null)
			return 0;
		else if (s1 == null)
			return -1;
		else if (s2 == null)
			return 1;

		String[] arr1 = s1.split("[^a-zA-Z0-9]+"), arr2 = s2
				.split("[^a-zA-Z0-9]+");

		int i1, i2, i3;
		for (int ii = 0, max = Math.min(arr1.length, arr2.length); ii <= max; ii++) {
			if (ii == arr1.length)
				return ii == arr2.length ? 0 : -1;
			else if (ii == arr2.length)
				return 1;

			try {
				i1 = Integer.parseInt(arr1[ii]);
			} catch (Exception x) {
				i1 = Integer.MAX_VALUE;
			}

			try {
				i2 = Integer.parseInt(arr2[ii]);
			} catch (Exception x) {
				i2 = Integer.MAX_VALUE;
			}

			if (i1 != i2) {
				return i1 - i2;
			}

			i3 = arr1[ii].compareTo(arr2[ii]);

			if (i3 != 0)
				return i3;
		}

		return 0;
	}

	/**
	 * MD5加密
	 * 
	 * @param s
	 *            被加密的字符串
	 * @return 加密后的字符串
	 */
	public static String md5(String s) throws Exception {
		if (s == null)
			s = "";
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] strTemp = s.getBytes("UTF-8");
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
}
