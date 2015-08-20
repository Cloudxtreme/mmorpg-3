package rpg.server.core.action;

/**
 * 动作类型<br/>
 * 定义与actionMeta.xml对应 <br/>
 * 注意：新增枚举项时，请对应更新actionMeta.xml资源文件 <br/>
 * 
 */
public enum ActionType {
	// *******************************************
	// *************GameObject
	// *******************************************
	/** 设置HP和MP值 */
	GOB_HPMP_CHANGE,
	/** 设置属性 */
	GOB_ATTR_CHANGE,
	/** 设置名称 */
	GOB_NAME_SET,
	/** 设置等级 */
	GOB_LEVEL_SET,

	// *******************************************
	// *************Map相关
	// *******************************************
	/** 地图传送 */
	MAP_TELEPORT,
	/** 传送到NPC */
	MAP_TELEPORT_TO_NPC,
	/** 传送到游戏对象身边 */
	MAP_TELEPORT_TO_OBJ,
	/** 自定义传送门 */
	MAP_TELEPORT_CUSTOM,
	/** 回城 */
	MAP_HOME,
	/** 绑定当前地图为回城点 */
	MAP_BIND_HOME,
	/** 进入家园 */
	MAP_ENTER_HOME,
	/** 选择初始村庄 */
	MAP_SELECT_HOMEPLACE,
	// *******************************************
	// *************NPC相关
	// *******************************************
	/** 召唤指定NPC到指定地图的指定坐标 */
	NPC_CALL_TO_CR,
	/** 指定NPC到角色附近 */
	NPC_CALL_TO_ROLE,
	/**
	 * 与NPC交互
	 */
	NPC_INTERACTIVE,

	/**
	 * 采集NPC
	 */
	NPC_GATHER,

	// *******************************************
	// *************物品相关
	// *******************************************
	/** 打开宠物重铸界面 */
	ITEM_VIEW_PET_RECAST,
	/** 打开仓库 */
	ITEM_VIEW_STORE,
	/** 打开保值界面 */
	ITEM_VIEW_WORTH,
	/** 打开宝箱或礼包 */
	ITEM_BOX,
	/** 将物品发到玩家背包中(提取物品/附件,放入背包) */
	ITEM_PICKUP,
	/** 提取字符串格式的物品，放入背包 */
	ITEM_PICKUP_ITEM,
	/** 任务奖励物品 */
	ITEM_ADDORMAIL_ITEM,
	/** 删除物品 */
	ITEM_REMOVE_ITEM,
	/** 从商人处购买物品的价格降低 */
	ITEM_PRICE,
	/** 打开商店 */
	ITEM_SHOP,
	/** 请求交易 */
	ITEM_TRADE_START,
	/** 邮寄物品 */
	ITEM_MAIL_ITEM,
	// /** 装备耐久度变化 */
	// ITEM_EQUIP_DURABILITY,
	ITEM_SYNTHESIS_OPEN_UI,
	/** 给合成系统用的打开合成界面action */
	ITEM_SYNTHESIS_OPEN_UI_FOR_VIGOR,
	/** 打开临时仓库 */
	ITEM_TEMPORARY,
	/** 打开强化界面 */
	ITEM_VIEW_UPGRADE,
	/** 打开附魔界面 */
	ITEM_VIEW_ATTACH,
	/** 使用了经验丹 */
	ITEM_JINGYANDAN_USED,
	/** 自动装备 */
	ITEM_AUTO_EQUIP,
	/** 使用了喜糖 */
	ITEM_XITANG_USED,
	// 打开宝石转换面板
	ITEM_OPEN_GEM_CONVERT_UI,
	/** 素材升级 */
	ITEM_OPEN_MATERIAL_CONVERT_UI,
	// 升级套装
	ITEM_SUIT_UPGRADE,
	// 打开升级套装界面
	ITEM_SUIT_UPGRADE_UI,
	// *******************************************
	// *************任务相关
	// *******************************************
	/** 任务给予 */
	QUEST_GIVE,
	/** 任务传送 */
	QUEST_TRANSPORT,
	/** 完成任务,直接完成已经接取的任务 */
	QUEST_FINISH,
	/** 交还任务,任务必须为已接,且状态为已完成 */
	QUEST_TURN,
	/**
	 * 挖宝完成<br>
	 * 用于完成挖宝任务时,差生挖宝完成事件供活力系统增加活力值
	 */
	QUEST_DIG,

