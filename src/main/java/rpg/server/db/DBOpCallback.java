package rpg.server.db;

/**
 * 数据库操作回调接口
 */
@FunctionalInterface
public interface DBOpCallback<T> {
	/**
	 * 回调方法<br>
	 * 
	 * @param flag
	 *            数据库执行结果,true:成功
	 * @param result
	 *            返回数据,数据库操作失败时候(即flag=false),此项为null.
	 * @param e
	 *            异常,当数据库执行成功时(即flag=true),此项为null.
	 */
	void handleResult(boolean flag, T result, Exception e);
}
