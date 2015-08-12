package rpg.server.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rpg.server.core.Const;
import rpg.server.util.io.ResourceListener;
import rpg.server.util.io.XmlUtils;

/**
 * 关键字过滤器： 暂时未分离出loader,先测试
 * 
 * 采用AC算法过滤关键词 800个关键词，占用内存1M左右 过滤速度：4MB/s
 * 
 * 
 */
public class DirtFilter implements ResourceListener {

	public static final char SUBSTITUTE_CHAR = '*';
	private static final char[] IGNORE_CHARS = { '　', ' ', '*', '-' };
	private static final char[] ILLEGAL_CHARS = { ' ', '　', '<', '>', '_' };

	public static final String DATA_PATH = "/dirtyword/";
	public static final String DIRTY_WORD = "dirtyword.xml";
	public static final String DIRTY_NAME = "dirtyname.xml";

	private volatile IKeyWordsFilter filter;
	private volatile IKeyWordsFilter namefilter;

	static DirtFilter instance = new DirtFilter();

	public static DirtFilter getInstance() {
		return instance;
	}

	public DirtFilter() {
		this.filter = new KeyWordsACFilter(IGNORE_CHARS, SUBSTITUTE_CHAR);
		this.namefilter = new KeyWordsACFilter(IGNORE_CHARS, SUBSTITUTE_CHAR);

	}