	QUEST_PET,
	/**
	 * 任务给予,随机
	 */
	QUEST_GIVE_RANDOM,
	// *******************************************
	// *************角色相关
	// *******************************************
	/** 增加货币 */
	PLAYER_CURRENCY_ADD,
	/** 属性变更 */
	PLAYER_ATTR,
	/** 发送消息提示框 */
	PLAYER_MSG,
	/** 发送提示语 */
	PLAYER_HINT,
	/** 按公式获得货币 */
	PLAYER_INCREASE_CURRENCY_FORMULA,
	/** 按公式获得经验 */
	PLAYER_INCREASE_EXP_FORMULA,
	/** 点数重置 */
	PLAYER_POINT_RESET,
	// *******************************************
	// *************buff相关
	// *******************************************
	/** Buff给予 */
	BUFF_ADD,
	/** 清除buff类 */
	BUFF_CLEAR,
	/** 清除buff组 */
	BUFF_CLEAR_GROUP,

	// *******************************************
	// *************技能精灵逻辑相关
	// *******************************************
	/** 人物获得一个精灵 */
	SS_SPIRIT_ADD,
	/** 人物遗忘一个精灵 */
	SS_SPIRIT_LOSE,
	/** 宠物获得一个技能 */
	SS_SKILL_ADD,
	/** 宠物遗忘一个技能 */
	SS_SKILL_LOSE,

