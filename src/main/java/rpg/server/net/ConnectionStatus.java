package rpg.server.net;

/** 连接状态 */
public enum ConnectionStatus {
	/** 登录中 */
	LOGIN, GATE,
	/** 游戏中 */
	PLAYING,
	/** 关闭状态 */
	CLOSED
}
