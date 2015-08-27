package rpg.server.net;

public enum CloseRaeson {
	/** 客户端异常 */
	CLIENT_EXCEPTION,
	/** 客户端断开连接 */
	CLIENT_CLOSED,
	/** 服务器维护 */
	SERVER_CLOSE,
}