	// *******************************************
	// *************技能精灵BUFF效果相关
	// *******************************************
	/** 破防攻击 */
	SS_EFFECT_ATK_DEFDEC,
	/** 多次攻击 */
	SS_EFFECT_ATK_TIMES,
	/** 降低攻击力的攻击 */
	SS_EFFECT_ATK_ATKDEC,
	/** 中毒 */
	SS_EFFECT_POISON,
	/** 石化 */
	SS_EFFECT_STONE,
	/** 泥醉 */
	SS_EFFECT_DRUNK,
	/** 混乱 */
	SS_EFFECT_CHAOS,
	/** 睡眠 */
	SS_EFFECT_SLEEP,
	/** 加强攻击 */
	SS_EFFECT_ATK_ATKINC,
	/** 一击必杀 */
	SS_EFFECT_ATK_ATKINC_HITDEC,
	/** 地球一周（离开） */
	SS_EFFECT_EARTH_ROUND_AWAY,
	/** 地球一周（回归） */
	SS_EFFECT_EARTH_ROUND_BACK,
	/** 全体攻击 */
	SS_EFFECT_ATK_ALL,
	/** 点燃攻击 */
	SS_EFFECT_ATK_BURST,
	/** 不防守战法 */
	SS_EFFECT_NO_DEFENSE,
	/** 保护他人 */
	SS_EFFECT_PROTECT_OTHER,
	/** 回复HP/MP 按上限比例回复目标HP/MP（技能：skill18体力回复，skill19气力回复） */
	SS_EFFECT_HPMP_RECOVER,
	/** 回复HP/MP 按具体值回复目标HP/MP（精灵：spirit1-spirit3） */
	SS_EFFECT_HPMP_RECOVER_VAL,
	/** 修改属性 */
	SS_EFFECT_ATTR_CHANGE,
	/** 挑衅 */
	SS_EFFECT_PROVOKE,
	/** 鼓舞 */
	SS_EFFECT_INSPIRE,
	/** 加buff 给单体加buff（技能：skill20-skill22增加敏捷、攻击、防御） */
	SS_EFFECT_BUFF_ADD,
	/** 移除buff 给单体移除buff */
	SS_EFFECT_BUFF_CLEAR,
	/** 移除buff 给单体移除buff */
	SS_EFFECT_BUFF_CLEAR2,
	/** 加buff 给指定方全体加buff */
	SS_EFFECT_BUFF_ADD_FORALL,
	/** 移除buff 给指定方全体移除buff */
	SS_EFFECT_BUFF_CLEAR_FORALL,
	/** 解毒 解除单体中毒（精灵：spirit4lv1） */
	SS_EFFECT_CLEAR_POISON,
	/** 解毒 解除我方全体中毒（精灵：spirit4lv2） */
	SS_EFFECT_CLEAR_POISON_ALL,
	/** 解酒 解除单体醉酒（精灵：spirit8lv1） */
	SS_EFFECT_CLEAR_DRUNK,
	/** 解酒 解除我方全体醉酒（精灵：spirit8lv2） */
	SS_EFFECT_CLEAR_DRUNK_ALL,
	/** 全体净化 */
	SS_EFFECT_PURIFY_FORALL,
	/** 复活 */
	SS_EFFECT_RELIVE_HPRATE,
	/** 反弹攻击 */
	SS_EFFECT_GUARD_REBOUND,
	/** 伤害回复 */
	SS_EFFECT_GUARD_RECOVER,
	/** 单元素攻击 */
	SS_EFFECT_ELEMENT_ATTACK,
	/** 属性提高，数值由施法者的该属性决定 */
	SS_EFFECT_ATTRUP_CASTER,
	/** 属性降低，数值由施法者的该属性决定 */
	SS_EFFECT_ATTRDOWN_CASTER,
	/** HPMP回复，数值由施法者的属性决定 */
	SS_EFFECT_HPMP_RECOVER_ATTRIADD,
	/** 元素抗性改变 */
	SS_EFFECT_ELEMENT_RESIST_CHANGE,
	/** 溅射 */
	SS_EFFECT_SPUTTERING,
	/** 一次施法造成连续几次攻击，每次攻击随机选目标，每次的攻击值略有下降 */
	SS_EFFECT_ATKTIMES_RANDOMTARGET,
	/** 多倍伤害，随机选定敌方目标 */
	SS_EFFECT_ATKUP_RANDOMTARGET,
	/** 攻击目标，并且给自己也可能给目标加buff */
	SS_EFFECT_ATK_BUFFADD,
	/** 攻击力上升后的一次猛攻 */
	SS_EFFECT_ATKUP_ATK,
	/** 给指定方移除buff，只对buff等级不高于指定等级的buff有效 */
	SS_EFFECT_BUFF_CLEAR_LV,
	/** 一定几率令目标中毒持续若干回合，指定每回合掉血的值 */
	SS_EFFECT_POISON_FIX_DAMAGE,
	/** 攻击吸血吸蓝 */
	SS_EFFECT_ABSORB_HPMP,
	/** 保护他人指定回合指定次数,并吸收伤害 */
	SS_EFFECT_PROTECT_OTHER_ABSORB_DAMAGE,
	/** 持续伤害 */
	SS_EFFECT_DOT,
	/** 护盾，按照生命值百分比算吸收量 */
	SS_EFFECT_SHIELD,
	/** 护盾2，按照指定数值算吸收量 */
	SS_EFFECT_SHIELD2,
	/** 解除本队的沉默状态，只对xx级别以下的沉默有效 */
	SS_EFFECT_DRUNK_CLEAR_FORALL,
	/** 解除本队的石化状态，只对xx级别以下的石化有效 */
	SS_EFFECT_STONE_CLEAR_FORALL,
	/** 解除本队的沉默状态，只对xx级别以下的沉默有效 */
	SS_EFFECT_SLEEP_CLEAR_FORALL,
	/** 解除本队的中毒状态，只对xx级别以下的中毒有效 */
	SS_EFFECT_POISON_CLEAR_FORALL,
	/** 解除本队的混乱状态，只对xx级别以下的混乱有效 */
	SS_EFFECT_CHAOS_CLEAR_FORALL,
	/** 落马术 */
	SS_EFFECT_RIDE_FALL,
	/** 牺牲体力补充到目标身上 */
	SS_EFFECT_RESCUE,
	/** 旅程伙伴 */
	SS_EFFECT_JOURNEY_PARTNER,
	/** 攻击力下降的一次攻击，附带吸血效果 */
	SS_EFFECT_ATKDEC_ABSORBHP,
	/** 怯战 */
	SS_EFFECT_FEAR_WAR,
	/** 保护目标，收到的伤害会有减免 */
	SS_EFFECT_PROTECT_OTHER_DEC_DAMAGE,
	/** 对方攻击无效 */
	SS_EFFECT_GOD,
	/** 新加的元素伤害 */
	SS_EFFECT_ELEMENT_DAMAGE,
	/** 斩击 */
	SS_EFFECT_CROSS,
	/** 剧毒 */
	SS_EFFECT_TOXIC,
	/** 地球上投 */
	SS_EFFECT_EARTH_THROW,
	/** 愤怒 */
	SS_EFFECT_ANGER,
	/** 单体挑衅 */
	SS_EFFECT_PROVOKE_SINGLE,
	/** 单体挑衅debuff */
	SS_EFFECT_PROVOKE_DEBUFF,
	/** 按比例修改主人属性(骑宠专用) */
	SS_EFFECT_OWNER_ATTR_CHANGE,
	/** 提高捉宠概率并执行捉宠 */
	SS_EFFECT_CATCH_PET,
	/** 基础属性加成百分比 */
	SS_EFFECT_ATTRBASE_ADDPER,
	/** 普通攻击+当前损失血量的百分比的伤害 */
	SS_EFFECT_HPLESS_ATKUP,
	/** 变身 */
	SS_EFFECT_BODY_CHANGE_SKILL,
	/** 提高某项属性，数值由基础属性决定 */
	SS_EFFECT_ATTRI_CHANGE_BY_BASE,
	/** 一定概率回复主人血/蓝，数值由宠物的属性*系数决定 */
	SS_EFFECT_RECOVER_OWNER_BY_ATTRI,
	/** 牺牲自己一定体力给对手造成伤害 */
	SS_EFFECT_SELF_DESTRUCTION,
	/** 一定概率造成伤害，数值由施法者属性*系数决定 */
	SS_EFFECT_DAMAGER_BY_ATTRI,
	/** 一定概率对额外的目标造成伤害，数值由固定的某个数值百分比决定 */
	SS_EFFECT_DAMAGE_EXTRATARGET_BY_VALUE,
	/** 中毒,多人目标按照属性值排序指定 */
	SS_EFFECT_POISON_MULTI,
	/** 石化,多人目标按照属性值排序指定 */
	SS_EFFECT_STONE_MULTI,
	/** 泥醉,多人目标按照属性值排序指定 */
	SS_EFFECT_DRUNK_MULTI,
	/** 混乱,多人目标按照属性值排序指定 */
	SS_EFFECT_CHAOS_MULTI,
	/** 睡眠,多人目标按照属性值排序指定 */
	SS_EFFECT_SLEEP_MULTI,
	/** 加buff,多人目标按照属性值排序指定 */
	SS_EFFECT_BUFF_ADD_MULTI,
	/** 强化敏捷 */
	SS_EFFECT_ENHANCE_QUI,
	/** 强化攻击 */
	SS_EFFECT_ENHANCE_ATK,
	/** 强化抗性 */
	SS_EFFECT_ENHANCE_RESIST,
	/** 单体加buff（附带概率） */
	SS_EFFECT_BUFF_ADD_BYRATE,
	/** 加buff 给指定方全体加buff（附带概率） */
	SS_EFFECT_BUFF_ADD_FORALL_BYRATE,
	/** 对目标无视防御无视命中公式的，百分比掉血一击 */
	SS_EFFECT_HPPER_DAMAGE,
	/** 自己替队友吸收伤害，并反弹该伤害（忠犬+镜之精灵） */
	SS_EFFECT_ZHONGQUAN_JINGZHIJINGLING,
	// *******************************************
	// *************宠物相关
	// *******************************************
	/** 获取宠物 */
	PET_GET_NEW,
	/** 学习驾兽技 */
	PET_RIDE_LEARN,
	/** 恢复所有携带宠物HP/MP */
	PET_RECOVER_ALL,
	/** 宠物通过食物提升饱腹 */
	PET_GET_FULL_BY_FOOD,
	/** 宠物通过道具提升心情 */
	PET_GET_MOOD_BY_ITEM,
	/** 宠物通过道具提升忠诚 */
	PET_GET_ROYAL_BY_ITEM,
	/** 宠物通过道具提升灵力 */
	PET_GET_INS_BY_ITEM,
	/** 宠物商店 */
	PET_SHOP_VIEW,
	/** 宠物外形改变 */
	PET_CHANGE_SHAPE,
	/** 增加宠物外形 */
	PET_ADD_SHAPE,
	/** 减少宠物灵力 */
	PET_DECREASE_INS,
	/** 打开宠物仓库UI */
	PET_STORE_OPEN,
	/** 打开宠物出售/回购UI */
	PET_SELL_OPEN,
	/** 打开宠物丢弃/找回UI */
	PET_FREE_OPEN,
	/** 凿技能栏 */
	PET_KNOCK_SKILL_BAR,
	/** 凿天赋栏 */
	PET_KNOCK_TALENT,
	/** 增加宠物装饰品 */
	PET_ADD_DECORATE,
	/** 学习骑宠技 */
	PET_LEARN_RIDESKILL,
	/** 宠物蛋 */
	PET_EGG,
	/** 宠物蛋（增加成长浮动值） */
	PET_EGG_FLOAT,
	/** 增加宠物经验 */
	PET_ADD_EXP,
	/** 培养预览 */
	PET_DEVELOP_PREVIEW,

