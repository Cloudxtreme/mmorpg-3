package rpg.server.core.event;

import rpg.server.core.GameObject;
import rpg.server.core.SimulateObject;

/**
 * 事件
 * 
 */
public class GameEvent {

	/** 事件渠道标识 */
	public static enum Channel {
		/** 自身 */
		SELF,
		/** 邻居 */
		NEIGHBOR,
		/** 自身和邻居 */
		BOTH
	}

	/** 事件类别标识 */
	public static enum Type {
		// ********************************************
		// ******第三方新增事件
		// ********************************************
		/** 第三方自定义事件 */
		ON_ENENT,
		/** 消耗钻石 */
		CONSUME_DIAMOND,
		/** 获赠游戏币 */
		ONREWARD_DIAMOND,
		/** 道具使用记录 */
		ITEM_USE_SYNC,

		// ********************************************
		// ******地图相关
		// ********************************************
		/** 离开家园 */
		MAP_LEAVE_HOME,
		/** 进入家园 */
		MAP_ENTER_HOME,
		/** 离开场景 */
		MAP_LEAVE,
		/** 进入场景 */
		MAP_ENTER,
		/**
		 * 离开区域<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Zone zone:离开的区域,如果为NULL表示离开了一个无区域范围<br>
		 * 
		 * @see Zone
		 */
		MAP_LEAVE_ZONE,
		/**
		 * 进入区域<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Zone zone:进入的区域,如果为NULL表示进入了一个无区域范围<br>
		 * 
		 * @see Zone
		 * 
		 */
		MAP_ENTER_ZONE,
		/** 移动 */
		MAP_MOVE,
		/** 回城 */
		MAP_HOME,
		/** 发现邻居 */
		MAP_DISCOVER_NEIGHBOR,
		/** 删除邻居 */
		MAP_REMOVE_NEIGHBOR,
		/** 客户端加载地图完成 */
		MAP_LOAD_SUCCESS,

		// ********************************************
		// ******角色相关
		// ********************************************
		/** 首次登录 */
		FIRST_LOGIN,
		/** 升级 */
		ROLE_LEVELUP,
		/** 点数重置 */
		ROLE_POINT_RESET,
		/** 点数变化 */
		ROLE_POINT_CHANGED,
		/** 改变状态 */
		STATE_CHANGE,
		/** 用户下线 */
		LOGOUT,
		/** 活力更新 */
		VIGOR_CHANGE,
		/** 活跃度更新 */
		ACTIVITY_CHANGE,
		/** 活跃度增加 */
		ACTIVITY_ADD,
		/** 血魔更新 */
		HPMP_CHANGE,
		/** 更新最大血值 */
		HP_MAX_CHANGE,
		/** 更新最大魔值 */
		MP_MAX_CHANGE,
		/** 移动速度改变 */
		SPEED_CHANGE,
		/** 上线 */
		LOGIN,
		/** 重新上线 */
		RELOGIN,
		/** 加载成功 */
		LOAD_SUCCESS,
		/** 连续N天登陆事件 */
		LOGIN_CONTINUE,
		/** 24：00处理 */
		OVER_24HOUR,
		/**
		 * 系统设置变更<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters list:<br>
		 * SettingsType:设置类型 <br>
		 * byte:类型值
		 */
		ROLE_SETTING_CHANGE,
		/** 转生 */
		ROLE_REINCARNATION,
		/** 完成拼图 */
		PLAYER_PINTU,

		// ********************************************
		// ******客户端相关
		// ********************************************
		/** 客户端发来的心跳包 */
		CLIENT_HEARTBEAT,

