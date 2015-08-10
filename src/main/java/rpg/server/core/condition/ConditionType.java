package rpg.server.core.condition;

/**
 * 条件类型<br/>
 * 定义与conditionMeta.xml对应 <br/>
 * 注意：新增枚举项时，请对应更新conditionMeta.xml资源文件 <br/>
 * 
 */
public enum ConditionType {
	// *******************************************
	// *************GameObject
	// *******************************************
	/** 等级范围 */
	GOB_LEVEL_RANGE,
	/** 检测HP范围 百分比 */
	GOB_HP_RANGE,
	/** 检测MP范围 百分比 */
	GOB_MP_RANGE,
	/** 检测HP是否满格 */
	GOB_HP_MAX,
	/** 检测MP是否满格 */
	GOB_MP_MAX,
	/** 对象状态 */
	GOB_STATE,
	/** 检测受限开关 */
	GOB_SWITCH,
	/** 检测时间范围,小时 */
	GOB_SYS_HOUR_LIMIT,
	/** 检测时间范围,年 */
	GOB_SYS_YEAR_LIMIT,
	/** 检测时间范围,星期 */
	GOB_SYS_WEEK_LIMIT,
	/** 地图检测 */
	GOB_MAP_RANGE,
	/** 技能目标是自己，用到参数"receiver" */
	GOB_SKILL_TARGET_SELF,

	// *******************************************
	// *************道具相关
	// *******************************************
	/**
	 * 是否拥有某个物品<br>
	 * 
	 * @param pos
	 *            byte 位置,0已装备身上的,1包裹内的,2仓库内的
	 * @param type
	 *            byte 类型,0装备,1道具,2任务道具
	 * @param exist
	 *            boolean true存在判断,false不存在判断
	 * @param id
	 *            String 物品ID
	 * @param count
	 *            int 数量
	 * 
	 * @return true\false
	 */
	ITEM_STUFF_ITEM,
	/** 是否携带物品 */
	ITEM_EXIST,
	/** 检测背包中是否含有指定物品 */
	ITEM_CONTAINER_EXIST, ITEM_NUDE,
	/** 判断装备位上是否有装备,0头盔 1项链 2肩膀 3胸甲 4腰带 5护腿 6鞋子 7护手 8戒指 9武器 */
	ITEM_SLOT_IS_EQUIP,
	/** 是否修理装备 */
	ITEM_EQUIP_IS_REPAIRED,
	/** 道具使用的时间限制 */
	ITEM_TIME_USE,
	/** 判断是否可以放入背包 */
	ITEM_CAN_ADD,
	/** 经验丹使用限制 */
	ITEM_JINGYANDAN_USE,
	/** 喜糖使用限制 */
	ITEM_XITANG_USE,

	// *******************************************
	// *************任务
	// *******************************************
	/** 检测已接任务的任务状态 */
	QUEST_ACCEPT,
	/** 检测玩家的任务列表是否为空 */
	QUEST_LIST_CHECK,

	// *******************************************
	// *************角色相关
	// *******************************************
	/** 是否在某张地图 */
	ROLE_ROOM,
	/** 角色状态 */
	ROLE_STATE,
	/** 靠近某个NPC */
	ROLE_NEAR_NPC,
	/** 初次登录 */
	ROLE_FIRST_LOGIN,
	/** 检测渠道号 */
	ROLE_CHAN_ID,
	/** 角色性别检测 */
	ROLE_GENDER,
	/** 角色战斗力检测*/
	ROLE_BATTLESCORE,
	/** 角色活跃度检测*/
	ROLE_ACTIVITYPOINT,
	/** 角色参加试炼场次数检测*/
	ROLE_TRAINING_COUNT,
	/** 角色环任务完成次数检测*/
	ROLE_RINGQUESTFINISH_COUNT,
	/** 角色活跃度累计值检测*/
	ROLE_ACTIVITYPOINT_ADDUP,
	/** 转生检测 */
	ROLE_REINCARNATION,
	// *******************************************
	// ************* buff相关
	// *******************************************
	/** 是否有某buff类的buff */
	BUFF_HAS,
	/** 是否有某buff组的buff */
	BUFF_HAS_GROUP,
	/** 检测身上是否有与其互斥的BUFF */
	BUFF_MUTEX_CHECK,
	// *******************************************
	// ************* 组队相关
	// *******************************************
	/** 队伍成员数量检测 */
	TEAM_SIZE,
	/** 是否有队伍 */
	TEAM_EXIST,
	// *******************************************
	// *************地图相关
	// *******************************************
	/** 回城点检测 */
	MAP_HOME_OPEN,

	// *******************************************
	// *************货币相关
	// *******************************************
	/**
	 * 货币数量<br>
	 * 检测玩家所持有货币数量是不是足够
	 * 
	 * @param type
	 *            byte货币类型
	 * @param num
	 *            long货币数量
	 * @return true:足够<br>
	 *         false:不足
	 * 
	 */
	CURRENCY_NUM,

	// *******************************************
	// *************宠物相关
	// *******************************************
	/**
	 * 宠物检测<br>
	 * byte post:位置 0-任意，1-随身，2-家园，3-仓库<br>
	 * String petId:宠物Id<br>
	 * int num:数量
	 * 
	 */
	PET_HAS,
	PET_HAS_2,
	/** 检测是否有宠物 位置种类：0-任意，1-随身，2-家园，3-仓库 */
	PET_SPACE,

	// *******************************************
	// *************战斗相关
	// *******************************************
	/** 检测是否正在战斗中 */
	BATTLE_IN_BATTLE,
	/** 是否完成指定战斗 */
	BATTLE_WIN_ID,
	/** 是否遭遇过指定战斗 */
	BATTLE_MEET_ID,
	/** 检测HP是否高于指定百分比 */
	BATTLE_HP_MORE_THAN,
	/** 检测HP是否低于指定百分比 */
	BATTLE_HP_LESS_THAN,
	/** 战斗回合数是否达到指定回合 */
	BATTLE_BOUT,
	/** 敌人中有包含指定buff的单元 */
	BATTLE_ENEMY_HAS_BUFF,
	/** 指定位置的单元的血量低于指定百分比 */
	BATTLE_OBJ_INPOS_HPRATE_LESS,

	// *******************************************
	// *************家园地图
	// *******************************************
	/** 当前地图是否是家园绑定的地图 */
	HOME_MAP,

	// *******************************************
	// *************好友相关
	// *******************************************
	/** 检测好友数量>指定值 */
	BUDDY_MORE_THAN,

	// 答题模块
	ANSWER_CHECK,

	// 活动模块
	ACTIVITY_OPEN,

	// *******************************************
	// *************阵营战相关
	// *******************************************
	/**
	 * 申请参与阵营战
	 */
	CAMP_BATTLE_APPLY,
	// *******************************************
	// *************VIP
	// *******************************************
	/**
	 * 检测玩家是否达到某VIP等级
	 * level:VIP等级
	 * state: 0:等于 1:大于 2:小于 3:大于等于 4:小于等于 5:不等于
	 *  
	 */
	VIP_LEVEL_CHECK,
	// *******************************************
	// *************家族
	// *******************************************
	/** 是否拥有家族 */
	FAMILY_HAS,
	
	// *******************************************
	// *************情侣
	// *******************************************
	COMPANION_HAS,
}