	PET_SHOW,
	/** 将出战宠物设置为休息状态 */
	PET_REST,
	/** 将宠物设置为出战状态 */
	PET_WAR,
	/** 基因改造UI */
	PET_TRANSFORM_UI,
	/** 进化UI */
	PET_EVOLVE_UI,
	/** 使用兽心道具 */
	PET_USE_BEASTHEART,
	/** 按照条件删除宠物 */
	PET_DESTROY,
	// *******************************************
	// *************副本相关
	// *******************************************
	/** 进入副本 */
	INSTANCE_ENTER,
	/** 退出副本 */
	INSTANCE_QUIT,
	/** 副本介绍 */
	INSTANCE_INFO,
	/** 副本菜单 */
	INSTANCE_MENU,
	/** 副本重置 */
	INSTANCE_RESET,
	/** 进入下一子副本 */
	INSTANCE_ENTER_NEXT,
	/** 格子副本移动到指定的格子（一个格子） */
	LATTICE_INSTANCE_MOVE,
	/** 格子副本传送到指定的格子 */
	LATTICE_INSTANCE_TRANSPORT,
	/** 格子副本评价 */
	LATTICE_INSTANCE_EVALUATE,
	/** 格子类型：加buff */
	LATTICE_INSTANCE_ADD_BUFF,
	/** 格子类型：加人物 */
	LATTICE_INSTANCE_QUEST_GIVE,
	/** 格子类型：完成 */
	LATTICE_INSTANCE_QUEST_FINISH,
	/** 格子类型：物品奖励 */
	LATTICE_INSTANCE_ITEM_REWARD,
	/** 指定id的副本信息下发 */
	LATTICE_INSTANCE_MENU_INFO,
	/** 格子类型：战斗 */
	LATTICE_INSTANCE_BATTLE,
	/** 与4撸副本的小boss开战 */
	ROAD_INSTANCE_FIGHT_ROAD_BOSS,
	/** 与4撸副本的大boss开战 */
	ROAD_INSTANCE_FIGHT_BIG_BOSS,