		// ********************************************
		// ******战斗相关
		// ********************************************
		/** 击中 */
		HIT,
		/** 被击中 */
		BE_HIT,
		/** 原地复活 */
		RELIVE_SITU,
		/** 就近复活 */
		RELIVE_NEAR,
		/** 回城复活 */
		RELIVE_HOME,
		/** 攻击命中（主攻击者:防御者:{攻击者id列表(long[]),伤害(int),是否暴击(boolean),是否防御(boolean)}） */
		BATTLE_ATTACK_HIT,
		/** 攻击闪避（主攻击者:防御者:{攻击者id列表(long[])}） */
		BATTLE_ATTACK_MISS,
		/** 攻击免疫（主攻击者:防御者:{攻击者id列表(long[])}） */
		BATTLE_ATTACK_IMMUNITY,
		/** 攻击反击（反击者:被反击者:{反击伤害(int)}） */
		BATTLE_ATTACK_RESIST,
		/** 攻击反弹（反弹者:被反弹者:{反弹伤害(int)}） */
		BATTLE_ATTACK_REBOUND,
		/** 昏厥（主攻击者:昏厥者:{攻击者id列表(long[])}） */
		BATTLE_FAINT,
		/** KO（主攻击者:被KO者:{攻击者id列表(long[])}） */
		BATTLE_KO,
		/** 战斗击杀-包括队友和自己击杀的情况（本人:被击杀者:{实际击杀者id列表(long[])}） */
		BATTLE_KILL,
		/** 战斗被击杀（被击杀者:主击杀者:{实际击杀者id列表(long[])}） */
		BATTLE_BE_KILLED,
		/** 呼救 */
		BATTLE_CFH,
		/** 逃跑（逃跑者::{成功与否(boolean)}） */
		BATTLE_FLEE,
		/** 回合结束（本人::） */
		BATTLE_BOUT_END,
		/** 新回合开始 */
		BATTLE_BOUT_BEGIN,
		/** 战斗开始 */
		BATTLE_BEGIN,
		/**
		 * 战斗结束（本人::{取胜与否(boolean),战斗名号(String),类型(byte),评价(byte),战利品日志(String[]
		 * )}）
		 */
		BATTLE_END,
		/**
		 * 战斗结束,BATTLE_END处理结束之后跟随的事件
		 */
		BATTLE_ENDED,
		/** 战结束的CG播放完成 */
		BATTLE_CG_END,
		/** 添加跟随者（主人::{被添加者id(long)}） */
		BATTLE_FOLLOWER_ADD,
		/** 替换跟随者（主人::{被替换者id(long),替换者id(long)}） */
		BATTLE_FOLLOWER_REPLACE,
		/** 托管战斗（本人::{开或关(boolean)}） */
		BATTLE_ENTRUST,
		/** 技能/精灵使用（使用者:主受法者:{待发指令(S_BATTLE_SS_USE)} */
		BATTLE_SS_SHOW,
		/** 技能/精灵伤害（攻击者:防御者:{伤害值(int),命中类型(byte),是否防御(boolean),护盾减伤值(int)}） */
		BATTLE_SS_DAMAGE,
		/** 技能/精灵烧蓝（攻击者:防御者:{伤害值(int),命中类型(byte),是否防御(boolean),护盾减伤值(int)}） */
		BATTLE_SS_MANABURN,
		/** 受到毒伤害（中毒者::{伤害值(int)}） */
		BATTLE_SS_POISON,
		/** 技能“地球一周”特殊事件（使用者:目标:{离开/回归(boolean)}） */
		BATTLE_SS_EARTH_ROUND,
		/** 技能/精灵回复（回复者::{回复值(int),回复类型(byte)}） */
		BATTLE_SS_RECOVER,
		/** 战斗开始（PVE） */
		BATTLE_PVE_START,
		/** 击杀一个boss */
		BATTLE_KILL_BOSS,

