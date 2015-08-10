package rpg.server.util.task;

import java.util.TimerTask;

/**
 * 定时任务
 * 
 */
public class ScheduledTimerTask extends TimerTask {
	/**
	 * 
	 */
	TaskForSchedule task;

	ScheduledTimerTask(TaskForSchedule task) {
		this.task = task;
		task.setTimertask(this);
	}

	@Override
	public void run() {
		try {
			task.run();
		} catch (Exception e) {
			// TODO
		}
	}

}