	// *******************************************
	// *************货币相关
	// *******************************************
	/** 改变货币数量 */
	CURRENCY_CHANGE,

	// *******************************************
	// *************家族相关
	// *******************************************
	/** 家族UI */
	FAMILY_SHOW,
	/** 创建家族 */
	FAMILY_CREATE,
	/** 将家族列表发往客户端(申请UI) */
	FAMILY_LIST,
	/** 将家族列表发往客户端(领地UI) */
	FAMILY_LIST_TERRITORY,
	/** 进入自己家族领地 */
	FAMILY_ENTER,
	/** 家族介绍 */
	FAMILY_DESCRIPTION,
	/** 家族精灵 */
	FAMILY_WIZARD,
	/** 家族修炼 */
	FAMILY_PRACTICE,
	/** 打开家族商店 */
	FAMILY_ITEM_SHOP,
	/** 打开家族挑战UI */
	FAMILY_CHALLENGE_UI,
	/** 打开家族庄园战UI */
	FAMILY_MANOR_UI,
	/** 进入家族庄园站地图 */
	FAMILY_MANOR_ENTER,
	/** 进入家族庄严商店 */
	FAMILY_MANOR_SHOP,
	/** 家族修改名称 */
	FAMILY_RENAME,
	/** 发送家族邀请 */
	FAMILY_NEWFAMILY_INVITAION,
	// /** 加入技能Buff */
	// FS_EFFECT_BUFF,
	// *******************************************
	// *************伴侣相关
	// *******************************************
	/** 增加亲密度 */
	C_INTIMACY,
	/** 申请情侣 */
	C_APPLY_LOVER,
	/** 解除情侣 */
	C_APART_LOVER,
	/** 申请师徒 */
	C_APPLY_MASTER,
	/** 解除师徒 */
	C_APART_MASTER,
	/** 出师 */
	C_APPRENTICESHIP,
	/** 师徒列表 **/
	C_APPRENTICE_MASTER_LIST,
	/** 举行婚礼 */
	C_WEDDING,
	/** 打开好友列表 */
	C_OPEN_FRIEND_LIST,
	// *******************************************
	// *************称号相关
	// *******************************************
	/** 添加一个称号，通过id */
	TITLE_ADD,
	/** 移除一个称号 */
	TITLE_REMOVE,
	// *******************************************
	// *************战斗相关
	// *******************************************
	/** 邀请PK */
	BATTLE_PK_INVITE,
	/** 选择一个技能，加到行动列表中 */
	BATTLE_CHOISE_SKILL,
	/** 选择一个技能，直接使用 */
	BATTLE_USE_SKILL,
	/** 加buff */
	BATTLE_ADD_BUFF,
	/** 与指定怪物组战斗 */
	BATTLE_ENCOUNT_MONSTERGROUP,
	/** 添加一个战斗优先怪物组 */
	BATTLE_ADD_ENCOUNTER_PRIORITY,
	/** 删除一个战斗优先怪物组 */
	BATTLE_REMOVE_ENCOUNTER_PRIORITY,
	// *******************************************
	// *************PVP活动相关
	// *******************************************
	/** 加入地图 */
	PVP_ACTIVITY_ADD_ROOM,
	/** 离开地图 */
	PVP_ACTIVITY_LEAVE_ROOM,
	/** 挑战世界BOSS */
	ACTIVITY_WORLDBOSS_FIGHT,
	/** 进入封妖地图 */
	ACTIVITY_FENGYAO_ENTER_MAP,
	/** 进入共同BOSS地图 */
	ACTIVITY_COMMUNITY_ENTER_MAP,
	// 答题模块
	ANSWER_DO,
	// 新手引导
	ANSWER_HELPER,
	// 挖宝UI
	ANSWER_DIGGING,
	// 答题活动UI
	ANSWER_ACTIVITY_DO,
	// 押镖任务
	ANSWER_DART_QUEST,
	// *******************************************
	// *************竞技场相关
	// *******************************************
	/** 打开创建竞技场队伍界面 */
	ARENA_OPEN_CREATE_TEAM,
	/** 打开进入进入竞技场选择界面 */
	ARENA_OPEN_ENTER_ARENA, ARENA_OPEN,
	/***
	 * 竞技场排行
	 */
	ARENA_RANK,
	// *******************************************
	// *************关系相关
	// *******************************************
	RELATIONSHIP_REQUEST_ADD,
	// *******************************************
	// *************家园相关
	// *******************************************
	HOMELAND_ENTER, HOMELAND_OPEN_CRIB_UI, HOMELAND_OPEN_STORE_UI, HOMELAND_EXPAND_HOME, HOMELAND_OPEN_INCUBATOR_UI,
	// *******************************************
	// *************精力相关
	// *******************************************
	PLAYER_ENERGY_ADD,
	// 温泉烤肉
	BARBECUE_UI,
	// 猎人之塔
	TRAINING_ENTER,
	// 离线战斗UI
	TRAINING_UI,
	/**
	 * 翻牌游戏
	 */
	CARD_GAME,
	// *******************************************
	// *************魔兽塔相关
	// *******************************************
	// 魔兽塔菜单
	TOWER_MENU,
	// *******************************************
	// *************守护精灵相关
	// *******************************************
	// 打开献祭界面
	GUARDIANSPIRIT_OPEN_FEED_UI, GUARDIANSPIRIT_SHOW,
	// *******************************************
	// *************活力相关
	// *******************************************
	VIGOR_ADD, ACTIVITY_ADD, VIGOR_ENTER,
	// *******************************************
	// *************UI相关
	// *******************************************
	// 打开充值面板
	OPEN_CHARGE_UI,
	// 打开充值面板
	OPEN_CHARGE_UI_TW,
	// 打开宝藏面板
	OPEN_AWARD_UI,
	// 打开商场面板
	OPEN_MALL_UI,