		/** 播放攻击动画（连击系列技能专用） */
		BATTLE_ATTACK_ANIMATE,
		/** 一次A对B的攻击结束 */
		BATTLE_ACTION_END,
		/** 战斗中HPMP变化 */
		BATTLE_HPMP_CHANGE,
		/** 进入快速（skip）战斗 */
		BATTLE_SKIP,
		/** 攻击AOE */
		BATTLE_ATTACK_HIT_AOE,
		/**
		 * 攻击命中被援护（主攻击者:援护者{选中的目标id,攻击者id列表(long[]),伤害(int),是否暴击(boolean),是否防御(
		 * boolean)}）
		 */
		BATTLE_ATTACK_HIT_PROTECTOR,
		/** 技能/精灵伤害被援护（攻击者:援护者:{选中的目标id, 伤害值(int),命中类型(byte),是否防御(boolean)}） */
		BATTLE_SS_DAMAGE_PROTECTOR,
		/** 援助者攻击了玩家(玩家之后就可以攻击援助者了) */
		BATTLE_SS_HELPER_HIT_PLAYER,
		/** 气泡消失 */
		BATTLE_BUBBLE_REMOVE,
		/** 战斗中落马 */
		BATTLE_PET_RIDE_FALL,
		/** 战斗中一个单元带走另一个单元，旅程伙伴 */
		BATTLE_TWO_UNIT_DISAPPEAR,
		/** 战斗中一个单元消失，怯战 */
		BATTLE_UNIT_DISAPPEAR,
		/** 战斗行动之前 */
		BATTLE_PRE_ACTION,
		/** 战斗宝箱掉落 */
		BATTLE_BOUT_DROP,
		/** 战斗中形象改变 */
		BATTLE_BODY_CHANGE,
		/** 战斗行动之前 */
		BATTLE_TAKEACTION_PRE,
		/** 战斗普通攻击之后 */
		BATTLE_POST_ATTACK,
		/** 战斗被普通攻击击中之后 */
		BATTLE_BE_ATTACKED,
		/** 普通攻击附加元素伤害 */
		BATTLE_ELEM_ATTACH_DAMAGE,
		/** 连击（暂时不用） */
		BATTLE_COMBO,
		/** 连击开始 */
		BATTLE_COMBO_START,
		/** 连击结束 */
		BATTLE_COMBO_END,
		/** 连击循环 */
		BATTLE_COMBO_CYCLE,
		/** 反震伤害 */
		BATTLE_FEEDBACK_DAMAGE,
		// ********************************************
		// ****** 宠物相关
		// ********************************************
		/** 上下马 */
		PET_RIDE,
		/** 外显宠物 */
		PET_SHOW,
		/** 宠物属性变化 */
		PET_ATTR_CHANGE,
		/** 宠物携带状态变化 */
		PET_TAKE_STATE_CHANGED,
		/** 在家园中放养宠物 */
		PET_FREE_AT_HOME,
		/** 捕捉宠物 */
		PET_CATCH,
		/** 从家园中带走宠物 */
		PET_TAKE_FROM_HOME,

