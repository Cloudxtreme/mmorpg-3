package rpg.server.module.player;

/** 连接状态 */
public enum LoginStatus {
	/** 将要登陆状态 */
	PRE_LOGIN,
	/** 数据载入成功 */
	DB_SUCESS,
	/** 数据载入失败 */
	DB_FAILED,
	/** 登陆成功 */
	LOGIN_SUCCESS,
	/** 将要注销状态 */
	PRE_LOGOUT,
	/** 注销状态 */
	LOGOUT,
}
