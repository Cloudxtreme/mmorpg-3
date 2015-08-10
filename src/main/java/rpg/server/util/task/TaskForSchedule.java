package rpg.server.util.task;

import java.util.concurrent.ScheduledFuture;

/**
 * 用于定时线程池的任务数据结构
 * 
 */
public abstract class TaskForSchedule {

	private ScheduledFuture<?> future;

	private ScheduledTimerTask timertask;

	/**
	 * @param timertask
	 *            the timertask to set
	 */
	void setTimertask(ScheduledTimerTask timertask) {
		this.timertask = timertask;
	}

	/**
	 * @param future
	 *            the future to set
	 */
	void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	/**
	 * 接口方法
	 */
	public abstract void run();

	/**
	 * 
	 * @return
	 */
	public boolean cancel() {
		timertask.cancel();
		if (future != null)
			return future.cancel(true);
		else
			return false;
	}

}
