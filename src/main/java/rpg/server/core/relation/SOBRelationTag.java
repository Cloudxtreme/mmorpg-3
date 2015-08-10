package rpg.server.core.relation;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿真对象间的关联关系标识枚举 <br/>
 * 又分为一对一和一对多两种关系 <br/>
 * 
 * 允许通过"关系标识组1&关系标识组2……"的格式序列来指定多个关系标识组 <br/>
 * 例如："TARGET.TEAM & SELF"表示目标所在小队和自身 <br/>
 * 
 * 单个关系标识组中，允许通过"关系标识A.关系标识B……"的格式序列查找与当前SOB具有某种关系标识的对象集 <br/>
 * 但除关系标识链的最后一项之外，只允许以一对一关系索引 <br/>
 * 例如："TARGET.TARGET.TEAM"表示目标的目标所在小队 <br/>
 * 但不允许"TARGET.TEAM.TARGET"，因为目标的小队是一组对象，他们可能没有公共的目标，这样索引会造成歧义
 * 
 */
public enum SOBRelationTag {

	// ***********************************
	// ******一对一关系
	// ***********************************
	/** 系统 */
	SYS(false),
	/** 自身 */
	SELF(false),
	/** 目标 */
	TARGET(false),
	/** 主人(对于宠物\召唤物\物品等) */
	OWNER(false),
	/** 伴侣(仅对玩家角色) */
	MATE(false),
	/** 小队,怪物小队AI（把游戏中的小队对象作为一个仿真对象来处理） */
	TEAM(false),
	/** 帮派（把游戏中的帮派作为一个仿真对象） */
	GUILD(false),
	// ***********************************
	// ******一对多关系
	// ***********************************
	/** 小队成员 */
	TEAM_MEMBER(true),
	/** 帮派成员 */
	GUILD_MEMBER(true),
	/** 邻居 */
	NEIGHBOR(true),
	/** 仇恨列表 */
	THREAT(true);

	private SOBRelationTag(boolean multi) {
		this.multi = multi;
	}

	/**
	 * 将用"&"隔开的关系串组，分割为多个由"."隔开的关系串，再分别解析为多组关系序列
	 * 
	 * @param tag
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static final List<SOBRelationTag>[] parse(String tag)
			throws Exception {
		if (tag == null || tag.trim().equals("")) {
			return new List[0];
		}
		String[] and_parts = tag.trim().split(ANDSEPERATOR);
		List<SOBRelationTag>[] lists = new List[and_parts.length];
		for (int i = 0; i < and_parts.length; i++) {
			lists[i] = parseDot(and_parts[i].trim());
		}
		return lists;
	}

	/**
	 * 将用"."隔开的关系串，解析为一组关系序列 <br/>
	 * 
	 * @param tag
	 * @return
	 * @throws Exception
	 *             解析中需判断该是否满足一对多的限制，以及每一段是否是合法的枚举字符串 <br/>
	 *             任意一项不满足，将抛出异常
	 */
	private static List<SOBRelationTag> parseDot(String tag) throws Exception {
		ArrayList<SOBRelationTag> list = new ArrayList<SOBRelationTag>();
		if (tag == null || tag.trim().length() == 0 ) {// 默认指向自身
			list.add(SELF);
		} else {
			tag = tag.trim();
			String[] dot_parts = tag.split(DOTSEPERATOR);
			for (int i = 0; i < dot_parts.length - 1; i++) { // 解析前面n-1段（只能是一对一）
				try {
					SOBRelationTag r = SOBRelationTag.valueOf(dot_parts[i]
							.trim().toUpperCase());
					if (r.multi) {
						throw new Exception("对象关联关系类型解析出错，一对多关系只能出现在末尾：tag = "
								+ tag);
					} else {
						list.add(r);
					}
				} catch (IllegalArgumentException e) {
					throw new Exception("对象关联关系类型解析出错：tag = " + dot_parts[i]);
				}
			}
			try { // 解析最后一段（可以是一对一和一对多）
				list.add(SOBRelationTag.valueOf(dot_parts[dot_parts.length - 1]
						.trim().toUpperCase()));
			} catch (IllegalArgumentException e) {
				throw new Exception("对象关联关系类型解析出错：tag = "
						+ dot_parts[dot_parts.length - 1]);
			}
		}
		return list;
	}

	/** 是否为一对多关系 */
	private boolean multi;

	/** 分隔符 表示关系与 */
	private static final String ANDSEPERATOR = "&";
	/** 分隔符. */
	private static final String DOTSEPERATOR = "\\.";
}