		/** 宠物心情变化 */
		PET_MOO_CHANGE,
		/** 宠物饱腹度变化 */
		PET_FUL_CHANGE,
		/** 宠物忠诚变化 */
		PET_ROY_CHANGE,
		/** 获得新的宠物 */
		PET_ADD,
		/** 移除宠物 */
		PET_REMOVE,
		/** 宠物设置“备战” */
		PET_SET_PFW,
		/** 宠物外形改变 */
		PET_SHAPE_CHANGE,
		/** 宠物升级 */
		PET_LEVELUP,
		/** 宠物保存重铸结果 */
		PET_RECAST_SAVE,
		/** 宠物状态变化 */
		PET_STATE_CHANGE,
		/** 宠物装饰品变化 */
		PET_DECORATE_CHANGE,
		/** 宠物学习技能 */
		PET_GAINSKILL,
		/** 宠物积分发生变化 */
		PET_POWER_CHANGE,
		/** 宠物单项成长发生变化 */
		PET_GROWTH_CHANGE,
		/** 宠物总成长发生变化 */
		PET_GROWTHALL_CHANGE,
		/**
		 * 宠物穿装备 <br>
		 * source:宠物所属玩家<br>
		 * target:宠物<br>
		 * parameters:null<br>
		 */
		PET_EQUIP_CHANGE,
		/**
		 * 宠物天赋重置 <br>
		 * source:宠物所属玩家<br>
		 * target:宠物<br>
		 * parameters:null<br>
		 */
		PET_TALENT_RELEARN,
		/** 双人骑乘(骑，销毁) */
		PET_MULTI_RIDE,
		/** 双人骑乘移除队员 */
		PET_MULTI_REMOVE_MEMBER,
		// ********************************************
		// ****** 装备相关
		// ********************************************
		/** 换装 */
		EQUIP_CHANGE,
		/** 强化-- 装备升级 */
		EQUIP_UPGRADE,
		/** 强化--装备星级提升 */
		// EQUIP_UPSTAR,
		/** 强化--装备强化等级提升 */
		// EQUIP_ENHANCE,
		/** 强化--镶嵌宝石 */
		// EQUIP_ENBED,
		/** 合成--印记 */
		// EQUIP_FUSING_YINJI,
		/** 修理装备 */
		EQUIP_REPAIR,
		/** 装备镶嵌精灵 */
		EQUIP_MOSAIC,
		/** 装备镶嵌结束（给新手引导用） */
		EQUIP_MOSAIC_FINISH,
		/** 装备耐久变化 */
		EQUIP_DURABILITY_CHANGE,
		/** 装备总积分变化 */
		EQUIPS_POWER_CHANGE,
		// ********************************************
		// ****** 物品相关
		// ********************************************
		/** 添加物品 */
		ITEM_ADD,
		/** 删除物品 */
		ITEM_REMOVE,
		/** 使用物品 */
		ITEM_USE,
		/** 批量添加物品 */
		ITEM_ADD_BATCH,
		/** 成功合成出物品 */
		ITEM_END_SYNTHESIS,
		/** 装备强化 */
		ITEM_UPGRADE,
		// ********************************************
		// ****** 技能相关
		// ********************************************
		/** 吟唱技能 */
		SING_SKILL,
		/** 使用技能 */
		USE_SKILL,
		/** 引导技能 */
		BOOT_SKILL,
		/** 引导技能完成 */
		BOOT_SKILL_COMP,
		/** 替换技能组 */
		REPLACE_SKILL,
		/** 特用特定技能（主要用于一些天赋、被动技能、buff的连带效果） */
		USE_SPECIAL_SKILL,

		// ********************************************
		// ****** Buff相关
		// ********************************************
		/** Buff相关 */
		BUFF_IN_BATTLE,

		// ********************************************
		// ****** Team相关
		// ********************************************
		/**
		 * 加入小队<br>
		 * source:本人
		 */
		TEAM_JOIN,
		/**
		 * 新队员加入
		 */
		TEAM_MEMBER_JOIN,
		/**
		 * 离开队伍<br>
		 * source:本人
		 */
		TEAM_LEAVE,
		/**
		 * 队伍解散<br>
		 * source:本人
		 */
		TEAM_DISMISS,
		/**
		 * 移交队长 <br>
		 * source:本人<br>
		 * target:新队长
		 */
		TEAM_TRANSFER,
		/**
		 * 踢人 <br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * long:被T者ID
		 */
		TEAM_KICK,
		/**
		 * 被踢<br>
		 * source:本人
		 */
		TEAM_BE_KICK,

		/**
		 * 队伍成员变动<br>
		 * 只有还在队伍里的人才能接收到此事件
		 */
		TEAM_MEMBER_CHANGE,

		// ********************************************
		// ****** 好友相关
		// ********************************************
		/** 添加好友 */
		BUDDY_ADD,
		/** 删除好友 */
		BUDDY_DEL,

		// ********************************************
		// ****** NPC相关
		// ********************************************
		/** NPC喊话 */
		NPC_TALK,
		/**
		 * 与NPC交互<br>
		 * source:本人<br>
		 * target:NPC<br>
		 */
		NPC_INTERACTIVE, NPC_FIRST_INTERACTIVE,

