package rpg.server.core.action;

enum ActionMode {
	/** 简单动作（一元） */
	SIMPLE,
	/** 随机动作组（执行时随机挑选其中一个执行） */
	RAND,
	/** 全部动作组（执行时全组都执行） */
	ALL,
	/** 模板类型的动作，从模板中载入 */
	TEMPLATE,
	/** 为对象增加脚本的动作类型 */
	SCRIPT,
	/** 为对象增加带条件的动作类型 */
	CONDI,
}