	// *******************************************
	// *************联赛相关
	// *******************************************
	// 打开报名界面
	LEAGUE_SIGN_UP_UI,
	// 打开投票界面
	LEAGUE_VOTE_UI,
	/** 打开观战界面 */
	LEAGUE_BATTLE_NEWS_UI,
	/** 进入联赛地图 */
	LEAGUE_ENTER_MAP,

	/** 跨服报名界面 */
	LEAGUE_CS_SIGN_UP_UI,
	// 打开投票界面
	LEAGUE_CS_VOTE_UI,
	/** 打开观战界面 */
	LEAGUE_CS_BATTLE_NEWS_UI,
	/** 进入跨服联赛地图 */
	LEAGUE_CS_ENTER_MAP,
	// *******************************************
	// *************阵营战相关
	// *******************************************
	/**
	 * 进入
	 */
	CAMP_BATTLE_ENTER,
	/**
	 * 报名参与阵营战
	 */
	CAMP_BATTLE_APPLY,
	/**
	 * 阵营战与NPC战斗
	 */
	CAMP_BATTLE_NPCBATTLE,
	/**
	 * 阵营战日榜
	 */
	CAMP_BATTLE_DAY,
	// *******************************************
	// *************时装相关
	// *******************************************
	/**
	 * 获得时装
	 */
	FASHION_GET_FASHION,
	/**
	 * 人物变身道具
	 */
	FASHION_CHANGE_PLAYER_IMAGE,