		// /** 发现玩家 */
		// NPC_DISCOVER_PLAYER,

		/**
		 * NPC被击杀<br>
		 * source:击杀的玩家<br>
		 * target:NPC 被击杀的NPC<br>
		 */
		NPC_BE_KILLED,

		// ********************************************
		// ****** 任务相关
		// ********************************************
		/**
		 * 在已接任务列表查看任务详情 <br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:查看的任务
		 * 
		 * @see Quest
		 */
		QUEST_DETAIL,
		/**
		 * 在NPC处查看任务详情<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:查看的任务
		 * 
		 * @see Quest
		 */
		QUEST_DETALL_BY_NPC,

		/**
		 * 新手引导
		 */
		QUEST_DETALL_BY_NPC_GUIDE,

		/**
		 * 新手引导
		 */
		QUEST_TURN_BY_NPC_GUIDE,
		/**
		 * 任务完成,未交还<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:完成的任务
		 * 
		 * @see Quest
		 */
		QUEST_FINISH,
		/**
		 * 完成任务获得经验<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:交还的任务<br>
		 * ExpRewardType expRewardType:奖励类型<br>
		 * Integer value:奖励经验值
		 * 
		 * @see Quest
		 * @see ExpRewardType
		 */
		QUEST_GAIN_EXP,
		/**
		 * 完成任务获得金钱<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:交还的任务<br>
		 * //这个参数现在发的是null Byte currencyType:货币类型<br>
		 * Integer value:奖励货币值
		 * 
		 * @see Quest
		 */
		QUEST_GAIN_CURRENCY,
		/**
		 * 交还任务<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:交还的任务
		 * 
		 * @see Quest
		 */
		QUEST_TURN,
		/**
		 * 接受任务 <br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:接受的任务
		 * 
		 * @see Quest
		 */
		QUEST_ACCEPTED,
		/**
		 * 取消任务 <br>
		 * source:玩家<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:取消的任务
		 * 
		 * @see Quest
		 */
		QUEST_CANCEL,
		/**
		 * 家族任务完成 <br>
		 * source:玩家<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:任务
		 * 
		 * @see Quest
		 */
		QUEST_FAMILY_FINISH,
		/**
		 * 情侣任务完成 <br>
		 * source:玩家<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:任务
		 * 
		 * @see Quest
		 */
		QUEST_LOVERS_FINISH,
		/**
		 * 师徒任务完成 <br>
		 * source:玩家<br>
		 * target:null<br>
		 * parameters:<br>
		 * Quest quest:任务
		 * 
		 * @see Quest
		 */
		QUEST_MASTER_FINISH, TOWER_FINISH,
		/**
		 * 任务失败
		 */
		QUEST_FAIL,
		/**
		 * 任务导航<br>
		 * source:玩家<br>
		 * target:null<br>
		 * parameters:<br>
		 * String questId:任务ID<br>
		 */
		QUEST_NAVIGATION,

		// ********************************************
		// ****** 家族相关
		// ********************************************
		/** 玩家加入家族（加入者::{家族id}） */
		FAMILY_JOIN,
		/**
		 * 玩家离开家族 <br>
		 * source:玩家<br>
		 * target:null<br>
		 * parameters:<br>
		 * Family family:退出的家族
		 * 
		 * @see Family
		 */
		FAMILY_LEAVE,

		/**
		 * 进入家族领地<br>
		 * source:玩家 <br>
		 * target:null<br>
		 * parameters:<br>
		 * long familyId:领地所属的家族的ID
		 */
		FAMILY_ENTER,
		/**
		 * 创建家族<br>
		 * source:玩家 <br>
		 * target:null<br>
		 * parameters:<br>
		 */
		FAMILY_CREATE,