	public void load(String path) {
		file = new File(path + DATA_PATH);
		wordFile = new File(path + DATA_PATH + DIRTY_WORD);
		nameFile = new File(path + DATA_PATH + DIRTY_NAME);

		try {
			reload();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void reload() throws Exception {

		Document d = XmlUtils.load(wordFile);
		Element e = d.getDocumentElement();
		String[] dirtyword = XmlUtils.getChildrenText(e, "word");

		d = XmlUtils.load(nameFile);
		e = d.getDocumentElement();
		String[] dirtyname = XmlUtils.getChildrenText(e, "word");

		dirtyname = mergeArray(dirtyword, dirtyname);

		filter.initialize(dirtyword);
		namefilter.initialize(dirtyname);
	}

	private static String[] mergeArray(String[] arr1, String[] arr2) {
		int len = arr1.length + arr2.length;
		String[] arr = new String[len];
		int i = 0;
		for (String str : arr1) {
			arr[i] = str;
			i++;
		}

		for (String str : arr2) {
			arr[i] = str;
			i++;
		}

		return arr;
	}

	File file;
	File wordFile;
	File nameFile;

	public File listenedFile() {
		return file;
	}

	public void onResourceChange(File file) {
		try {
			reload();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "dirtfilter";
	}

	public String filt(String inputMsg) {
		return filter.filt(inputMsg);
	}

	public boolean contains(String msg) {
		return filter.contain(msg);
	}

	public String filtName(String name) {
		return namefilter.filt(name);
	}

	public boolean containsName(String msg) {
		return namefilter.contain(msg);
	}

	/**
	 * 公用的输入文字检查工具，主要用于玩家姓名，宠物姓名，玩家签名等可能带中文字符的输入检查 包括以下检查：
	 * 
	 * 
	 * 1. 非法字符检查 2. 非法屏蔽字检查 3. 允许的字符长度检查 4. 是否区分中/英文字符长度 5. 为空检查
	 * 
	 * @param input
	 *            需要检查的输入信息
	 * @param inputType
	 *            输入项信息 ： 角色名/宠物名/角色签名
	 * @param minLen
	 *            字符最小长度
	 * @param maxLen
	 *            字符最大长度
	 * @param wchar
	 *            是否区分中/英文字符
	 * @param checkNull
	 *            是否进行为空，或者长度为0的判断
	 * @return 错误信息
	 */
	public String checkInput(WordCheckType type, String input,
			String inputType, int minLen, int maxLen, boolean wchar,
			boolean checkNull) {

		// 获取多语言管理类
		// 参数
		String[] _params = new String[] { Const.getString(inputType) };
		String[] _minparams = new String[] { Const.getString(inputType),
				"" + minLen };
		String[] _maxparams = new String[] { Const.getString(inputType),
				"" + maxLen };

		// 检查是否为NULL
		if (input == null) {
			return Const.getString("input_null", _params);
		}

		// 获取长度
		int _length = input.length();

		// 长度是否区分中英文
		if (wchar) {

			int len = 0;
			for (int i = 0; i < _length; i++) {
				char c = input.charAt(i);
				if (c > '\u4E00' && c < '\u9FA5') {
					len = len + 2;
				} else {
					len++;
				}
			}

			_length = len;
		}

		// 如果不能为空
		if (checkNull) {
			if (_length < 1) {
				return Const.getString("input_too_short", _minparams);
			}
		}

		// 检查长度
		if (_length < minLen) {
			// 如果输入的字符串小于最小长度
			return Const.getString("input_too_short", _minparams);
		}

		// 如果输入的字符串长度大于最大允许长度
		if (_length > maxLen) {
			return Const.getString("input_too_long", _maxparams);
		}

		if (type == WordCheckType.NAME) {
			// 检查字符是否有异常字符
			if (StringUtil.hasExcludeChar(input)
					|| !StringUtil.isValidMySqlUTF8Fast(input)) {
				return Const.getString("input_error1", _params);
			}
			// 检查字符是否有非法关键字
			if (this.containsName(input)) {
				return Const.getString("input_error2", _params);
			}
			// 检查非法字符
			for (int i = 0; i < ILLEGAL_CHARS.length; i++) {
				if (input.indexOf(ILLEGAL_CHARS[i]) >= 0) {
					return Const.getString("input_error3", _params);
				}
			}
		} else if (type == WordCheckType.CHAT_ANNOUNCE_DESC) {
			// 检查字符是否有异常字符
			if (StringUtil.hasExcludeChar(input)
					|| !StringUtil.isValidMySqlUTF8Fast(input)) {
				return Const.getString("input_error1", _params);
			}
			// 检查字符是否有非法关键字
			if (this.contains(input)) {
				return Const.getString("input_error2", _params);
			}
		}
		return null;
	}

	/**
	 * 文字检查类型
	 * 
	 * 
	 */
	public enum WordCheckType {
		/** 名字类 */
		NAME,
		/** 聊天信息，大段信息，公告类 */
		CHAT_ANNOUNCE_DESC
	}

	/**
	 * 关键词过滤接口
	 * 
	 * 
	 */
	public interface IKeyWordsFilter {

		/**
		 * 使用关键字列表进行初始化
		 * 
		 * @param keyWords
		 *            关键字列表
		 * @return 是否初始化成功，成功true，否则false
		 */
		public abstract boolean initialize(String[] keyWords);

		/**
		 * 过滤关键词
		 * 
		 * @param s
		 *            要被处理的字符串
		 * @return 处理完毕的被过滤的字符串
		 */
		public abstract String filt(String s);

		/**
		 * 检测输入的消息是否有关键字
		 * 
		 * @param inputMsg
		 *            要检测的消息
		 * @return 若有返回true，否则false
		 */
		public abstract boolean contain(String inputMsg);

	}

	/**
	 * 基于***代码修改，实现真正AC过滤算法
	 * 
	 * 原有算法：失效的时候退回自动机入口重新匹配下一个字符。 相当于AC算法中把每个点的失效节点置为初始状态节点。
	 * 
	 * 本改进:计算正确的失效节点,当扫描一个字符，并无对应状态转移时根据该节点的失效节点计算转移（原算法回到初始状态，开始下一轮状态转换)，
	 * 从而降低复杂度，提高效率
	 * 
	 * 
	 */
	public class KeyWordsACFilter implements IKeyWordsFilter {
		/** DFA入口 */
		private final DFANode dfaEntrance;
		/** 要忽略的字符 */
		private final char[] ignoreChars;
		/** 过滤时要被替换的字符 */
		private final char subChar;

		/**
		 * 定义不进行小写转化的字符,在此表定义所有字符,其小写使用原值,以避免转小写后发生冲突:
		 * <ul>
		 * <li>char code=304, -> i,即的小写是i,以下的说明类似</li>
		 * <li>char code=8490, -> k,</li>
		 * </ul>
		 */
		private final char[] ignowLowerCaseChars = new char[] { 304, 8490 };

		/**
		 * 
		 * @param ignore
		 *            要忽略的字符,即在检测时将被忽略的字符
		 * @param substitute
		 *            过滤时要被替换的字符
		 */
		public KeyWordsACFilter(char[] ignore, char substitute) {
			dfaEntrance = new DFANode();
			this.ignoreChars = new char[ignore.length];
			System.arraycopy(ignore, 0, this.ignoreChars, 0,
					this.ignoreChars.length);
			this.subChar = substitute;
		}

		public boolean initialize(String[] keyWords) {
			clear();
			// 构造DFA
			for (int s = 0; s < keyWords.length; s++) {
				String _keyword = keyWords[s];
				if (_keyword == null
						|| (_keyword = _keyword.trim()).length() == 0) {
					continue;
				}
				char[] patternTextArray = _keyword.toCharArray();
				DFANode currentDFANode = dfaEntrance;
				for (int i = 0; i < patternTextArray.length; i++) {
					final char _c = patternTextArray[i];
					// 逐点加入DFA
					final Character _lc = toLowerCaseWithoutConfict(_c);
					DFANode _next = currentDFANode.dfaTransition.get(_lc);
					if (_next == null) {
						_next = new DFANode();
						currentDFANode.dfaTransition.put(_lc, _next);
					}
					currentDFANode = _next;
				}
				if (currentDFANode != dfaEntrance) {
					currentDFANode.isTerminal = true;
				}
			}

			buildFailNode();
			return true;
		}

		/**
		 * 构造失效节点： 一个节点的失效节点所代表的字符串是该节点所表示它的字符串的最大 部分前缀
		 */
		private final void buildFailNode() {
			// 以下构造失效节点
			List<DFANode> queues = new ArrayList<DFANode>();
			dfaEntrance.failNode = dfaEntrance;//
			for (Iterator<DFANode> it = dfaEntrance.dfaTransition.values()
					.iterator(); it.hasNext();) {
				DFANode node = it.next();
				node.level = 1;
				queues.add(node);
				node.failNode = dfaEntrance;// 失效节点指向状态机初始状态
			}
			DFANode curNode = null;
			DFANode failNode = null;
			while (!queues.isEmpty()) {
				curNode = queues.remove(0);// 该节点的失效节点已计算
				failNode = curNode.failNode;
				for (Iterator<Map.Entry<Character, DFANode>> it = curNode.dfaTransition
						.entrySet().iterator(); it.hasNext();) {
					Map.Entry<Character, DFANode> nextTrans = it.next();
					Character nextKey = nextTrans.getKey();
					DFANode nextNode = nextTrans.getValue();
					// 如果父节点的失效节点中有条相同的出边，那么失效节点就是父节点的失效节点
					while (failNode != dfaEntrance
							&& !failNode.dfaTransition.containsKey(nextKey)) {
						failNode = failNode.failNode;
					}
					nextNode.failNode = failNode.dfaTransition.get(nextKey);
					if (nextNode.failNode == null) {
						nextNode.failNode = dfaEntrance;
					}
					nextNode.level = curNode.level + 1;
					queues.add(nextNode);// 计算下一层

				}

			}
		}

		/**
		 * 基于AC状态机查找匹配，并根据节点层数覆写应过滤掉的字符
		 * 
		 */
		public String filt(String s) {
			char[] input = s.toCharArray();
			char[] result = s.toCharArray();
			boolean _filted = false;

			DFANode currentDFANode = dfaEntrance;
			DFANode _next = null;
			int replaceFrom = 0;
			for (int i = 0; i < input.length; i++) {
				final Character _lc = this.toLowerCaseWithoutConfict(input[i]);
				_next = currentDFANode.dfaTransition.get(_lc);
				while (_next == null && currentDFANode != dfaEntrance) {
					currentDFANode = currentDFANode.failNode;
					_next = currentDFANode.dfaTransition.get(_lc);
				}
				if (_next != null) {
					// 找到状态转移，可继续
					currentDFANode = _next;
				}
				// 看看当前状态可退出否
				if (currentDFANode.isTerminal) {
					// 可退出，记录，可以替换到这里
					int j = i - (currentDFANode.level - 1);
					if (j < replaceFrom) {
						j = replaceFrom;
					}
					replaceFrom = i + 1;
					for (; j <= i; j++) {
						if (!this.isIgnore(input[j])) {
							result[j] = this.subChar;
							_filted = true;
						}
					}
				}
			}
			if (_filted) {
				return String.valueOf(result);
			} else {
				return s;
			}
		}

		public boolean contain(final String inputMsg) {
			char[] input = inputMsg.toCharArray();
			DFANode currentDFANode = dfaEntrance;
			DFANode _next = null;
			for (int i = 0; i < input.length; i++) {
				final Character _lc = this.toLowerCaseWithoutConfict(input[i]);
				_next = currentDFANode.dfaTransition.get(_lc);
				while (_next == null && currentDFANode != dfaEntrance) {
					currentDFANode = currentDFANode.failNode;
					_next = currentDFANode.dfaTransition.get(_lc);
				}
				if (_next != null) {
					// 找到状态转移，可继续
					currentDFANode = _next;
				}
				// 看看当前状态可退出否
				if (currentDFANode.isTerminal) {
					// 可退出，记录，可以替换到这里
					return true;
				}
			}

			return false;
		}

		/**
		 * 初始化时先调用此函数清理
		 */
		private void clear() {
			// 清理入口
			dfaEntrance.dfaTransition.clear();
		}

		/**
		 * 将指定的字符转成小写,如果与{@link #ignowLowerCaseChars}所定义的字符相冲突,则取原值
		 * 
		 * @param c
		 * @return
		 */
		private char toLowerCaseWithoutConfict(final char c) {
			return (c == ignowLowerCaseChars[0] || c == ignowLowerCaseChars[1]) ? c
					: Character.toLowerCase(c);
		}

		/**
		 * 是否属于被忽略的字符
		 * 
		 * @param c
		 * @return
		 */
		private boolean isIgnore(final char c) {
			for (int i = 0; i < this.ignoreChars.length; i++) {
				if (c == this.ignoreChars[i]) {
					return true;
				}
			}
			return false;
		}

	}

	/**
	 * DFA节点
	 * 
	 * 
	 */
	private static class DFANode {
		// 是否终结状态的节点
		public boolean isTerminal;
		/** 保存小写字母，判断时用小写 */
		private final HashMap<Character, DFANode> dfaTransition;
		// 不匹配时走的节点
		DFANode failNode;
		// 节点层数
		int level;

		// public boolean canExit = false;
		public DFANode() {
			this.dfaTransition = new HashMap<Character, DFANode>();
			isTerminal = false;
			failNode = null;
			level = 0;
		}
	}
}
