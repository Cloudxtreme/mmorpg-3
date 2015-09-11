package rpg.server.db;

class DBResultHandler<T> {
	private T t;
	private Exception e;
	private boolean flag;
	private DBOpCallback<T> callback;

	protected DBResultHandler(DBOpCallback<T> callback, boolean flag, T t,
			Exception e) {
		this.t = t;
		this.callback = callback;
		this.flag = flag;
		this.e = e;
	}

	protected void doAction() {
		callback.handleResult(flag, t, e);
	}

}