		// ********************************************
		// ****** 称号相关
		// ********************************************
		/** 更换称号 */
		CHANGE_TITLE,
		// ********************************************
		// ****** 副本相关
		// ********************************************
		/**
		 * 进入副本<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * String 副本名称
		 */
		INSTANCE_ENTER,
		/**
		 * 退出副本<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * String 副本Id
		 */
		INSTANCE_QUIT,
		/**
		 * 格子事件完成 boolean 是否通知客户端开始移动
		 * */
		LATTICE_INSTANCE_FINISH,
		/** 格子事件：开启宝箱 */
		LATTICE_INSTANCE_OPEN_BOX,
		/** 格子副本完成 */
		LATTICE_INSTANCE_EVALUATE,
		/** 队员离开 为了解散队伍使用 */
		LATTICE_TEAM_MEMBER_LEAVE,
		/** 返回主菜单后离开格子副本 */
		LATTICE_NOTIFY_TO_LEAVE,

		// ********************************************
		// ******伙伴相关事件
		// ********************************************
		/**
		 * 伙伴外显<br>
		 * source:本人<br>
		 * target:null<br>
		 * parameters:<br>
		 * boolean 是否外显,true:显示,false:取消显示<br>
		 * String 伙伴形象,是否外显为true时有此参数 <br>
		 * String 伙伴名称,是否外显为true时有此参数
		 */
		PARTNER_SHOW,
		// ********************************************
		// ****** 新手帮助相关事件
		// ********************************************
		FIRST_BATTLE,
		/** 和固定怪物组XX战斗 */
		FIGHTING,
		/** 首次战斗 */
		FIRST_FIGHTING,
		/** 第二次战斗 */
		SECOND_FIGHTING,
		/** 第三次战斗 */
		THIRD_FIGHTING,
		/** 第四次战斗 */
		FOURTH_FIGHTING,
		/** 接受xsc_06_zhux任务后首次战斗 */
		FIRST_FIGHTING_QUEST,
		/** 第一次获得骑宠 */
		FIRST_HORSE_CLOTH,
		/** 获得新天赋点 */
		GIFT_POINT_INC,
		/** 第一次使用任务传送 */
		FIRST_QUEST_TRANSPORT,
		/** 接受副本任务 */
		ACCEPT_QUEST_INSTANCE,
		/** 接收到邮件 */
		RECEIVE_MAIL,
		/** 祭司第一次组队 */
		FIRST_PRIST_TEAM,
		/** 一起做任务吧 */
		QUEST_DO_QUEST,
		/** 迷路了吗 */
		QUEST_STRAY,
		/** 更名删除更名卡 */
		CHANGE_NAME, // 本应属于物品模块，暂时放在此处
		/** 每日登陆 */
		LOGIN_DAILY,
		// ********************************************
		// ****** 守护精灵相关
		// ********************************************
		/** 守护精灵外观变化（包括是否显示) */
		GUARDIANSPIRIT_IMAGE_CHANGE,
		/** 守护精灵纳献 */
		GUARDIANSPIRIT_FEED,
		/** 守护精灵属性改变 */
		GUARDIANSPIRIT_ATTRI_CHANGE,
		/** 等级变化 */
		GUARDIANSPIRIT_LEVEL_UP,
		/** 守护精灵使用时装 */
		GUARDIANSPIRIT_EQUIPFASHION,
		// ********************************************
		// ****** 成就相关
		// ********************************************
		/** 达成一个1级成就 */
		ACHIEVEMENT_L1_DONE,
		/** 达成一个2级成就 */
		ACHIEVEMENT_L2_DONE,
		// 答题事件
		ANSWER_NORMAL_OK, ANSWER_NORMAL_FAIL, ANSWER_VERIF_OK, ANSWER_VERIF_FAIL, ANSWER_ACTIVITY_OK, ANSWER_ACTIVITY_FAIL,

		// 押镖任务图标外显事件
		ANSWER_DART_QUEST_SHOW,

		// 答题全对的累计天数
		ANSWER_ALL_RIGHT_DAY,