	FASHION_CHANGE_PLAYER_IMAGE_CANCEL,

	/** 打开人物形象界面 */
	FASHION_IMAGE_UI,

	// *******************************************
	// *************充值相关
	// *******************************************
	/**
	 * 增加VIP等级
	 */
	ADD_VIP_LEVEL,
	// *******************************************
	// *************庄园战
	// *******************************************
	/**
	 * 庄园战进入
	 */
	MANOR_BATTLE_ENTER,
	/**
	 * 庄园对战列表
	 */
	MANOR_BATTLE_LIST,
	/**
	 * 情缘匹配
	 */
	RELATION_MATCH,
	// *******************************************
	// *************跨服庄园战
	// *******************************************
	/**
	 * 跨服庄园战进入
	 */
	MANOR_BATTLE_CS_ENTER,
	/**
	 * 跨服庄园战对战列表
	 */
	MANOR_BATTLE_CS_LIST,
	/**
	 * 跨服庄园战投票
	 */
	MANOR_BATTLE_CS_VOTE,
	/**
	 * 首充UI
	 */
	SHOUCHONG_UI,
	/** 打开家族祭拜UI */
	SACRIFICE_UI,
	/** 打开百人道场UI */
	PURGATORY_UI,

	// *******************************************
	// *************天使之翼
	// *******************************************
	WING_GET,
}