		// ****************新手引导相关*****************
		GUIDE_FIRST_OPEN_SHOP,
		// ********************************************
		// ****** 家园相关
		// ********************************************
		HOMELAND_PET_BUBBLE_PLAYER, HOMELAND_DISCOVER_FINISH,
		/** 返回主菜单后离开家园 */
		HOMELAND_NOTIFY_LOG_OUT,
		/** 第一次进入家园 */
		HOMELAND_FIRST_ENTER_HOMELAND,
		/** 孵化完成 */
		HOMELAND_MIX_FINISHED,

		// 环任务
		/** 完成环任务 */
		QUESTRING_OVER,
		/** 完成任务环 */
		QUESTRING_TURN,
		/** 新的环任务 */
		QUEST_RING_OVER,

		/** 押镖完成 */
		QUEST_DART,

		/** 挖宝完成 */
		QUEST_DIG,

		/** 答题事件 */
		QUESTION_DO,

		/** 挑战练功房 */
		TAINNING_TEST,

		/** 挑战猎人之塔 */
		TRAINNING_TOW,

		/** 进入猎人之塔 */
		TRAINNING_TOW_ENTER,

		/** 挑战猎人之塔获胜 */
		TRAINNING_TOW_WIN,

		/** 在线竞技 */
		ARENA_EVENT,

		/** 在线竞技，创建一支队伍 */
		ARENA_CREATETEAM,

		/** 在线竞技1V1获胜 */
		ARENA_1V1_WIN,

		/** 在线竞技5V5获胜 */
		ARENA_5V5_WIN,

		/** 温泉挂机 */
		BARBECUE_EVENT,

		/** 温泉挂机到达指定连续时间 */
		BARBECUE_TIME,

		/** 温泉挂机到达指定连续天数 */
		BARBECUE_DAY,

		/** 挂机开始 */
		AUTO_START,

		/** 挂机结束 */
		AUTO_END,

		/** 第一次获得活跃度 */
		ACTIVITY_FIRST,

		// 首次进入温泉挂机地图*/
		BARBECUE_ISFIRST,

		// 进入温泉事件
		BARBECUE_ENTER,

		/** 情侣温泉退出 */
		BARBECUE_LOVE_EXIT,

		// 第一次战斗死亡
		DEAD_FIGHT_FIRST,

		// 第二次、第三次 战斗死亡，未加点
		DEAD_FIGHT,

		// ********************************************
		// ****** 聊天
		// ********************************************
		/**
		 * 接收到私聊信息
		 * */
		WHISPER_RECEIVERED,
		/**
		 * 发送私聊信息
		 * */
		WHISPER_SENDED,

		// ********************************************
		// ****** Companion相关
		// ********************************************
		/** COMPANION关系 */
		COMPANION_UPDATE,
		/** COMPANION关系加载完毕 */
		COMPANION_LOAD_COMPLETE,
		/** MASTER关系加载完毕 */
		COMPANION_MASTER_LOAD_COMPLETE,
		/** 亲密度上升 */
		INTIMACY_INCREASE,
		/** 出师 */
		APPRENTICE_CHUSHI,

		// ********************************************
		// ****** 充值
		// ********************************************
		/**
		 * 充值成功
		 **/
		CHARGE_SUCC,
		/**
		 * 充值补单（不在发放钻石）
		 **/
		CHARGE_SUPPLEMENT,
		/**
		 * 充值更新
		 **/
		CHARGE_UPDATE,
		/**
		 * 充值成功
		 **/
		CHARGE_FAIL,
		/**
		 * 消费
		 **/
		CURRENCY_DEBIT,

		/**
		 * 首次打开活力活动UI
		 */
		FIRST_VIGOR_UI,

		/** vip降级事件 */
		REDUCE_VIP_LEVEL,

		/** 充值额度降低 */
		REDUCE_CHARGE,
		// ********************************************
		// ****** 时装形象相关
		// ********************************************
		PLAYER_MODEL_CHANGE,
		/** 33竞技场日积分变化 */
		SCORE_33_CHANGE_DAY,
		/** 33竞技场周积分变化 */
		SCORE_33_CHANGE_WEEK,

		/**
		 * 交易相关（交易出） 99 货币 0装备 1道具 3宠物 货币类型 原型ID 货币量 交易数量
		 * */
		TRADE_COMPLETE_OUT,

		/**
		 * 交易相关（交易入） 99 货币 0装备 1道具 3宠物 货币类型 原型ID 货币量 交易数量 税
		 * */
		TRADE_COMPLETE_IN,
		/** 端午节积分改变 */
		DUANWU_SCORE_CHANGE,
		/** 玩家战斗力改变 */
		PLAYER_BATTLE_SCORE_CHANGE,
		/** 玩家观战结束 */
		PLAYER_BATTLE_WATCH_END,
		// ********************************************
		// ****** 百人道场相关
		// ********************************************
		/** 进入下一层 */
		PLAYER_PURGATORY_GOTO_NEXTFLOOR,
		/** 进入挑战 */
		PLAYER_PURGATORY_FIGHT,
	}

	/**
	 * @param type
	 * @param source
	 */
	public GameEvent(Type type, GameObject source) {
		this(type, source, null, NO_PARAM);
	}

	/**
	 * 
	 * @param type
	 * @param source
	 * @param target
	 */
	public GameEvent(Type type, GameObject source, GameObject target) {
		this(type, source, target, NO_PARAM);
	}

	/**
	 * 
	 * @param type
	 * @param source
	 * @param params
	 */
	public GameEvent(Type type, GameObject source, Object... params) {
		this(type, source, null, params);
	}

	/**
	 * 
	 * @param type
	 * @param source
	 * @param target
	 * @param callback
	 * @param params
	 */
	public GameEvent(Type type, GameObject source, GameObject target,
			Object... params) {
		this.source = source;
		this.target = target;
		this.type = type;
		this.params = params;
	}

	/**
	 * 获取第i各参数
	 * 
	 * @param i
	 * @return
	 */
	public final Object getParameter(int i) {
		return params[i];
	}

	/**
	 * 获取String型参数
	 * 
	 * @param i
	 * @return
	 */
	public final String getStringParameter(int i) {
		return (String) params[i];
	}

	/**
	 * 获取bool型参数
	 * 
	 * @param i
	 * @return
	 */
	public final boolean getBoolParameter(int i) {
		return ((Boolean) params[i]).booleanValue();
	}

	/**
	 * 获取byte型参数
	 * 
	 * @param i
	 * @return
	 */
	public final byte getByteParameter(int i) {
		return ((Byte) params[i]).byteValue();
	}

	/**
	 * 获取short型参数
	 * 
	 * @param i
	 * @return
	 */
	public final short getShortParameter(int i) {
		return ((Short) params[i]).shortValue();
	}

	/**
	 * 获取int型参数
	 * 
	 * @param i
	 * @return
	 */
	public final int getIntParameter(int i) {
		return ((Integer) params[i]).intValue();
	}

	/**
	 * 获取long型参数
	 * 
	 * @param i
	 * @return
	 */
	public final long getLongParameter(int i) {
		return ((Long) params[i]).longValue();
	}

	public final float getFloatParameter(int i) {
		return ((Float) params[i]).floatValue();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(type).append("|")
				.append(source == null ? "" : source.getId()).append("|")
				.append(target == null ? "" : target.getId()).append("|");
		for (Object para : params) {
			sb.append(para).append("|");
		}
		return sb.toString();
	}

	// ///////////////////////getter & setter//////////////////////////////
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the source
	 */
	public SimulateObject getSource() {
		return source;
	}

	/**
	 * @return the target
	 */
	public GameObject getTarget() {
		return target;
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}

	// ///////////////////////////////////////////////////////////////////
	private final Type type; // 事件类别
	// private final Source sourceTag; //事件源标识
	private final GameObject source, target; // 事件的发起者和目标（target may be
	// null）
	private final Object[] params; // 事件参数
	private final static Object[] NO_PARAM = new Object[0];
}
